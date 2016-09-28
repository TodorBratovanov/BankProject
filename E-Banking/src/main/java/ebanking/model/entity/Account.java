package ebanking.model.entity;

import ebanking.exceptions.AccountException;
import ebanking.exceptions.IbanException;
import ebanking.exceptions.IdException;
import ebanking.exceptions.InvalidStringException;
import ebanking.validators.IValidator;
import ebanking.validators.IbanValidator;

public abstract class Account {

	private long accountId;
	private double netAvlbBalance;
	private double currentBalance;
	private double blockedAmount;
	private String iban;
	private long userId;
	private String currency;
	
	public Account(long accountId, double netAvlbBalance, double currentBalance, String iban,
			long userId, String currency) throws AccountException, IbanException, InvalidStringException, IdException {
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
		
	}
	
}
