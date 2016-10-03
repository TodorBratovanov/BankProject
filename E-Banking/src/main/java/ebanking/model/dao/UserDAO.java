package ebanking.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;

import ebanking.exceptions.AccountException;
import ebanking.exceptions.IbanException;
import ebanking.exceptions.UserException;
import ebanking.model.entity.Account;
import java.util.ArrayList;
import java.util.List;

import ebanking.exceptions.AccountException;
import ebanking.exceptions.DateTimeException;
import ebanking.exceptions.IbanException;
import ebanking.exceptions.IdException;
import ebanking.exceptions.InterestException;
import ebanking.exceptions.InvalidStringException;
import ebanking.exceptions.UserException;
import ebanking.model.entity.Account;
import ebanking.model.entity.Credit;
import ebanking.model.entity.CurrentAccount;
import ebanking.model.entity.Deposit;
import ebanking.model.entity.User;
import ebanking.validators.IbanValidator;

public class UserDAO {

	private static final String INSERT_USER_SQL = "INSERT INTO Users VALUES (null, ?, ?, ?, ?, ?, md5(?), ?, ?, ?, ?)";
	private static final String SELECT_USER_SQL = "SELECT user_id FROM Users WHERE email = ? AND password = md5(?);";
	private static final String SELECT_USER_ACCOUNT_SQL = "SELECT * FROM Accounts WHERE account_id = ?";
	private static final String UPDATE_ACCOUNT_SQL = "UPDATE Accounts SET net_avlb_balance = ?, blocked_amount = ? WHERE account_id = ?";
	private static final String SELECT_RECEIVING_ACCOUNT_SQL = "SELECT account_id FROM Accounts WHERE iban = ?";
	private static final String UPDATE_RECIPIENT_ACCOUNT_ID = "UPDATE Accounts SET recipient_account_id = ? WHERE account_id = ?";
	private static final String SELECT_CURRENT_ACCOUNTS_SQL = "SELECT ca.account_id, a.net_avlb_balance, a.current_balance, a.iban, "
			+ "a.user_id, a.currency, ca.current_account_id, ca.credit_limit  FROM e_banking.accounts a "
			+ " JOIN e_banking.current_accounts ca on (ca.account_id = a.account_id)" + " WHERE a.user_id = ?;";
	private static final String SELECT_DEPOSIT_ACCOUNTS_SQL = "SELECT a.account_id, a.net_avlb_balance, a.current_balance, a.iban, "
			+ "a.user_id, a.currency, da.deposit_id, da.date_open, da.maturity, da.interest FROM e_banking.accounts a "
			+ " JOIN e_banking.deposits da on (da.account_id = a.account_id)" + " WHERE a.user_id = ?;";
	private static final String SELECT_CREDIT_ACCOUNTS_SQL = "SELECT a.account_id, a.net_avlb_balance, a.current_balance, a.iban, "
			+ "a.user_id, a.currency, ca.credit_id, ca.interest, ca.expitre_date, ca.payment FROM e_banking.accounts a "
			+ " JOIN e_banking.credits ca on (ca.account_id = a.account_id)" + " WHERE a.user_id = ?;";
	private static final String SELECT_ALL_ACCOUNTS_FOR_TRANSACTION_SQL = "SELECT account_id FROM Accounts WHERE blocked_amount > 0";
	private static final String FINALIZE_SENDER_TRANSACTION_SQL = "UPDATE Accounts SET current_balance = ?, blocked_amount = 0, "
			+ "recipient_account_id = null WHERE account_id = ?";
	private static final String FINALIZE_RECIPIENT_TRANSACTION_SQL = "UPDATE Accounts SET net_avlb_balance = ?, current_balance = ? "
			+ "WHERE account_id = ?";

	public int registerUser(User user) throws UserException {
		Connection connection = DBConnection.getInstance().getConnection();

		try {
			PreparedStatement ps = connection.prepareStatement(INSERT_USER_SQL, Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, user.getFirstName());
			ps.setString(2, user.getMiddleName());
			ps.setString(3, user.getLastName());
			ps.setString(4, user.getPhoneNumber());
			ps.setString(5, user.getEmail());
			ps.setString(6, user.getPassword());
			ps.setString(7, user.getAddress());
			ps.setString(8, user.getEgn());
			ps.setBoolean(9, user.isAdmin());
			ps.setBoolean(10, user.isRegistered());

			ps.executeUpdate();

			ResultSet rs = ps.getGeneratedKeys();
			rs.next();
			return rs.getInt(1);
		} catch (SQLException e) {

			throw new UserException("User registration failed!");
		}
	}

