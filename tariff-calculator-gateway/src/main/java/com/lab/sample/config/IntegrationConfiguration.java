package com.lab.sample.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.channel.MessageChannels;
import org.springframework.integration.kafka.dsl.Kafka;
import org.springframework.integration.support.json.Jackson2JsonObjectMapper;
import org.springframework.integration.support.json.JsonObjectMapper;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.listener.KafkaMessageListenerContainer;
import org.springframework.kafka.listener.config.ContainerProperties;
import org.springframework.kafka.requestreply.ReplyingKafkaTemplate;

import com.lab.sample.Constants.Channels;

@Configuration
@EnableConfigurationProperties(KafkaConfigurationProperties.class)
public class IntegrationConfiguration {

	@Autowired
	private KafkaConfigurationProperties kafkaConfigurationProperties;

	@Autowired
	private KafkaProperties kafkaProperties;

	@Bean
	JsonObjectMapper<?, ?> mapper() {
		return new Jackson2JsonObjectMapper();
	}

	@Bean
	KafkaMessageListenerContainer listener(ConsumerFactory consumerFactory) {
		ContainerProperties containerProperties = new ContainerProperties("topics");
		KafkaMessageListenerContainer container = new KafkaMessageListenerContainer<>(consumerFactory,
			containerProperties);
		return container;
	}

	@SuppressWarnings("rawtypes")
	@Bean
	ReplyingKafkaTemplate template(ProducerFactory producerFactory, KafkaMessageListenerContainer replyContainer) {
		ReplyingKafkaTemplate<String, String, String> template = new ReplyingKafkaTemplate<>(producerFactory,
			replyContainer);
		return template;
	}

	//@formatter:off
	@Bean
	IntegrationFlow creationflow(ReplyingKafkaTemplate<?, ?,?> kafkaTemplate) {
		return IntegrationFlows
			.from(MessageChannels.publishSubscribe(Channels.CalculationRequest))
			//.transform(Transformers.toJson(mapper()))
			.handle(
				Kafka.outboundGateway(kafkaTemplate)
//				Amqp
//				.outboundGateway(amqpTemplate)
//				.routingKey(to)
			)
			//.transform(Transformers.fromJson(Contract.class, mapper()))
			.channel(MessageChannels.direct(Channels.CalculationResponse))
			.get();
	}
//@formatter:on

	// @Bean
	// IntegrationFlow calculateRequest(KafkaTemplate<?, ?> kafkaTemplate) {
//		//@formatter:off
//		return f ->
//			f.log(Level.INFO, "Sending message to Kafka")
//			.handle(Kafka.outboundChannelAdapter(kafkaTemplate)
//				.messageKey(this.kafkaProperties.getMessageKey()))
//			.channel(channels);
//		//@formatter:on
	// }
	//
	// @Bean
	// IntegrationFlow fromKafkaFlow(ConsumerFactory<?, ?> consumerFactory) {
	// return IntegrationFlows
	// .from(Kafka.messageDrivenChannelAdapter(consumerFactory, this.kafkaProperties.getTopic()))
	// .log(Level.INFO, "Received message from Kafka").channel(c -> c.queue("fromKafka")).get();
	// }

}
