package com.lab.sample.rest;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.lab.sample.gateway.KafkaGateway;
import com.lab.sample.model.SampleMessage;

@RestController
public class SampleController {

	@Autowired
	private KafkaGateway gateway;

	// private MessagePublisher messagePublisher;

	@PostMapping("/api/publish")
	public SampleMessage publish(@RequestBody SampleMessage message) {
		message.setId(UUID.randomUUID().toString());
		gateway.sendToKafka("uno dos tres", "test");
		// messagePublisher.sendMessage(message);
		return message;
	}

}
