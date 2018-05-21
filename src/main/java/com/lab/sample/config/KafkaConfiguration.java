package com.lab.sample.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.handler.LoggingHandler.Level;
import org.springframework.integration.kafka.dsl.Kafka;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.KafkaTemplate;

@Configuration
@EnableConfigurationProperties(KafkaConfigurationProperties.class)
public class KafkaConfiguration {

	@Autowired
	private KafkaConfigurationProperties kafkaProperties;

	@Bean
	IntegrationFlow toKafka(KafkaTemplate<?, ?> kafkaTemplate) {
		return f -> f
			.log(Level.INFO, "Sending message to Kafka")
			.handle(Kafka.outboundChannelAdapter(kafkaTemplate)
				.messageKey(this.kafkaProperties.getMessageKey()));
	}

	@Bean
	IntegrationFlow fromKafkaFlow(ConsumerFactory<?, ?> consumerFactory) {
		return IntegrationFlows
			.from(Kafka.messageDrivenChannelAdapter(consumerFactory, this.kafkaProperties.getTopic()))
			.log(Level.INFO, "Received message from Kafka")
			.channel(c -> c.queue("fromKafka")).get();
	}

	// @Bean
	// public NewTopic topic(KafkaConfigurationProperties properties) {
	// return new NewTopic(properties.getTopic(), 1, (short) 1);
	// }
	//
	// @Bean
	// public NewTopic newTopic(KafkaConfigurationProperties properties) {
	// return new NewTopic(properties.getNewTopic(), 1, (short) 1);
	// }

	// @Autowired
	// private IntegrationFlowContext flowContext;
	//
	// @Autowired
	// private KafkaProperties kafkaProperties;

	// public void addAnotherListenerForTopics(String... topics) {
	// Map<String, Object> consumerProperties =
	// kafkaProperties.buildConsumerProperties();
	// // change the group id so we don't revoke the other partitions.
	// consumerProperties.put(ConsumerConfig.GROUP_ID_CONFIG,
	// consumerProperties.get(ConsumerConfig.GROUP_ID_CONFIG) + "x");
	// IntegrationFlow flow = IntegrationFlows
	// .from(Kafka.messageDrivenChannelAdapter(
	// new DefaultKafkaConsumerFactory<String, String>(consumerProperties), topics))
	// .channel("fromKafka").get();
	// this.flowContext.registration(flow).register();
	// }

}
