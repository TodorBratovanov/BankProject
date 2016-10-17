package com.starbank.model.entity;

import java.sql.Timestamp;

import com.starbank.exceptions.DateTimeException;
import com.starbank.exceptions.IdException;
import com.starbank.exceptions.InvalidStringException;
import com.starbank.validators.IValidator;

public class UserSession {

	private int sessionId;
	private Timestamp dateTime;
	private String description;
	private String ipAddress;
	private int userId;
	
	public UserSession(int sessionId, Timestamp dateTime, String description, String ipAddress, int userId)
			throws DateTimeException, InvalidStringException, IdException {
		if (IValidator.isPositive(sessionId)) {
			this.sessionId = sessionId;
		}else {
			throw new IdException("Incorrect session ID!");
		}
		if (dateTime != null) {
			this.dateTime = dateTime;
		} else {
			throw new DateTimeException("Invalid date and time");
		}
		if (IValidator.isValidString(ipAddress)) {
			this.ipAddress = ipAddress;
		}else {
			throw new InvalidStringException("Invalid IP Address");
		}
		if (IValidator.isPositive(userId)) {
			this.userId = userId;
		}else {
			throw new IdException("Incorrect user ID!");
		}
		if (IValidator.isValidString(description)) {
			this.description = description;
		}
	}

	public int getSessionId() {
		return sessionId;
	}

	public Timestamp getDateTime() {
		return dateTime;
	}

	public String getDescription() {
		return description;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public int getUserId() {
		return userId;
	}

}
