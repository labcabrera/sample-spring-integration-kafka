package com.cnp.arc.hodei.notifications.gateway;

import com.cnp.arc.hodei.notifications.model.NotificationMessage;

public interface NotificationGateway {

	void sendError(NotificationMessage message);

}
