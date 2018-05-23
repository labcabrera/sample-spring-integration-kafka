package org.lab.tariff.calculator.gateway.config;

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
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.MessageChannel;

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
		return MessageChannels.publishSubscribe("channel-tf-calculator-out").get();
	}

	//@formatter:off
	@Bean
	IntegrationFlow flowToKafka(KafkaTemplate<String, String> kafkaTemplate, JsonObjectMapper<?, ?> mapper) {
		return IntegrationFlows
			.from(MessageChannels.publishSubscribe("channel-tf-calculator-in"))
			.transform(Transformers.toJson(mapper))
			.log(Level.INFO, "channel -> kafka")
			.handle(Kafka.outboundChannelAdapter(kafkaTemplate).messageKey("dummy-message-key").topic("tf-calculator-in"))
			.get();
	}
	//@formatter:on

	//@formatter:off
	@Bean
	IntegrationFlow flowFromKafka(ConsumerFactory<String, String> consumerFactory, JsonObjectMapper<?, ?> mapper) {
		return IntegrationFlows
			.from(Kafka.messageDrivenChannelAdapter(consumerFactory, "tf-calculator-out"))
			.log(Level.INFO, "kafka -> channel")
			.channel("channel-tf-calculator-out")
			.bridge()
			.get();
	}
	//@formatter:off
	
	//@formatter:off
	@Bean
	IntegrationFlow flowFromKafkaDummy(KafkaTemplate<String, String> kafkaTemplate, ConsumerFactory<String, String> consumerFactory, JsonObjectMapper<?, ?> mapper) {
		return IntegrationFlows
			.from(Kafka.messageDrivenChannelAdapter(consumerFactory, "tf-calculator-in"))
			.log(Level.INFO, "kafka -> channel [DEMO CORE]")
			.handle(Kafka.outboundChannelAdapter(kafkaTemplate).messageKey("dummy-message-key").topic("tf-calculator-out"))
			.get();
	}
	//@formatter:off

}
