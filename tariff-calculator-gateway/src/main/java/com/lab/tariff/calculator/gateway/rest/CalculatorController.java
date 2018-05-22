package com.lab.tariff.calculator.gateway.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.lab.tariff.calculator.gateway.gateway.CalculatorGateway;
import com.lab.tariff.calculator.gateway.model.CalculationRequest;
import com.lab.tariff.calculator.gateway.model.CalculationResponse;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class CalculatorController {

	@Autowired
	private CalculatorGateway gateway;

	@PostMapping("/api/v1/calculator")
	public CalculationResponse calculate(@RequestBody CalculationRequest request) {
		log.info("Processing calculation request: {}", request);
		CalculationResponse response = gateway.processCreation(request);
		return response;
	}

}
