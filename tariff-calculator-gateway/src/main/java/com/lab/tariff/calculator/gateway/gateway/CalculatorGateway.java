package com.lab.tariff.calculator.gateway.gateway;

import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.GatewayHeader;
import org.springframework.integration.annotation.MessagingGateway;

import com.lab.tariff.calculator.model.CalculationRequest;
import com.lab.tariff.calculator.model.CalculationResponse;

@MessagingGateway
public interface CalculatorGateway {

	@Gateway(requestChannel = "channel-tf-calculator-in",
		replyChannel = "channel-tf-calculator-out",
		headers = @GatewayHeader(name = "x-header-name", value = "x-header-value"),
		replyTimeout = 1000,
		requestTimeout = 1000)
	CalculationResponse processCreation(CalculationRequest request);

}