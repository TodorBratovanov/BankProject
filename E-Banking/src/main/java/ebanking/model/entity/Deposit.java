package ebanking.model.entity;

import java.time.LocalDateTime;

import ebanking.exceptions.AccountException;
import ebanking.exceptions.IbanException;
import ebanking.exceptions.IdException;
import ebanking.exceptions.InvalidStringException;

public class Deposit extends Account {

	private long depositId;
	private LocalDateTime dateOpen;
	private LocalDateTime maturity;
	private double interest;

	public Deposit(long acountId, double netAvlbBalance, double currentBalance, String iban,
			long userId, String currency) throws AccountException, IbanException, InvalidStringException, IdException {
		super(acountId, netAvlbBalance, currentBalance, iban, userId, currency);

		// TODO Auto-generated constructor stub
	}
	
}
