package org.lab.tariff.calculator.core.config;

import org.lab.tariff.calculator.core.services.CoreCalculator;
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
	private KafkaProperties kafkaProperties;

	@Autowired
	private CoreCalculator coreCalculator;

	@Bean
	JsonObjectMapper<?, ?> mapper() {
		return new Jackson2JsonObjectMapper();
	}

	@Bean
	IntegrationFlow flowFromKafkaDummy(
		KafkaTemplate<String, String> kafkaTemplate,
		ConsumerFactory<String, String> consumerFactory,
		JsonObjectMapper<?, ?> mapper) {

		return IntegrationFlows
			.from(Kafka
				.messageDrivenChannelAdapter(consumerFactory, kafkaProperties.getTopicIn()))
			.log(Level.DEBUG, getClass().getName(), m -> String.format("Received calculation request: %s", m))
			.transform(Transformers.fromJson(mapper))
			.handle(coreCalculator)
			.transform(Transformers.toJson(mapper))
			.log(Level.DEBUG, getClass().getName(), m -> String.format("Returning calculation response: %s", m))
			.handle(Kafka
				.outboundChannelAdapter(kafkaTemplate)
				.messageKey(kafkaProperties.getMessageKey())
				.topic(kafkaProperties.getTopicOut()))
			.get();
	}

}
