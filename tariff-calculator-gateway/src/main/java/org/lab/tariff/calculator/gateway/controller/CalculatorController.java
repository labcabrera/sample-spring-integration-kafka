package org.lab.tariff.calculator.gateway.controller;

import org.lab.tariff.calculator.gateway.gateway.CalculatorGateway;
import org.lab.tariff.calculator.model.CalculationRequest;
import org.lab.tariff.calculator.model.CalculationResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/calculator")
@Slf4j
public class CalculatorController {

	@Autowired
	private CalculatorGateway gateway;

	@PostMapping
	public CalculationResponse calculate(@RequestBody CalculationRequest request) {
		log.debug("Processing calculation request: {}", request);
		CalculationResponse response = gateway.sendMessage(request);
		log.debug("Received response: {}", response);
		return response;
	}

}
