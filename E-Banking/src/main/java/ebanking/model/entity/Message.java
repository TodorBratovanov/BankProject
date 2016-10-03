package ebanking.model.entity;

import java.time.LocalDateTime;

import ebanking.exceptions.DateTimeException;
import ebanking.exceptions.IdException;
import ebanking.exceptions.InvalidStringException;
import ebanking.validators.IValidator;

public class Message {

	private int messageId;
	private String title;
	private String text;
	private LocalDateTime date;
	
	
	public Message(int messageId, String title, String text, LocalDateTime date) throws IdException, InvalidStringException, DateTimeException {
		
		if (IValidator.isPositive(messageId)) {
			this.messageId = messageId;
		} else {
			throw new IdException("Incorrect message ID!");
		}
		
		if (IValidator.isValidString(title)) {
			this.title = title;
		} else {
			throw new InvalidStringException("Incorrect Title!");
		}
		
		if (IValidator.isValidString(text)) {
			this.text = text;
		} else {
			throw new InvalidStringException("Incorrect Text!");
		}
		
		if (date != null) {
			this.date = date;
			
		} else {
			throw new DateTimeException("Incorrect Date!");
		}
	}


	public long getMessageId() {
		return messageId;
	}


	public String getTitle() {
		return title;
	}


	public String getText() {
		return text;
	}


	public LocalDateTime getDate() {
		return date;
	}
	
}
