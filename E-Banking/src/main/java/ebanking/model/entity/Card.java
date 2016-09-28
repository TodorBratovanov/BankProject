package ebanking.model.entity;

import java.time.LocalDateTime;

public class Card {

	private long cardId;
	private String name;
	private String type;
	private LocalDateTime issuedOn;
	private LocalDateTime validThrough;
	private Account account;

	public Card(long cardId, String name, String type, LocalDateTime issuedOn, LocalDateTime validThrough,
			Account account) {
		this.cardId = cardId;
		this.name = name;
		this.type = type;
		this.issuedOn = issuedOn;
		this.validThrough = validThrough;
		this.account = account;
	}
	
}
