package org.lab.tariff.calculator.gateway.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.IntegrationMessageHeaderAccessor;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.Transformers;
import org.springframework.integration.kafka.dsl.Kafka;
import org.springframework.integration.kafka.dsl.KafkaProducerMessageHandlerSpec;
import org.springframework.integration.support.json.JsonObjectMapper;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.KafkaHeaderMapper;

@Configuration
public class OutboundKafkaAsyncConfig {

	public static final String CHANNEL_NAME_IN = "channelCalculationAsyncIn";

	@Autowired
	private KafkaHeaderMapper kafkaHeaderMapper;

	@Autowired
	private KafkaProperties kafkaProperties;

	@Autowired
	private JsonObjectMapper<?, ?> mapper;

	@Bean
	public IntegrationFlow sendToKafkaFlow(ProducerFactory<Integer, String> producerFactory) {
		return IntegrationFlows
			.from(CHANNEL_NAME_IN)
			.transform(Transformers.toJson(mapper))
			.handle(kafkaMessageHandler(producerFactory, kafkaProperties.getTopicInAsync()))
			.log()
			.get();
	}

	private KafkaProducerMessageHandlerSpec<Integer, String, ?> kafkaMessageHandler(
		ProducerFactory<Integer, String> producerFactory, String topic) {
		return Kafka
			.outboundChannelAdapter(producerFactory)
			.messageKey(m -> m
				.getHeaders()
				.get(IntegrationMessageHeaderAccessor.SEQUENCE_NUMBER))
			.headerMapper(kafkaHeaderMapper)
			.topicExpression(String.format("headers[kafka_topic] ?: '%s'", topic))
			.configureKafkaTemplate(t -> t.id("kafkaTemplate:" + topic));
	}

}
