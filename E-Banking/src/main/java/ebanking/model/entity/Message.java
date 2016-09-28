package ebanking.model.entity;

import java.time.LocalDateTime;

public class Message {

	private long messageId;
	private String title;
	private String text;
	private LocalDateTime date;

	public Message(long messageId, String title, String text, LocalDateTime date) {
		this.messageId = messageId;
		this.title = title;
		this.text = text;
		this.date = date;
	}

}
