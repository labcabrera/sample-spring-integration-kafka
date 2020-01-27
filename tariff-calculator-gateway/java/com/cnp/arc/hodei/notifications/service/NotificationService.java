package com.cnp.arc.hodei.notifications.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.cnp.arc.hodei.core.service.ModuleSupplier;
import com.cnp.arc.hodei.notifications.gateway.NotificationGateway;
import com.cnp.arc.hodei.notifications.model.NotificationMessage;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class NotificationService {

	@Autowired
	private NotificationGateway gateway;

	@Autowired
	private ModuleSupplier moduleSupplier;

	public void send(NotificationMessage message) {
		try {
			gateway.sendError(message);
		}
		catch (Exception ex) {
			log.error("Error sending notification message: {}", ex.getMessage(), ex);
		}
	}

	public void sendError(String msg, Exception ex) {
		NotificationMessage message = NotificationMessage.builder()
			.module(moduleSupplier.get())
			.entityType("error")
			.actionType("error")
			.actionData(msg + ": " + ex.getMessage())
			//TODO
			.build();
		send(message);
	}

}
