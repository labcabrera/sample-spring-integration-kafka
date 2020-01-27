package org.lab.tariff.calculator.model;

import java.time.LocalDate;

import lombok.Data;

@Data
public class CalculationRequest {

	private LocalDate calculationRequestDate;

	private CustomerRequestInfo customer;

	private String source;

}
