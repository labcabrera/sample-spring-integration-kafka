package org.lab.tariff.calculator.core.services;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Random;

import org.lab.tariff.calculator.core.domain.CalculationHistory;
import org.lab.tariff.calculator.core.domain.CalculationSourceData;
import org.lab.tariff.calculator.core.repositories.CalculationHistoryRepository;
import org.lab.tariff.calculator.core.repositories.CalculationSourceDataRepository;
import org.lab.tariff.calculator.model.CalculationRequest;
import org.lab.tariff.calculator.model.CalculationResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.handler.GenericHandler;
import org.springframework.messaging.MessageHeaders;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class CoreCalculator implements GenericHandler<CalculationRequest> {

	@Autowired
	private CalculationSourceDataRepository sourceRepository;

	@Autowired
	private CalculationHistoryRepository historyRepository;

	@Override
	public CalculationResponse handle(CalculationRequest request, MessageHeaders headers) {
		log.info("Performing internal calculation: {}", request);
		CalculationResponse response = calculateResponse(request);

		CalculationHistory history = saveHistory(request, response);
		response.setReference(history.getId());

		return response;
	}

	private CalculationResponse calculateResponse(CalculationRequest request) {
		CalculationSourceData source = sourceRepository.findBySourceName(request.getSource());
		Assert.notNull(source, "Missing source " + request.getSource() + " in mongodb");
		Integer rand = new Random().nextInt(50);
		BigDecimal amount = source.getBaseAmount().add(new BigDecimal(rand));

		CalculationResponse response = new CalculationResponse();
		response.setAmount(amount);
		response.setCalculated(Calendar.getInstance().getTime());
		return response;
	}

	private CalculationHistory saveHistory(CalculationRequest request, CalculationResponse response) {
		CalculationHistory entity = new CalculationHistory();
		entity.setRequest(request);
		entity.setResponse(response);
		return historyRepository.save(entity);
	}

}
