package org.lab.tariff.calculator.gateway.gateway;

import org.lab.tariff.calculator.gateway.config.OutboundKafkaSyncConfig;
import org.lab.tariff.calculator.gateway.config.OutboundKafkaAsyncConfig;
import org.lab.tariff.calculator.model.CalculationRequest;
import org.lab.tariff.calculator.model.CalculationResponse;
import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;

@MessagingGateway
public interface CalculatorGateway {

	@Gateway(requestChannel = OutboundKafkaSyncConfig.CHANNEL_NAME_IN, replyChannel = OutboundKafkaSyncConfig.CHANNEL_NAME_OUT)
	CalculationResponse sendMessage(CalculationRequest request);

	@Gateway(requestChannel = OutboundKafkaAsyncConfig.CHANNEL_NAME_IN)
	void sendMessageAsync(CalculationRequest request);

}