package org.lab.tariff.calculator.core.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Configuration
@ConfigurationProperties("app.kafka")
@Data
public class KafkaProperties {

	private String topicIn;

	private String topicInAsync;

	private String topicOut;

	private String topicOutAsync;

	private String messageKey;

}
