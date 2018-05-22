package com.lab.sample.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.lab.sample.gateway.CalculatorGateway;
import com.lab.sample.model.CalculationRequest;
import com.lab.sample.model.CalculationResponse;

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
