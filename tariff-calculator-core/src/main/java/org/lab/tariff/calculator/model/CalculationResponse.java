package org.lab.tariff.calculator.model;

import java.math.BigDecimal;
import java.util.Date;

import lombok.Data;

@Data
public class CalculationResponse {

	private String reference;

	private BigDecimal amount;

	private Date calculated;

}
