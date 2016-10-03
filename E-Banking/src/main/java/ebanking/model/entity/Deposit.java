package ebanking.model.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import ebanking.exceptions.AccountException;
import ebanking.exceptions.DateTimeException;
import ebanking.exceptions.IbanException;
import ebanking.exceptions.IdException;
import ebanking.exceptions.InterestException;
import ebanking.exceptions.InvalidStringException;
import ebanking.validators.IValidator;

public class Deposit extends Account {

	private long depositId;
	private LocalDate dateOpen;
	private LocalDate maturity;
	private double interest;

	public Deposit(int accountId, double netAvlbBalance, double currentBalance, String iban, int userId,
			String currency, int depositId, LocalDate dateOpen, LocalDate maturity, double interest)
			throws AccountException, IbanException, InvalidStringException, IdException, DateTimeException, InterestException {
		super(accountId, netAvlbBalance, currentBalance, iban, userId, currency);

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

}
