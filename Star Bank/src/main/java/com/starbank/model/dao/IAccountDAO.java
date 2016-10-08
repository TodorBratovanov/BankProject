package com.starbank.model.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.starbank.exceptions.AccountException;
import com.starbank.exceptions.DateTimeException;
import com.starbank.exceptions.IbanException;
import com.starbank.exceptions.IdException;
import com.starbank.exceptions.InterestException;
import com.starbank.exceptions.InvalidStringException;
import com.starbank.exceptions.UserException;
import com.starbank.model.entity.Account;

public interface IAccountDAO {
	
	static final String SELECT_CURRENT_ACCOUNTS_SQL = "SELECT ca.account_id, a.net_avlb_balance, a.current_balance, a.iban, a.user_id, "
			+ "a.currency FROM e_banking.accounts a JOIN e_banking.current_accounts ca on (ca.account_id = a.account_id) WHERE a.user_id = ?;";
	final String SELECT_DEPOSIT_ACCOUNTS_SQL = "SELECT a.account_id, a.net_avlb_balance, a.current_balance, a.iban, a.user_id, "
			+ "a.currency FROM e_banking.accounts a JOIN e_banking.deposits da on (da.account_id = a.account_id)"
			+ " WHERE a.user_id = ?;";
	final String SELECT_CREDIT_ACCOUNTS_SQL = "SELECT a.account_id, a.net_avlb_balance, a.current_balance, a.iban, a.user_id, "
			+ "a.currency FROM e_banking.accounts a JOIN e_banking.credits ca on (ca.account_id = a.account_id)"
			+ " WHERE a.user_id = ?;";
	final String SELECT_USER_ACCOUNT_SQL = "SELECT * FROM Accounts WHERE account_id = ?";
	final String SELECT_RECEIVING_ACCOUNT_SQL = "SELECT account_id FROM Accounts WHERE iban = ?";
	static final String UPDATE_RECIPIENT_ACCOUNT_ID = "UPDATE Accounts SET recipient_account_id = ? WHERE account_id = ?";
	static final String UPDATE_ACCOUNT_SQL = "UPDATE Accounts SET net_avlb_balance = ?, blocked_amount = ? WHERE account_id = ?";

	public List<Account> showUserAccounts(int userId) throws UserException, AccountException, IbanException,
			InvalidStringException, IdException, DateTimeException, InterestException;

	public boolean transferMoneyToOtherAccount(Account account, double moneyToTransfer, String recipientIban)
			throws IbanException, AccountException;

	public boolean transferMoneyToMyAccount(Account senderAccount, Account recipientAccount, double moneyToTransfer)
			throws IbanException, AccountException;

	public void updateRecipientAccount(Account senderAccount, Connection connection, int recipientAccountId)
			throws SQLException;

	public void updateCurrentAccount(Account senderAccount, double moneyToTransfer, Connection connection,
			double availableBalance, double blockedAmount) throws SQLException;

}
