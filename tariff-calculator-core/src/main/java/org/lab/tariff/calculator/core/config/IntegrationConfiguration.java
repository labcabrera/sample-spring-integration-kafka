package org.lab.tariff.calculator.core.config;

import org.lab.tariff.calculator.core.Constants.MessageKeys;
import org.lab.tariff.calculator.core.Constants.Topics;
import org.lab.tariff.calculator.core.services.CoreCalculator;
import org.lab.tariff.calculator.model.CalculationRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.Transformers;
import org.springframework.integration.handler.LoggingHandler.Level;
import org.springframework.integration.kafka.dsl.Kafka;
import org.springframework.integration.support.json.Jackson2JsonObjectMapper;
import org.springframework.integration.support.json.JsonObjectMapper;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.KafkaTemplate;

@Configuration
@EnableIntegration
public class IntegrationConfiguration {

	@Autowired
	private CoreCalculator coreCalculator;

	@Bean
	JsonObjectMapper<?, ?> mapper() {
		return new Jackson2JsonObjectMapper();
	}

	//@formatter:off
	@Bean
	IntegrationFlow flowFromKafkaDummy(
			KafkaTemplate<String, String> kafkaTemplate,
			ConsumerFactory<String, String> consumerFactory,
			JsonObjectMapper<?, ?> mapper) {

		return IntegrationFlows
			.from(
				Kafka.messageDrivenChannelAdapter(consumerFactory, Topics.CalculationIn))
			.log(Level.INFO, IntegrationConfiguration.class.getName(), "\"Received Kafka calculation request\"")
			.transform(Transformers.fromJson(mapper))
			.handle(CalculationRequest.class, (request, headers) -> coreCalculator.calculate(request))
			.transform(Transformers.toJson(mapper))
			.handle(
				Kafka.outboundChannelAdapter(kafkaTemplate)
					.messageKey(MessageKeys.CalculationMessageKey)
					.topic(Topics.CalculationOut))
			.get();
	}
	//@formatter:on

}
