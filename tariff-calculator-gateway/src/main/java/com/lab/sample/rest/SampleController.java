package com.lab.sample.rest;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.lab.sample.config.KafkaConfigurationProperties;
import com.lab.sample.gateway.KafkaGateway;
import com.lab.sample.model.SampleMessage;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class SampleController {

	@Autowired
	private KafkaConfigurationProperties kafkaProperties;

	@Autowired
	private KafkaGateway gateway;

	@PostMapping("/api/publish")
	public SampleMessage publish(@RequestBody SampleMessage message) {
		log.info("Received message", message);
		message.setId(UUID.randomUUID().toString());
		gateway.sendToKafka("uno dos tres", kafkaProperties.getTopic());
		Message<?> received = gateway.receiveFromKafka();
		log.info("Received Kafka response: {}", received);
		return message;
	}

}
