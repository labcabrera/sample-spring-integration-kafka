package com.lab.tariff.calculator.gateway.model;

import java.math.BigDecimal;
import java.util.Date;

import lombok.Data;

@Data
public class CalculationResponse {

	private String id;

	private BigDecimal amount;

	private Date calculated;

}
