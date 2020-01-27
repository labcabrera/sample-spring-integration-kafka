package com.cnp.arc.hodei.notifications;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.config.EnableIntegration;

import com.cnp.arc.hodei.notifications.service.NotificationService;

@Configuration
@ComponentScan
@EnableIntegration
@IntegrationComponentScan
public class HodeiNotificationsConfiguration {

	@Bean
	@ConditionalOnMissingBean
	@ConditionalOnProperty("app.amqp.notifications.exchange")
	NotificationService notificationService() {
		return new NotificationService();
	}
}
