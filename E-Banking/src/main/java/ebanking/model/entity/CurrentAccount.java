package ebanking.model.entity;

import ebanking.exceptions.AccountException;
import ebanking.exceptions.IbanException;
import ebanking.exceptions.IdException;
import ebanking.exceptions.InvalidStringException;
import ebanking.validators.IValidator;

public class CurrentAccount extends Account {

	private long currentAccountId;
	private double creditLimit;

	public CurrentAccount(long acountId, double netAvlbBalance, double currentBalance,
			String iban, long userId, String currency, long currentAccountId, double creditLimit) 
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
	
}
