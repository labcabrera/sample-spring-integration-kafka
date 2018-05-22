package com.lab.tariff.calculator.gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.Transformers;
import org.springframework.integration.dsl.channel.MessageChannels;
import org.springframework.integration.handler.LoggingHandler.Level;
import org.springframework.integration.kafka.dsl.Kafka;
import org.springframework.integration.support.json.Jackson2JsonObjectMapper;
import org.springframework.integration.support.json.JsonObjectMapper;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.listener.KafkaMessageListenerContainer;
import org.springframework.kafka.listener.config.ContainerProperties;
import org.springframework.kafka.requestreply.ReplyingKafkaTemplate;
import org.springframework.messaging.MessageChannel;

import com.lab.tariff.calculator.gateway.Constants.Channels;
import com.lab.tariff.calculator.gateway.model.CalculationResponse;

@Configuration
public class IntegrationConfiguration {

	@Bean
	JsonObjectMapper<?, ?> mapper() {
		return new Jackson2JsonObjectMapper();
	}

	@Bean(name = "channel-tf-calculator-in")
	MessageChannel channelCalculatorIn() {
		return MessageChannels.direct().get();
	}

	@Bean(name = "channel-tf-calculator-out")
	MessageChannel channelCalculatorOut() {
		return MessageChannels.direct().get();
	}

	@Bean
	ReplyingKafkaTemplate<String, String, String> replyKafkaTemplate(ProducerFactory<String, String> pf,
		KafkaMessageListenerContainer<String, String> container) {

		return new ReplyingKafkaTemplate<>(pf, container);
	}

	@Bean
	KafkaMessageListenerContainer<String, String> replyContainer(ConsumerFactory<String, String> cf) {
		ContainerProperties containerProperties = new ContainerProperties("tf-calculator-in");
		return new KafkaMessageListenerContainer<>(cf, containerProperties);
	}

	//@formatter:off
	@Bean
	IntegrationFlow creationflow(ReplyingKafkaTemplate<String, String, String> kafkaTemplate, JsonObjectMapper<?, ?> mapper) {
		return IntegrationFlows
			.from(MessageChannels.publishSubscribe("channel-tf-calculator-in"))
			.transform(Transformers.toJson(mapper))
			.log(Level.INFO, "Received message from calculator input channel")
			.handle(
				Kafka.outboundGateway(kafkaTemplate)
					.messageKey("message-key-value")
					.topic("tf-calculator-in"))
			.transform(Transformers.fromJson(CalculationResponse.class, mapper))
			.channel(MessageChannels.direct("channel-tf-calculator-out"))
			.get();
	}

}
