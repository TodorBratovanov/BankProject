package ebanking.model.entity;

public abstract class Account {

	private long acountId;
	private double netAvlbBalance;
	private double currentBalance;
	private double blockedAmount;
	private String iban;
	private long userId;
	private String currency;
	
	public Account(long acountId, double netAvlbBalance, double currentBalance, double blockedAmount, String iban,
			long userId, String currency) {
		super();
		this.acountId = acountId;
		this.netAvlbBalance = netAvlbBalance;
		this.currentBalance = currentBalance;
		this.blockedAmount = blockedAmount;
		this.iban = iban;
		this.userId = userId;
		this.currency = currency;
	}
	
}
