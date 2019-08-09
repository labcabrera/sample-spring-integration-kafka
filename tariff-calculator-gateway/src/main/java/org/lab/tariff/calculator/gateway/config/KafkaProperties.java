package org.lab.tariff.calculator.gateway.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Configuration
@ConfigurationProperties("app.kafka")
@Data
public class KafkaProperties {

	private String topicIn;

	private String topicOut;

	private String messageKey;

	private int topicPartitions;

	private short replicationFactor;

}
