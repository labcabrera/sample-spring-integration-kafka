package org.lab.tariff.calculator.gateway.rest;

import org.lab.tariff.calculator.gateway.gateway.CalculatorGateway;
import org.lab.tariff.calculator.model.CalculationRequest;
import org.lab.tariff.calculator.model.CalculationResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class CalculatorController {

	@Autowired
	private CalculatorGateway gateway;

	@PostMapping("/api/v1/calculator")
	public CalculationResponse calculate(@RequestBody CalculationRequest request) {
		log.info("Processing calculation request: {}", request);
		gateway.sendMessage(request);
		CalculationResponse response = gateway.receiveMessage();
		return response;
	}

}
