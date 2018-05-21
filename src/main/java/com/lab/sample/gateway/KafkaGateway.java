package com.lab.sample.gateway;

import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.Header;

@MessagingGateway
public interface KafkaGateway {

	// TODO crear el canal que lee y escribe

	@Gateway(requestChannel = "toKafka.input")
	void sendToKafka(String payload, @Header(KafkaHeaders.TOPIC) String topic);

	@Gateway(replyChannel = "fromKafka", replyTimeout = 10000)
	Message<?> receiveFromKafka();

}