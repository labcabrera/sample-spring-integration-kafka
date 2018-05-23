package org.lab.tariff.calculator.core;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.Map;

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
		checkDataInitialization();
	}

	private void checkDataInitialization() {
		if (sourceRepository.count() < 1) {
			log.info("Starting sample data");
			Map<String, String> sources = new LinkedHashMap<>();

			// Some bootstrap dummy data to populate sources
			sources.put("web", "100.10");
			sources.put("direct", "500.00");
			sources.put("test", "42.11");
			sources.entrySet().forEach(x -> sourceRepository.insert(CalculationSourceData.builder()
				.sourceName(x.getKey()).baseAmount(new BigDecimal(x.getValue())).build()));
		}
	}
}
