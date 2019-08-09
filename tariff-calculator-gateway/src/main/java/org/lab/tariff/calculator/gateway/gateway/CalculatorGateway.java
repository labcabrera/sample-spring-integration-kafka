package org.lab.tariff.calculator.gateway.gateway;

import org.lab.tariff.calculator.gateway.config.IntegrationConfiguration;
import org.lab.tariff.calculator.model.CalculationRequest;
import org.lab.tariff.calculator.model.CalculationResponse;
import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;

@MessagingGateway
public interface CalculatorGateway {

	@Gateway(requestChannel = IntegrationConfiguration.CHANNEL_NAME_IN, replyChannel = IntegrationConfiguration.CHANNEL_NAME_OUT)
	CalculationResponse sendMessage(CalculationRequest request);

}