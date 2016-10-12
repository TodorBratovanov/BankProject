package com.starbank.model.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.starbank.exceptions.AccountException;
import com.starbank.exceptions.CardException;
import com.starbank.exceptions.DateTimeException;
import com.starbank.exceptions.IdException;
import com.starbank.exceptions.InvalidStringException;
import com.starbank.validators.CardNumberValidator;
import com.starbank.validators.IValidator;

public class Card {

	private int cardId;
	private String name;
	private String type;
	private String number;
	private LocalDate issuedOn;
	private LocalDate validThrough;
	private int accountId;

	public Card(int cardId, String name, String type, String number, LocalDate issuedOn, LocalDate validThrough,
			int account) throws InvalidStringException, IdException, DateTimeException, AccountException, CardException {
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
		if (CardNumberValidator.isValidCardNumber(number)) {
			this.number = number;
		}
		if (issuedOn != null && issuedOn.isBefore(LocalDate.now())) {
			this.issuedOn = issuedOn;
		} else {
			throw new DateTimeException("Incorrect card issue on");
		}
		if (validThrough != null && validThrough.isAfter(LocalDate.now())) {
			this.validThrough = validThrough;
		} else {
			throw new DateTimeException("Incorrect card validation");
		}
		if (IValidator.isPositive(account)) {
			this.accountId = account;
		} else {
			throw new AccountException("Account missing");
		}
	}

	public int getCardId() {
		return cardId;
	}

	public String getName() {
		return name;
	}

	public String getType() {
		return type;
	}

	public String getNumber() {
		return number;
	}

	public LocalDate getIssuedOn() {
		return issuedOn;
	}

	public LocalDate getValidThrough() {
		return validThrough;
	}

	public int getAccountId() {
		return accountId;
	}

}
