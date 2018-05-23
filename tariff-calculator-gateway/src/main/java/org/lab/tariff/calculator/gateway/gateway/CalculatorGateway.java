package org.lab.tariff.calculator.gateway.gateway;

import org.lab.tariff.calculator.gateway.Constants.Channels;
import org.lab.tariff.calculator.model.CalculationRequest;
import org.lab.tariff.calculator.model.CalculationResponse;
import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;

@MessagingGateway
public interface CalculatorGateway {

	@Gateway(requestChannel = Channels.CalculationIn)
	void sendMessage(CalculationRequest request);

	@Gateway(replyChannel = Channels.CalculationOut, requestTimeout = 5000L)
	CalculationResponse receiveMessage();

}