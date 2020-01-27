package org.lab.tariff.calculator.gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.kafka.support.DefaultKafkaHeaderMapper;

@Configuration
@EnableIntegration
public class KafkaIntegrationConfiguration {

	@Bean
	public DefaultKafkaHeaderMapper kafkaHeaderMapper() {
		return new DefaultKafkaHeaderMapper();
	}

}
