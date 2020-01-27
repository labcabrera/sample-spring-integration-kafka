package com.cnp.arc.hodei.notifications.model;

import com.cnp.arc.hodei.model.actions.ActionExecutionResult;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NotificationMessage {

	private String id;

	private String module;

	private String entityType;

	private String actionType;

	private String actionData;

	private ActionExecutionResult result;

}
