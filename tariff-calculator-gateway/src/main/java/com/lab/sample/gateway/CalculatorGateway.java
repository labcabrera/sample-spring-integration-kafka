package com.lab.sample.gateway;

import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;

import com.lab.sample.model.CalculationRequest;
import com.lab.sample.model.CalculationResponse;

@MessagingGateway
public interface CalculatorGateway {

	@Gateway(requestChannel = "channel-tf-calculator-in",
		replyChannel = "channel-tf-calculator-out",
		replyTimeout = 600000,
		requestTimeout = 600000)
	CalculationResponse processCreation(CalculationRequest request);

}