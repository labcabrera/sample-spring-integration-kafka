package org.lab.tariff.calculator.core.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories(basePackages = "org.lab.tariff.calculator.core.repositories")
public class MongoConfiguration {

}
