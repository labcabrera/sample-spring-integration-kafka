package org.lab.tariff.calculator.core.config;

import org.lab.tariff.calculator.core.services.CoreCalculator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.Transformers;
import org.springframework.integration.handler.LoggingHandler.Level;
import org.springframework.integration.kafka.dsl.Kafka;
import org.springframework.integration.support.json.JsonObjectMapper;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.KafkaTemplate;

@Configuration
public class KafkaInboundConfiguration {

	@Autowired
	private KafkaProperties kafkaProperties;

	@Autowired
	private CoreCalculator coreCalculator;

	@Autowired
	private JsonObjectMapper<?, ?> jsonMapper;

	@Bean
	IntegrationFlow inboundSyncFlow(KafkaTemplate<String, String> kafkaTemplate, ConsumerFactory<String, String> consumerFactory) {
		return IntegrationFlows
			.from(Kafka.messageDrivenChannelAdapter(consumerFactory, kafkaProperties.getTopicIn()))
			.log(Level.DEBUG, getClass().getName(), m -> String.format("Received calculation request: %s", m))
			.transform(Transformers.fromJson(jsonMapper))
			.handle(coreCalculator)
			.transform(Transformers.toJson(jsonMapper))
			.log(Level.DEBUG, getClass().getName(), m -> String.format("Returning calculation response: %s", m))
			.handle(Kafka
				.outboundChannelAdapter(kafkaTemplate)
				.messageKey(kafkaProperties.getMessageKey())
				.topic(kafkaProperties.getTopicOut()))
			.get();
	}

}
