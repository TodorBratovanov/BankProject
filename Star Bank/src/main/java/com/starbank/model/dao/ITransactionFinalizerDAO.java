package com.starbank.model.dao;

import org.springframework.stereotype.Component;

@Component
public interface ITransactionFinalizerDAO {

	static final String SELECT_ALL_ACCOUNTS_FOR_TRANSACTION_SQL = "SELECT * FROM Accounts WHERE blocked_amount > 0";
	static final String SELECT_USER_ACCOUNT_SQL = "SELECT * FROM Accounts WHERE account_id = ?";
	static final String FINALIZE_SENDER_TRANSACTION_SQL = "UPDATE Accounts SET current_balance = ?, blocked_amount = 0, "
			+ "recipient_account_id = null WHERE account_id = ?";
	static final String FINALIZE_RECIPIENT_TRANSACTION_SQL = "UPDATE Accounts SET net_avlb_balance = ?, current_balance = ? "
			+ "WHERE account_id = ?";

	public boolean finalizeAllUserTransactions();

}
