package com.starbank.model.entity;

import com.starbank.exceptions.AccountException;
import com.starbank.exceptions.IbanException;
import com.starbank.exceptions.IdException;
import com.starbank.exceptions.InvalidStringException;
import com.starbank.validators.IValidator;
import com.starbank.validators.IbanValidator;

public abstract class Account {

	private int accountId;
	private double netAvlbBalance;
	private double currentBalance;
	private double blockedAmount;
	private String iban;
	private int userId;
	private String currency;
	private int recipientAccountId;
	
	

	public Account(int accountId, double netAvlbBalance, double currentBalance, String iban, int userId,
			String currency, int recipientAccountId) throws AccountException, IbanException, InvalidStringException, IdException {
		this(iban);
		if (IValidator.isPositive(accountId)) {
			this.accountId = accountId;
		} else {
			throw new IdException("Invalid account id!");
		}
		if (IValidator.isPositive(netAvlbBalance)) {
			this.netAvlbBalance = netAvlbBalance;
		} else {
			throw new AccountException("Net available balance can not be negative!");
		}
		if (IValidator.isPositive(currentBalance)) {
			this.currentBalance = currentBalance;
		} else {
			throw new AccountException("Current balance can not be negative!");
		}

		if (IValidator.isValidString(iban) && (IbanValidator.isValidIban(iban))) {
			this.iban = iban;
		}
		if (IValidator.isPositive(userId)) {
			this.userId = userId;
		} else {
			throw new AccountException("Invalid user id!");
		}
		if (IValidator.isValidString(currency)) {
			this.currency = currency;
		} else {
			throw new AccountException("Invalid currency!");
		}
		if (IValidator.isPositive(recipientAccountId)) {
			this.recipientAccountId = recipientAccountId;
		}
	}
	
	public Account(String iban) throws InvalidStringException, IbanException {
		if (IValidator.isValidString(iban) && (IbanValidator.isValidIban(iban))) {
			this.iban = iban;
		}
	}

	public int getAccountId() {
		return accountId;
	}

	public double getNetAvlbBalance() {
		return netAvlbBalance;
	}

	public double getCurrentBalance() {
		return currentBalance;
	}

	public double getBlockedAmount() {
		return blockedAmount;
	}

	public String getIban() {
		return iban;
	}

	public int getUserId() {
		return userId;
	}

	public String getCurrency() {
		return currency;
	}
	public int getRecipientAccountId() {
		return recipientAccountId;
	}

	@Override
	public String toString() {
		return "Account [accountId=" + accountId + ", netAvlbBalance=" + netAvlbBalance + ", currentBalance="
				+ currentBalance + ", blockedAmount=" + blockedAmount + ", iban=" + iban + ", userId=" + userId
				+ ", currency=" + currency + ", recipientAccountId=" + recipientAccountId + "]";
	}
	
}
