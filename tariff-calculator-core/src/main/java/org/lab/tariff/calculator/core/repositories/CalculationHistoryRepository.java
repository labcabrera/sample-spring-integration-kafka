package org.lab.tariff.calculator.core.repositories;

import org.lab.tariff.calculator.core.domain.CalculationHistory;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CalculationHistoryRepository extends MongoRepository<CalculationHistory, String> {

}
