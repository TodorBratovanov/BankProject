package com.starbank.model.entity;

import java.time.LocalDate;

import com.starbank.exceptions.AccountException;
import com.starbank.exceptions.DateTimeException;
import com.starbank.exceptions.IbanException;
import com.starbank.exceptions.IdException;
import com.starbank.exceptions.InterestException;
import com.starbank.exceptions.InvalidStringException;
import com.starbank.validators.IValidator;

public class Deposit extends Account {

	private int depositId;
	private LocalDate dateOpen;
	private LocalDate maturity;
	private double interest;

	public Deposit(int accountId, double netAvlbBalance, double currentBalance, double blockedAmount, String iban, int userId,
			String currency, int recipientAccountId, int depositId, LocalDate dateOpen, LocalDate maturity, double interest)
			throws AccountException, IbanException, InvalidStringException, IdException, DateTimeException, InterestException {
		super(accountId, netAvlbBalance, currentBalance, blockedAmount, iban, userId, currency, recipientAccountId);

		if (IValidator.isPositive(depositId)) {
			this.depositId = depositId;
		} else {
			throw new IdException("Invalid deposit ID");
		}
		if (dateOpen != null && dateOpen.isBefore(LocalDate.now())) {
			this.dateOpen = dateOpen;
		} else {
			throw new DateTimeException("Incorrect deposit opening");
		}
		if (maturity != null && maturity.isAfter(LocalDate.now())) {
			this.maturity = maturity;
		} else {
			throw new DateTimeException("Incorrect deposit maturity");
		}
		if (IValidator.isValidInterest(interest)) {
			this.interest = interest;
		}
	}

	public Deposit(int accountId, double netAvlbBalance, double currentBalance, double blockedAmount, String iban, int userId,
			String currency, int recipientAccountId) throws AccountException, IbanException, InvalidStringException, IdException {
		super(accountId, netAvlbBalance, currentBalance, blockedAmount, iban, userId, currency, recipientAccountId);
	}

	public int getDepositId() {
		return depositId;
	}

	public LocalDate getDateOpen() {
		return dateOpen;
	}

	public LocalDate getMaturity() {
		return maturity;
	}

	public double getInterest() {
		return interest;
	}
	
}
