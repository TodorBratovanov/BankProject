package com.starbank.model.entity;

import java.sql.Timestamp;

import com.starbank.exceptions.DateTimeException;
import com.starbank.exceptions.IdException;
import com.starbank.exceptions.InvalidStringException;
import com.starbank.validators.IValidator;

public class Message {

	private int messageId;
	private String title;
	private String text;
	private Timestamp date;
	
	public Message(int messageId, String title, String text, Timestamp date) throws IdException, InvalidStringException, DateTimeException {
		
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

	public Message() {
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

	public Timestamp getDate() {
		return date;
	}

	public void setMessageId(int messageId) {
		this.messageId = messageId;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setText(String text) {
		this.text = text;
	}

	public void setDate(Timestamp date) {
		this.date = date;
	}

}
