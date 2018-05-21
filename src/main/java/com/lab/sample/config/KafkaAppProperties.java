package com.lab.sample.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

@Data
@ConfigurationProperties("kafka")
public class KafkaAppProperties {

	private String topic;

	private String newTopic;

	private String messageKey;

}