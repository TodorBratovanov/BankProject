package ebanking.model.entity;

import java.time.LocalDateTime;

public class Credit extends Account {

	private long creditId;
	private double interest;
	private LocalDateTime expireDate;
	private double payment;

	public Credit(long acountId, double netAvlbBalance, double currentBalance, double blockedAmount, String iban,
			long userId, Currency currency) {
		super(acountId, netAvlbBalance, currentBalance, blockedAmount, iban, userId, currency);
		// TODO Auto-generated constructor stub
	}
	
}
