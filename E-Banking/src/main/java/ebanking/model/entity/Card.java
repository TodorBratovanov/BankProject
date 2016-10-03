package ebanking.model.entity;

import java.time.LocalDateTime;

import ebanking.exceptions.AccountException;
import ebanking.exceptions.DateTimeException;
import ebanking.exceptions.IdException;
import ebanking.exceptions.InvalidStringException;
import ebanking.validators.IValidator;

public class Card {

	private int cardId;
	private String name;
	private String type;
	private LocalDateTime issuedOn;
	private LocalDateTime validThrough;
	private Account account;

	public Card(int cardId, String name, String type, LocalDateTime issuedOn, LocalDateTime validThrough,
			Account account) throws InvalidStringException, IdException, DateTimeException, AccountException {
		if (IValidator.isPositive(cardId)) {
			this.cardId = cardId;
		} else {
			throw new IdException("Invalid card ID");
		}
		if (IValidator.isValidString(name)) {
			this.name = name;
		}
		if (IValidator.isValidString(type)) {
			this.type = type;
		}
		if (issuedOn != null && issuedOn.isBefore(LocalDateTime.now())) {
			this.issuedOn = issuedOn;
		} else {
			throw new DateTimeException("Incorrect card issue on");
		}
		if (validThrough != null && validThrough.isAfter(LocalDateTime.now())) {
			this.validThrough = validThrough;
		} else {
			throw new DateTimeException("Incorrect card validation");
		}
		if (account != null) {
			this.account = account;
		} else {
			throw new AccountException("Account missing");
		}
	}

}
