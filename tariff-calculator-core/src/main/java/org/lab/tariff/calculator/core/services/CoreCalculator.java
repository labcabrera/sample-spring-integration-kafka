package org.lab.tariff.calculator.core.services;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Random;

import org.lab.tariff.calculator.core.domain.CalculationSourceData;
import org.lab.tariff.calculator.core.repositories.CalculationSourceDataRepository;
import org.lab.tariff.calculator.model.CalculationRequest;
import org.lab.tariff.calculator.model.CalculationResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class CoreCalculator {

	@Autowired
	private CalculationSourceDataRepository sourceRepository;

	public CalculationResponse calculate(CalculationRequest request) {
		log.info("Performing internal calculation: {}", request);
		CalculationResponse response = new CalculationResponse();

		CalculationSourceData source = sourceRepository.findBySourceName(request.getSource());
		Assert.notNull(source, "Missing source " + request.getSource() + " in mongodb");
		Integer rand = new Random().nextInt(50);
		BigDecimal amount = source.getBaseAmount().add(new BigDecimal(rand));

		response.setAmount(amount);
		response.setCalculated(Calendar.getInstance().getTime());
		return response;
	}

}
