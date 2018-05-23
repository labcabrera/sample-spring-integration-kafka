package org.lab.tariff.calculator.core;

import java.math.BigDecimal;

import org.lab.tariff.calculator.core.domain.CalculationSourceData;
import org.lab.tariff.calculator.core.repositories.CalculationSourceDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import lombok.extern.slf4j.Slf4j;

@SpringBootApplication
@Slf4j
public class CoreApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(CoreApplication.class, args);
	}

	@Autowired
	private CalculationSourceDataRepository sourceRepository;

	@Override
	public void run(String... args) {
		if (sourceRepository.count() < 1) {
			log.info("Starting sample data");
			sourceRepository
				.insert(CalculationSourceData.builder().sourceName("web").baseAmount(new BigDecimal("100.10")).build());
			sourceRepository
				.insert(CalculationSourceData.builder().sourceName("test").baseAmount(new BigDecimal("42.11")).build());
		}
	}
}