	public int loginUser(User user) throws UserException {

		Connection connection = DBConnection.getInstance().getConnection();

		try {
			PreparedStatement ps = connection.prepareStatement(SELECT_USER_SQL);
			ps.setString(1, user.getEmail());
			ps.setString(2, user.getPassword());
			ResultSet rs = ps.executeQuery();
			boolean isRegistered = rs.next();
			if (isRegistered == false) {
				throw new UserException("Registration not confirmed by administrator yet!");
			}

			return rs.getInt(1);
		} catch (SQLException e) {
			throw new UserException("User login failed!");
		}
	}

	public boolean transferMoneyToOtherAccount(Account account, double moneyToTransfer, String recipientIban)
			throws IbanException, AccountException {

		Connection connection = DBConnection.getInstance().getConnection();

		if (moneyToTransfer > 0) {
			if (IbanValidator.isValidIban(recipientIban)) {
				try {
					connection.setAutoCommit(false);

					PreparedStatement ps = connection.prepareStatement(SELECT_USER_ACCOUNT_SQL);
					ps.setInt(1, account.getAccountId());
					ResultSet rs = ps.executeQuery();
					rs.next();
					double availableBalance = rs.getDouble(2);
					double blockedAmount = rs.getDouble(4);

					if (availableBalance >= moneyToTransfer) {
						ps = connection.prepareStatement(UPDATE_ACCOUNT_SQL);
						ps.setDouble(1, availableBalance - moneyToTransfer);
						ps.setDouble(2, blockedAmount + moneyToTransfer);
						ps.setInt(3, account.getAccountId());
						ps.executeUpdate();

						ps = connection.prepareStatement(SELECT_RECEIVING_ACCOUNT_SQL);
						ps.setString(1, recipientIban);
						rs = ps.executeQuery();

						int recipientAccountId;
						if (rs.next()) {
							recipientAccountId = rs.getInt(1);

							ps = connection.prepareStatement(UPDATE_RECIPIENT_ACCOUNT_ID);
							ps.setInt(1, recipientAccountId);
							ps.setInt(2, account.getAccountId());
							ps.executeUpdate();
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
						rs.getInt(5), rs.getString(6), rs.getInt(7), rs.getDouble(8)));

			}

			ps = connection.prepareStatement(SELECT_DEPOSIT_ACCOUNTS_SQL);
			ps.setInt(1, userId);
			rs = ps.executeQuery();

			while (rs.next()) {

				accounts.add(new Deposit(rs.getInt(1), rs.getDouble(2), rs.getDouble(3), rs.getString(4), rs.getInt(5),
						rs.getString(6), rs.getInt(7), rs.getDate(8).toLocalDate(), rs.getDate(9).toLocalDate(),
						rs.getDouble(10)));

			}

			ps = connection.prepareStatement(SELECT_CREDIT_ACCOUNTS_SQL);
			ps.setInt(1, userId);
			rs = ps.executeQuery();

			while (rs.next()) {

				accounts.add(new Credit(rs.getInt(1), rs.getDouble(2), rs.getDouble(3), rs.getString(4), rs.getInt(5),
						rs.getString(6), rs.getInt(7), rs.getDouble(8), rs.getDate(9).toLocalDate(), rs.getDouble(10)));

			}
			return accounts;

		} catch (SQLException e) {
			e.getMessage();
			throw new UserException("Something went wrong!");
		}

	}

	public boolean finalizeAllUserTransactions() {

		Connection connection = DBConnection.getInstance().getConnection();

		try {
			connection.setAutoCommit(false);

			PreparedStatement ps = connection.prepareStatement(SELECT_ALL_ACCOUNTS_FOR_TRANSACTION_SQL);
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
				int accountId = rs.getInt(1);
				ps = connection.prepareStatement(SELECT_USER_ACCOUNT_SQL);
				ps.setInt(1, accountId);
				rs = ps.executeQuery();
				
				rs.next();
				double senderAvailableBalance = rs.getDouble(2);
				double senderBlockedAmount = rs.getDouble(4);
				Integer recipient = rs.getInt(8);
				
				ps = connection.prepareStatement(FINALIZE_SENDER_TRANSACTION_SQL);
				ps.setDouble(1, senderAvailableBalance);
				ps.setInt(2, accountId);
				ps.executeUpdate();
				
				if (recipient != null) {
					ps = connection.prepareStatement(SELECT_USER_ACCOUNT_SQL);
					ps.setInt(1, recipient);
					rs = ps.executeQuery();
					
					rs.next();
					double recipientAvailableBalance = rs.getDouble(2);
					double recipientCurrentBalance = rs.getInt(3);
					
					ps = connection.prepareStatement(FINALIZE_RECIPIENT_TRANSACTION_SQL);
					ps.setDouble(1, recipientAvailableBalance + senderBlockedAmount);
					ps.setDouble(2, recipientCurrentBalance + senderBlockedAmount);
					ps.setInt(3, recipient);
					ps.executeUpdate();
				}
				
				connection.commit();
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
		return false;
	}
	
}
