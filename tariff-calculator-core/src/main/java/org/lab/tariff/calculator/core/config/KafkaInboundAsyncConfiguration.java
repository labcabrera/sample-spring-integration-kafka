package org.lab.tariff.calculator.core.config;

import org.lab.tariff.calculator.core.services.CoreCalculator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.Pollers;
import org.springframework.integration.dsl.Transformers;
import org.springframework.integration.handler.LoggingHandler.Level;
import org.springframework.integration.kafka.dsl.Kafka;
import org.springframework.integration.support.json.JsonObjectMapper;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.KafkaTemplate;

@Configuration
@EnableIntegration
public class KafkaInboundAsyncConfiguration {

	@Autowired
	private KafkaProperties kafkaProperties;

	@Autowired
	private CoreCalculator coreCalculator;

	@Autowired
	private JsonObjectMapper<?, ?> jsonMapper;

	@Bean
	IntegrationFlow flowAsyncFlow(KafkaTemplate<String, String> kafkaTemplate, ConsumerFactory<String, String> consumerFactory) {
		return IntegrationFlows
			//.from(Kafka.inboundChannelAdapter(consumerFactory, kafkaProperties.getTopicInAsync()))
			.from(Kafka.inboundChannelAdapter(consumerFactory, kafkaProperties.getTopicInAsync()).groupId("xxy"),
				e -> e.poller(Pollers.fixedDelay(1000)))
			.log(Level.DEBUG, KafkaInboundAsyncConfiguration.class.getName(),
				m -> String.format("Received calculation async request: %s", m))
			.transform(Transformers.fromJson(jsonMapper))
			.handle(coreCalculator)
			.log(Level.DEBUG, KafkaInboundAsyncConfiguration.class.getName(),
				m -> String.format("Processed calculation async response: %s", m))
			.get();
	}

}
