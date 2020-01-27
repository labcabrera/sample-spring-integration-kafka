package com.cnp.arc.hodei.notifications.dsl.outbound;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.amqp.dsl.Amqp;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.MessageChannels;
import org.springframework.integration.dsl.Transformers;
import org.springframework.integration.handler.LoggingHandler.Level;
import org.springframework.messaging.MessageChannel;

import com.cnp.arc.hodei.core.integration.AbstractOutboundConfiguration;

@Configuration
public class NotificationOutboundConfig extends AbstractOutboundConfiguration {

	public static final String CREATION_CHANNEL = "actionExecutionCreationChannel";
	private static final String MSG_REQUEST = "Sending notification message: %s";

	@Value("${app.amqp.notifications.exchange}")
	private String exchangeName;

	@Value("${app.amqp.notifications.routing.process-notification}")
	private String routingKey;

	@Bean(name = CREATION_CHANNEL)
	MessageChannel actionExecutionCreationChannel() {
		return MessageChannels.direct().get();
	}

	@Bean
	IntegrationFlow notificationOutboundFlow() {
		return IntegrationFlows
			.from(actionExecutionCreationChannel())
			.transform(Transformers.toJson(jsonObjectMapper))
			.log(Level.DEBUG, NotificationOutboundConfig.class.getName(), m -> String.format(MSG_REQUEST, m.getPayload()))
			.enrich(securityHeaderEnricher)
			.handle(Amqp.outboundAdapter(amqpTemplate)
				.exchangeName(exchangeName)
				.routingKey(routingKey))
			.get();
	}

}
