package com.starbank.model.entity;

import com.starbank.exceptions.AccountException;
import com.starbank.exceptions.IbanException;
import com.starbank.exceptions.IdException;
import com.starbank.exceptions.InvalidStringException;
import com.starbank.validators.IValidator;

public class CurrentAccount extends Account {

	private int currentAccountId;
	private double creditLimit;

	public CurrentAccount(int acountId, double netAvlbBalance, double currentBalance,
			String iban, int userId, String currency, int currentAccountId, double creditLimit) 
					throws AccountException, IbanException, InvalidStringException, IdException {
		super(acountId, netAvlbBalance, currentBalance, iban, userId, currency);

		if(IValidator.isPositive(currentAccountId)) {
			this.currentAccountId = currentAccountId;
		} else {
			throw new AccountException("Invalid account ID");
		}
		if((IValidator.isPositive(creditLimit)) || (creditLimit == 0)) {
			this.creditLimit = creditLimit;
		} else {
			throw new AccountException("Invalid credit limit");
		}
	}

	public CurrentAccount(int accountId, double netAvlbBalance, double currentBalance, String iban, int userId,
			String currency) throws AccountException, IbanException, InvalidStringException, IdException {
		super(accountId, netAvlbBalance, currentBalance, iban, userId, currency);
	}
}
