package org.lab.tariff.calculator.core.repositories;

import org.lab.tariff.calculator.core.domain.CalculationSourceData;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CalculationSourceDataRepository extends MongoRepository<CalculationSourceData, String> {

	CalculationSourceData findBySourceName(String name);

}
