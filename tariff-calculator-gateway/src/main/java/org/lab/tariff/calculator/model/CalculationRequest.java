package org.lab.tariff.calculator.model;

import java.util.Date;

import lombok.Data;

@Data
public class CalculationRequest {

	private Date calculationRequestDate;

	private CustomerRequestInfo customer;

	private String source;

}
