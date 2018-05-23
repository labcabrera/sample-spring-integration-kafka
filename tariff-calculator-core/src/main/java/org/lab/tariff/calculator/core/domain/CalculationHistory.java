package org.lab.tariff.calculator.core.domain;

import org.lab.tariff.calculator.model.CalculationRequest;
import org.lab.tariff.calculator.model.CalculationResponse;
import org.springframework.data.annotation.Id;

import lombok.Data;

@Data
public class CalculationHistory {

	@Id
	private String id;

	private CalculationRequest request;

	private CalculationResponse response;
}
