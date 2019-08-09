package org.lab.tariff.calculator.gateway.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.lab.tariff.calculator.model.CalculationResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.Transformers;
import org.springframework.integration.handler.LoggingHandler.Level;
import org.springframework.integration.kafka.dsl.Kafka;
import org.springframework.integration.support.json.JsonObjectMapper;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.listener.KafkaMessageListenerContainer;
import org.springframework.kafka.requestreply.ReplyingKafkaTemplate;

@Configuration
@EnableIntegration
public class IntegrationConfiguration {

	public static final String CHANNEL_NAME_IN = "channelCalculationIn";
	public static final String CHANNEL_NAME_OUT = "channelCalculationOut";
	public static final String TOPIC_NAME_IN = "calculationTopicRequest";
	public static final String TOPIC_NAME_OUT = "calculationTopicReplies";
	public static final String MESSAGE_KEY = "calculationMessageKey";

	@Autowired
	private JsonObjectMapper<?, ?> mapper;

	@Value("${topic.partitions:10}")
	private int partitions;

	@Value("${topic.replication-factor:2}")
	private short topicReplicationFactor;

	@Bean
	NewTopic calculationTopicRequest() {
		return new NewTopic(TOPIC_NAME_IN, partitions, topicReplicationFactor);
	}

	@Bean
	NewTopic calculationTopicReplies() {
		return new NewTopic(TOPIC_NAME_OUT, partitions, topicReplicationFactor);
	}

	@Bean
	IntegrationFlow outboundGateFlow(ReplyingKafkaTemplate<String, String, String> kafkaTemplate) {
		return IntegrationFlows.from(CHANNEL_NAME_IN)
			.log(Level.DEBUG, getClass().getName(), m -> String.format("Sending calculation request: %s", m))
			.transform(Transformers.toJson(mapper))
			.handle(Kafka.outboundGateway(kafkaTemplate).topic(TOPIC_NAME_IN).messageKey("calculateMessageKey"))
			.log(Level.DEBUG, getClass().getName(), m -> String.format("Received calculation response: %s", m))
			.transform(Transformers.fromJson(CalculationResponse.class, mapper))
			.channel(CHANNEL_NAME_OUT)
			.get();
	}

	@Bean
	KafkaMessageListenerContainer<String, String> replyContainer(ConsumerFactory<String, String> consumerFactory) {
		ContainerProperties properties = new ContainerProperties(TOPIC_NAME_OUT);
		return new KafkaMessageListenerContainer<>(consumerFactory, properties);
	}

	@Bean
	ReplyingKafkaTemplate<String, String, String> kafkaTemplate(ProducerFactory<String, String> pf,
		KafkaMessageListenerContainer<String, String> replyContainer) {
		return new ReplyingKafkaTemplate<>(pf, replyContainer);
	}

}
