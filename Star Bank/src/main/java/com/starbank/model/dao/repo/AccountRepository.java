package com.starbank.model.dao.repo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.support.TransactionTemplate;

import com.starbank.exceptions.AccountException;
import com.starbank.exceptions.DateTimeException;
import com.starbank.exceptions.IbanException;
import com.starbank.exceptions.IdException;
import com.starbank.exceptions.InterestException;
import com.starbank.exceptions.InvalidStringException;
import com.starbank.exceptions.UserException;
import com.starbank.model.dao.DBConnection;
import com.starbank.model.dao.IAccountDAO;
import com.starbank.model.entity.Account;
import com.starbank.model.entity.Credit;
import com.starbank.model.entity.CurrentAccount;
import com.starbank.model.entity.Deposit;
import com.starbank.validators.IbanValidator;

public class AccountRepository {

	private JdbcTemplate jdbcTemplate;
	private TransactionTemplate transactionTemplate;
	
	public AccountRepository() {
		// TODO Auto-generated constructor stub
	}
	
	@Autowired
	public AccountRepository(DataSource dataSource, TransactionTemplate template) {
		jdbcTemplate = new JdbcTemplate(dataSource);
		transactionTemplate = template;
	}
	
	public List<Account> showUserAccounts(int userId) throws UserException, AccountException, IbanException,
			InvalidStringException, IdException, DateTimeException, InterestException {

		Connection connection = DBConnection.getInstance().getConnection();
		List<Account> accounts = new ArrayList<Account>();
		try {

			PreparedStatement ps = connection.prepareStatement(IAccountDAO.SELECT_CURRENT_ACCOUNTS_SQL);
			ps.setInt(1, userId);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {

				accounts.add(new CurrentAccount(rs.getInt(1), rs.getDouble(2), rs.getDouble(3), rs.getString(4),
						rs.getInt(5), rs.getString(6)));

			}

			ps = connection.prepareStatement(IAccountDAO.SELECT_DEPOSIT_ACCOUNTS_SQL);
			ps.setInt(1, userId);
			rs = ps.executeQuery();

			while (rs.next()) {

				accounts.add(new Deposit(rs.getInt(1), rs.getDouble(2), rs.getDouble(3), rs.getString(4), rs.getInt(5),
						rs.getString(6)));

			}

			ps = connection.prepareStatement(IAccountDAO.SELECT_CREDIT_ACCOUNTS_SQL);
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

	public boolean transferMoneyToOtherAccount(Account account, double moneyToTransfer, String recipientIban)
			throws IbanException, AccountException {

		Connection connection = DBConnection.getInstance().getConnection();

		if (moneyToTransfer > 0) {
			if (IbanValidator.isValidIban(recipientIban)) {
				try {
					connection.setAutoCommit(false);

					PreparedStatement ps = connection.prepareStatement(IAccountDAO.SELECT_USER_ACCOUNT_SQL);
					ps.setInt(1, account.getAccountId());
					ResultSet rs = ps.executeQuery();
					rs.next();
					double availableBalance = rs.getDouble(2);
					double blockedAmount = rs.getDouble(4);

					if (availableBalance >= moneyToTransfer) {
						updateCurrentAccount(account, moneyToTransfer, connection, availableBalance,
								blockedAmount);

						ps = connection.prepareStatement(IAccountDAO.SELECT_RECEIVING_ACCOUNT_SQL);
						ps.setString(1, recipientIban);
						rs = ps.executeQuery();

						int recipientAccountId;
						if (rs.next()) {
							recipientAccountId = rs.getInt(1);

							updateRecipientAccount(account, connection, recipientAccountId);
						}

						connection.commit();
					} else {
						connection.rollback();
						throw new AccountException("Insufficient funds");
					}
					return true;
				} catch (SQLException e) {
					try {
						connection.rollback();
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
					e.printStackTrace();
				}
			}
		} else {
			throw new AccountException("Incorrect transfer sum! Must be positive.");
		}
		return false;
	}

	public boolean transferMoneyToMyAccount(Account senderAccount, Account recipientAccount, double moneyToTransfer)
			throws IbanException, AccountException {

		Connection connection = DBConnection.getInstance().getConnection();

		if (moneyToTransfer > 0) {
			if ((IbanValidator.isValidIban(senderAccount.getIban()))
					&& (IbanValidator.isValidIban(senderAccount.getIban()))) {
				try {
					connection.setAutoCommit(false);

					PreparedStatement ps = connection.prepareStatement(IAccountDAO.SELECT_USER_ACCOUNT_SQL);
					ps.setInt(1, senderAccount.getAccountId());
					ResultSet rs = ps.executeQuery();
					rs.next();
					double availableBalance = rs.getDouble(1);
					double blockedAmount = rs.getDouble(2);

					if (availableBalance >= moneyToTransfer) {
						updateCurrentAccount(senderAccount, moneyToTransfer, connection,
								availableBalance, blockedAmount);

						int recipientAccountId = recipientAccount.getAccountId();

						updateRecipientAccount(senderAccount, connection, recipientAccountId);

						connection.commit();
						
					} else {
						connection.rollback();
						throw new AccountException("Insufficient funds");
					}
					return true;
				} catch (SQLException e) {
					try {
						connection.rollback();
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
					e.printStackTrace();
				}
			}
		} else {
			throw new AccountException("Incorrect transfer sum! Must be positive.");
		}
		return false;
	}

	public static void updateRecipientAccount(Account senderAccount, Connection connection, int recipientAccountId)
			throws SQLException {
		PreparedStatement ps;
		ps = connection.prepareStatement(IAccountDAO.UPDATE_RECIPIENT_ACCOUNT_ID);
		ps.setInt(1, recipientAccountId);
		ps.setInt(2, senderAccount.getAccountId());
		ps.executeUpdate();
	}

	public static void updateCurrentAccount(Account senderAccount, double moneyToTransfer, Connection connection,
			double availableBalance, double blockedAmount) throws SQLException {
		PreparedStatement ps = connection.prepareStatement(IAccountDAO.UPDATE_ACCOUNT_SQL);
		ps.setDouble(1, availableBalance - moneyToTransfer);
		ps.setDouble(2, blockedAmount + moneyToTransfer);
		ps.setInt(3, senderAccount.getAccountId());
		ps.executeUpdate();
	}

}
