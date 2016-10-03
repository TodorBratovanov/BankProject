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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (accountId ^ (accountId >>> 32));
		long temp;
		temp = Double.doubleToLongBits(blockedAmount);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((currency == null) ? 0 : currency.hashCode());
		temp = Double.doubleToLongBits(currentBalance);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((iban == null) ? 0 : iban.hashCode());
		temp = Double.doubleToLongBits(netAvlbBalance);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + (int) (userId ^ (userId >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Account other = (Account) obj;
		if (accountId != other.accountId)
			return false;
		if (Double.doubleToLongBits(blockedAmount) != Double.doubleToLongBits(other.blockedAmount))
			return false;
		if (currency == null) {
			if (other.currency != null)
				return false;
		} else if (!currency.equals(other.currency))
			return false;
		if (Double.doubleToLongBits(currentBalance) != Double.doubleToLongBits(other.currentBalance))
			return false;
		if (iban == null) {
			if (other.iban != null)
				return false;
		} else if (!iban.equals(other.iban))
			return false;
		if (Double.doubleToLongBits(netAvlbBalance) != Double.doubleToLongBits(other.netAvlbBalance))
			return false;
		if (userId != other.userId)
			return false;
		return true;
	}
	
}
