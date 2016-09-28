package ebanking.model.entity;

import ebanking.exceptions.AccountException;

public class CurrentAccount extends Account {

	private long currentAccountId;
	private double creditLimit;

	public CurrentAccount(long acountId, double netAvlbBalance, double currentBalance, double blockedAmount,
			String iban, long userId, String currency, long currentAccountId, double creditLimit) throws AccountException {
		super(acountId, netAvlbBalance, currentBalance, blockedAmount, iban, userId, currency);
		if(currentAccountId > 0) {
			this.currentAccountId = currentAccountId;
		} else {
			throw new AccountException("Invalid account ID");
		}
		if(creditLimit > 0) {
			this.creditLimit = creditLimit;
		} else {
			throw new AccountException("Credit limit must be positive");
		}
	}
	
}
