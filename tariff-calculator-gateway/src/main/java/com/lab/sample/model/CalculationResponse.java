package com.lab.sample.model;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class CalculationResponse {

	private String id;

	private BigDecimal amount;

}
