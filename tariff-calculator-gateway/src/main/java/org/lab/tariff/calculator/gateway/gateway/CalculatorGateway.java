package org.lab.tariff.calculator.gateway.gateway;

import org.lab.tariff.calculator.gateway.Constants.Channels;
import org.lab.tariff.calculator.model.CalculationRequest;
import org.lab.tariff.calculator.model.CalculationResponse;
import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.GatewayHeader;
import org.springframework.integration.annotation.MessagingGateway;

@MessagingGateway
public interface CalculatorGateway {

	@Gateway(requestChannel = Channels.CalculationIn,
		replyChannel = Channels.CalculationOut,
		headers = @GatewayHeader(name = "x-header-name", value = "x-header-value"),
		replyTimeout = 1000,
		requestTimeout = 1000)
	CalculationResponse processCreation(CalculationRequest request);

}