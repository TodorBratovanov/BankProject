package com.starbank.model.entity;

import java.time.LocalDateTime;

import com.starbank.exceptions.DateTimeException;
import com.starbank.exceptions.InvalidStringException;
import com.starbank.validators.IValidator;

public class UserSession {

	private int sessionId;
	private LocalDateTime dateTime;
	private String description;
	private String ipAddress;

	public UserSession(int sessionId, LocalDateTime dateTime, String description, String ipAddress)
			throws DateTimeException, InvalidStringException {
		this.sessionId = sessionId;
		if (dateTime != null) {
			this.dateTime = dateTime;
		} else {
			throw new DateTimeException("Invalid date and time");
		}

		if (IValidator.isValidString(description)) {
			this.description = description;
		}
		this.ipAddress = ipAddress;
	}

}
