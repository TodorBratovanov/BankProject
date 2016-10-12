package com.starbank.model.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

import com.starbank.exceptions.DateTimeException;
import com.starbank.exceptions.InvalidStringException;

public class UserSession {

	private int sessionId;
	private LocalDate dateTime;
	private String ipAddress;

	public UserSession(int sessionId, LocalDate dateTime, String description, String ipAddress)
			throws DateTimeException, InvalidStringException {
		this.sessionId = sessionId;
		if (dateTime != null) {
			this.dateTime = dateTime;
		} else {
			throw new DateTimeException("Invalid date and time");
		}

		this.ipAddress = ipAddress;
	}
	
}
