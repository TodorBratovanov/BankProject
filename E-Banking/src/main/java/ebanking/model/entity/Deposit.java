package ebanking.model.entity;

import java.time.LocalDateTime;

import ebanking.exceptions.AccountException;
import ebanking.exceptions.DateTimeException;
import ebanking.exceptions.IbanException;
import ebanking.exceptions.IdException;
import ebanking.exceptions.InterestException;
import ebanking.exceptions.InvalidStringException;
import ebanking.validators.IValidator;

public class Deposit extends Account {

	private int depositId;
	private LocalDateTime dateOpen;
	private LocalDateTime maturity;
	private double interest;

	public Deposit(int accountId, double netAvlbBalance, double currentBalance, String iban, int userId,
			String currency, int depositId, LocalDateTime dateOpen, LocalDateTime maturity, double interest)
			throws AccountException, IbanException, InvalidStringException, IdException, DateTimeException, InterestException {
		super(accountId, netAvlbBalance, currentBalance, iban, userId, currency);

		if (IValidator.isPositive(depositId)) {
			this.depositId = depositId;
		} else {
			throw new IdException("Invalid deposit ID");
		}

		if (dateOpen != null && dateOpen.isBefore(LocalDateTime.now())) {
			this.dateOpen = dateOpen;
		} else {
			throw new DateTimeException("Incorrect deposit opening");
		}

		if (maturity != null && maturity.isAfter(LocalDateTime.now())) {
			this.maturity = maturity;
		} else {
			throw new DateTimeException("Incorrect deposit maturity");
		}

		if (IValidator.isValidInterest(interest)) {
			this.interest = interest;
		}
	}

}
