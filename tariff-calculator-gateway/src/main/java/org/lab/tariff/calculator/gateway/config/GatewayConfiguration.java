package org.lab.tariff.calculator.gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.support.json.Jackson2JsonObjectMapper;
import org.springframework.integration.support.json.JsonObjectMapper;

import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
public class GatewayConfiguration {

	@Bean
	JsonObjectMapper<?, ?> mapper(ObjectMapper objectMapper) {
		return new Jackson2JsonObjectMapper(objectMapper);
	}
}
