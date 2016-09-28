package ebanking.model.entity;

import java.time.LocalDateTime;

public class Card {

	private long cardId;
	private String name;
	private String type;
	private LocalDateTime issuedOn;
	private LocalDateTime validThrough;
	private Account acccount;
	
}
