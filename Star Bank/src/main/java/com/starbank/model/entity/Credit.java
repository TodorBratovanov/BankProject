package com.starbank.model.entity;

import java.time.LocalDate;

import com.starbank.exceptions.AccountException;
import com.starbank.exceptions.DateTimeException;
import com.starbank.exceptions.IbanException;
import com.starbank.exceptions.IdException;
import com.starbank.exceptions.InterestException;
import com.starbank.exceptions.InvalidStringException;
import com.starbank.validators.IValidator;

public class Credit extends Account {
	
	private int creditId;
	private double interest;
	private LocalDate expireDate;
	private double payment;

	public Credit(int accountId, double netAvlbBalance, double currentBalance, double blockedAmount, String iban, int userId,
			String currency, int recipientAccountId, int creditId, double interest, LocalDate expireDate, double payment) 
					throws AccountException, IbanException, InvalidStringException, IdException, InterestException, DateTimeException {
		super(accountId, netAvlbBalance, currentBalance, blockedAmount, iban, userId, currency, recipientAccountId);
		
		if (IValidator.isPositive(creditId)) {
			this.creditId = creditId;
		}else {
			throw new IdException("Invalid credit id!");
		}
		if (IValidator.isValidInterest(interest)) {
			this.interest = interest;
		}
		if ((expireDate != null) && (expireDate.isAfter(LocalDate.now()))) {
			this.expireDate =  expireDate;
		}else {
			throw new DateTimeException("Incorrect date!");
		}
		if (IValidator.isPositive(payment)) {
			this.payment = payment;
		} else {
			throw new AccountException("Incorrect payment!");
		}
	}

	public Credit(int accountId, double netAvlbBalance, double currentBalance, double blockedAmount, String iban, int userId,
			String currency, int recipientAccountId) throws AccountException, IbanException, InvalidStringException, IdException {
		super(accountId, netAvlbBalance, currentBalance, blockedAmount, iban, userId, currency, recipientAccountId);
	}

	public int getCreditId() {
		return creditId;
	}

	public double getInterest() {
		return interest;
	}

	public LocalDate getExpireDate() {
		return expireDate;
	}

	public double getPayment() {
		return payment;
	}
	
}
