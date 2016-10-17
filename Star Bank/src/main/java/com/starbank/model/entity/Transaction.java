package com.starbank.model.entity;

import java.sql.Timestamp;

import com.starbank.exceptions.AccountException;
import com.starbank.exceptions.DateTimeException;
import com.starbank.exceptions.IbanException;
import com.starbank.exceptions.IdException;
import com.starbank.exceptions.InvalidStringException;
import com.starbank.validators.IValidator;
import com.starbank.validators.IbanValidator;

public class Transaction {
	
	private int tansactionId;
	private Timestamp date;
	private String senderIban;
	private String recipientIban;
	private double amount;
	private String currency;
	private int accountId;

	public Transaction(int tansactionId, Timestamp date, String senderIban, String recipientIban, double amount,
			String currency, int accountId)
			throws IdException, InvalidStringException, AccountException, DateTimeException, IbanException {

		if (IValidator.isPositive(tansactionId)) {
			this.tansactionId = tansactionId;
		} else {
			throw new IdException("Invalid transaction id!");
		}
		if (date != null) {
			this.date = date;
		} else {
			throw new DateTimeException("Invalid date!");
		}
		if (IValidator.isValidString(senderIban) && IbanValidator.isValidIban(senderIban)) {
			this.senderIban = senderIban;
		} else {
			throw new IbanException("Invalid sender IBAN!");
		}
		if (IValidator.isValidString(recipientIban) && IbanValidator.isValidIban(recipientIban)) {
			this.recipientIban = recipientIban;
		} else {
			throw new IbanException("Invalid recipient IBAN!");
		}
		if (IValidator.isPositive(amount)) {
			this.amount = amount;
		} else {
			throw new AccountException("Invalid amount!");
		}
		if (IValidator.isValidString(currency)) {
			this.currency = currency;
		} else {
			throw new AccountException("Invalid currency!");
		}
		if (IValidator.isPositive(accountId)) {
			this.accountId = accountId;
		} else {
			throw new IdException("Invalid account id!");
		}
	}

	public int getTansactionId() {
		return tansactionId;
	}

	public Timestamp getDate() {
		return date;
	}

	public String getSenderIban() {
		return senderIban;
	}

	public String getRecipientIban() {
		return recipientIban;
	}

	public double getAmount() {
		return amount;
	}

	public String getCurrency() {
		return currency;
	}

	public int getAccountId() {
		return accountId;
	}

}
