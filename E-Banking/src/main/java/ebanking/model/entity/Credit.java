package ebanking.model.entity;

import java.time.LocalDateTime;

import ebanking.exceptions.AccountException;
import ebanking.exceptions.IbanException;

public class Credit extends Account {

	private long creditId;
	private double interest;
	private LocalDateTime expireDate;
	private double payment;

	public Credit(long acountId, double netAvlbBalance, double currentBalance, String iban,
			long userId, String currency) throws AccountException, IbanException {
		super(acountId, netAvlbBalance, currentBalance, iban, userId, currency);
		
		if (creditId > 0) {
			
		}
	}
	
}
