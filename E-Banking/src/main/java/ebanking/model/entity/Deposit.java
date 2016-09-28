package ebanking.model.entity;

import java.time.LocalDateTime;

public class Deposit extends Account {

	private long depositId;
	private LocalDateTime dateOpen;
	private LocalDateTime maturity;
	private double interest;

	public Deposit(long acountId, double netAvlbBalance, double currentBalance, double blockedAmount, String iban,
			long userId, String currency) {
		super(acountId, netAvlbBalance, currentBalance, blockedAmount, iban, userId, currency);
		// TODO Auto-generated constructor stub
	}
	
}
