package com.starbank.model.dao;

import java.util.List;
import java.util.Set;

import com.starbank.model.entity.Message;
import com.starbank.model.entity.Transaction;

public interface ITransactionDAO {

	static final String SELECT_TRANSACTIONS_SQL = "SELECT t.transaction_id, t.date,t.sender_iban,t.recipient_iban, t.amount, t.currency, t.account_id FROM e_banking.transactions t JOIN e_banking.accounts a ON(a.account_id = t.account_id) WHERE a.user_id = ? AND t.date BETWEEN ? AND ?;";

	public List<Transaction> getAllTransactions(int userId, String startDate, String endDate);

}
