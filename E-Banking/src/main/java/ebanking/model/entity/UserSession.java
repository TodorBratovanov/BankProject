package ebanking.model.entity;

import java.time.LocalDateTime;

import ebanking.exceptions.DateTimeException;
import ebanking.exceptions.InvalidStringException;
import ebanking.validators.IValidator;

public class UserSession {

	private long sessionId;
	private LocalDateTime dateTime;
	private String description;
	private String ipAddress;

	public UserSession(long sessionId, LocalDateTime dateTime, String description, String ipAddress)
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
