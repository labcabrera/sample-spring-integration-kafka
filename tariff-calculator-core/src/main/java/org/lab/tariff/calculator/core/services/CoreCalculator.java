package org.lab.tariff.calculator.core.services;

import java.math.BigDecimal;
import java.util.Calendar;

import org.lab.tariff.calculator.model.CalculationRequest;
import org.lab.tariff.calculator.model.CalculationResponse;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class CoreCalculator {

	public CalculationResponse calculate(CalculationRequest request) {
		log.info("Performing internal calculation: {}", request);
		CalculationResponse response = new CalculationResponse();
		response.setAmount(new BigDecimal("1042.24"));
		response.setCalculated(Calendar.getInstance().getTime());
		return response;
	}

}
