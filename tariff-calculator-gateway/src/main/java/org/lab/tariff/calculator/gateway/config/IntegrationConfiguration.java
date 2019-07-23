package org.lab.tariff.calculator.gateway.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.lab.tariff.calculator.gateway.Constants.Channels;
import org.lab.tariff.calculator.gateway.Constants.MessageKeys;
import org.lab.tariff.calculator.gateway.Constants.Topics;
import org.lab.tariff.calculator.model.CalculationResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.channel.QueueChannel;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.MessageChannels;
import org.springframework.integration.dsl.Transformers;
import org.springframework.integration.handler.LoggingHandler.Level;
import org.springframework.integration.handler.advice.ErrorMessageSendingRecoverer;
import org.springframework.integration.kafka.dsl.Kafka;
import org.springframework.integration.kafka.inbound.KafkaMessageDrivenChannelAdapter;
import org.springframework.integration.kafka.support.RawRecordHeaderErrorMessageStrategy;
import org.springframework.integration.support.json.Jackson2JsonObjectMapper;
import org.springframework.integration.support.json.JsonObjectMapper;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.PollableChannel;

@Configuration
@EnableIntegration
public class IntegrationConfiguration {

	@Bean
	JsonObjectMapper<?, ?> mapper() {
		return new Jackson2JsonObjectMapper();
	}

	@Bean
	NewTopic topicCalculationIn() {
		return new NewTopic(Topics.CalculationIn, 1, (short) 1);
	}

	@Bean
	NewTopic topicCalculationOut() {
		return new NewTopic(Topics.CalculationOut, 1, (short) 1);
	}

	@Bean(name = Channels.CalculationIn)
	MessageChannel channelCalculatorIn() {
		return MessageChannels.direct().get();
	}

	@Bean(name = Channels.CalculationOut)
	public PollableChannel channelCalculatorOut() {
		return new QueueChannel();
	}

	@Bean(name = Channels.CalculationErr)
	MessageChannel channelCalculatorError() {
		return MessageChannels.direct().get();
	}

	@Bean
	IntegrationFlow inputChannelToKafkaFlow(KafkaTemplate<String, String> kafkaTemplate, JsonObjectMapper<?, ?> mapper) {
		return IntegrationFlows
			.from(MessageChannels.publishSubscribe(Channels.CalculationIn))
			.transform(Transformers.toJson(mapper))
			.log(Level.INFO, IntegrationConfiguration.class.getName(), "\"Publishing Kafka calculation request\"")
			.handle(Kafka
				.outboundChannelAdapter(kafkaTemplate)
				.messageKey(MessageKeys.CalculationMessageKey)
				.topic(Topics.CalculationIn))
			.get();
	}

	@Bean
	IntegrationFlow kafkaToOutputChannelFlow(ConsumerFactory<String, String> consumerFactory, JsonObjectMapper<?, ?> mapper) {
		return IntegrationFlows
			.from(Kafka
				.messageDrivenChannelAdapter(consumerFactory, KafkaMessageDrivenChannelAdapter.ListenerMode.record, Topics.CalculationOut)
				.recoveryCallback(new ErrorMessageSendingRecoverer(channelCalculatorError(), new RawRecordHeaderErrorMessageStrategy())))
			.log(Level.INFO, IntegrationConfiguration.class.getName(), "\"Received Kafka calculation response\"")
			.transform(Transformers.fromJson(CalculationResponse.class, mapper))
			.channel(Channels.CalculationOut)
			.get();
	}

	@Bean
	IntegrationFlow flowError(ConsumerFactory<String, String> consumerFactory, JsonObjectMapper<?, ?> mapper) {
		return IntegrationFlows
			.from(MessageChannels.publishSubscribe(Channels.CalculationErr))
			.log(Level.INFO, IntegrationConfiguration.class.getName(), "\"Received calculation error message\"")
			.bridge()
			.get();
	}

}
