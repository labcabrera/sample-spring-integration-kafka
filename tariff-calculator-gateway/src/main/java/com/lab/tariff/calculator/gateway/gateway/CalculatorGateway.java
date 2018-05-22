package com.lab.tariff.calculator.gateway.gateway;

import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;

import com.lab.tariff.calculator.gateway.model.CalculationRequest;
import com.lab.tariff.calculator.gateway.model.CalculationResponse;

@MessagingGateway
public interface CalculatorGateway {

	@Gateway(requestChannel = "channel-tf-calculator-in",
		replyChannel = "channel-tf-calculator-out",
		replyTimeout = 600000,
		requestTimeout = 600000)
	CalculationResponse processCreation(CalculationRequest request);

}