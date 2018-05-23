package org.lab.tariff.calculator.core.domain;

import java.math.BigDecimal;

import org.springframework.data.annotation.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CalculationSourceData {

	@Id
	private String id;

	private String sourceName;

	private BigDecimal baseAmount;

}
