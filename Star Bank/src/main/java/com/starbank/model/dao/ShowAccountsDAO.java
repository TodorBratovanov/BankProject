package com.starbank.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.starbank.exceptions.AccountException;
import com.starbank.exceptions.DateTimeException;
import com.starbank.exceptions.IbanException;
import com.starbank.exceptions.IdException;
import com.starbank.exceptions.InterestException;
import com.starbank.exceptions.InvalidStringException;
import com.starbank.exceptions.UserException;
import com.starbank.model.entity.Account;
import com.starbank.model.entity.Credit;
import com.starbank.model.entity.CurrentAccount;
import com.starbank.model.entity.Deposit;

public class ShowAccountsDAO {
	private static final String SELECT_CURRENT_ACCOUNTS_SQL = "SELECT ca.account_id, a.net_avlb_balance, a.current_balance, a.iban, a.user_id, "
			+ "a.currency FROM e_banking.accounts a JOIN e_banking.current_accounts ca on (ca.account_id = a.account_id) WHERE a.user_id = ?;";
	private static final String SELECT_DEPOSIT_ACCOUNTS_SQL = "SELECT a.account_id, a.net_avlb_balance, a.current_balance, a.iban, a.user_id, "
			+ "a.currency FROM e_banking.accounts a JOIN e_banking.deposits da on (da.account_id = a.account_id)" + " WHERE a.user_id = ?;";
	private static final String SELECT_CREDIT_ACCOUNTS_SQL = "SELECT a.account_id, a.net_avlb_balance, a.current_balance, a.iban, a.user_id, "
			+ "a.currency FROM e_banking.accounts a JOIN e_banking.credits ca on (ca.account_id = a.account_id)" + " WHERE a.user_id = ?;";

	
	public List<Account> showUserAccounts(int userId) throws UserException, AccountException, IbanException,
			InvalidStringException, IdException, DateTimeException, InterestException {

		Connection connection = DBConnection.getInstance().getConnection();
		List<Account> accounts = new ArrayList<Account>();
		try {

			PreparedStatement ps = connection.prepareStatement(SELECT_CURRENT_ACCOUNTS_SQL);
			ps.setInt(1, userId);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {

				accounts.add(new CurrentAccount(rs.getInt(1), rs.getDouble(2), rs.getDouble(3), rs.getString(4),
						rs.getInt(5), rs.getString(6)));

			}

			ps = connection.prepareStatement(SELECT_DEPOSIT_ACCOUNTS_SQL);
			ps.setInt(1, userId);
			rs = ps.executeQuery();

			while (rs.next()) {

				accounts.add(new Deposit(rs.getInt(1), rs.getDouble(2), rs.getDouble(3), rs.getString(4), rs.getInt(5),
						rs.getString(6)));

			}

			ps = connection.prepareStatement(SELECT_CREDIT_ACCOUNTS_SQL);
			ps.setInt(1, userId);
			rs = ps.executeQuery();

			while (rs.next()) {

				accounts.add(new Credit(rs.getInt(1), rs.getDouble(2), rs.getDouble(3), rs.getString(4), rs.getInt(5),
						rs.getString(6)));

			}
			return accounts;

		} catch (SQLException e) {
			e.getMessage();
			throw new UserException("Something went wrong!");
		}

	}

}
