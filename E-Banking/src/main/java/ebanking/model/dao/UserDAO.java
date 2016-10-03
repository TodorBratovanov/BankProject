package ebanking.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalDateTime;

import ebanking.exceptions.AccountException;
import ebanking.exceptions.IbanException;
import ebanking.exceptions.UserException;
import ebanking.model.entity.Account;
import ebanking.model.entity.User;
import ebanking.validators.IbanValidator;

public class UserDAO {

	private static final String INSERT_USER_SQL = "INSERT INTO Users VALUES (null, ?, ?, ?, ?, ?, md5(?), ?, ?, ?, ?)";
	private static final String SELECT_USER_SQL = "SELECT user_id FROM Users WHERE email = ? AND password = md5(?);";
	private static final String SELECT_USER_ACCOUNT_SQL = "SELECT net_avlb_balance FROM Accounts WHERE account_id = ?";
	private static final String UPDATE_ACCOUNT_SQL = "UPDATE Accounts SET net_avlb_balance = ?, blocked_amount = ? WHERE account_id = ?";
	private static final String SELECT_RECEIVING_ACCOUNT_SQL = "SELECT account_id FROM Accounts WHERE iban = ?";
	private static final String UPDATE_RECIPIENT_ACCOUNT_ID = "UPDATE Accounts SET recipient_account_id = ? WHERE account_id = ?";

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

	public boolean loginUser(User user) throws UserException {
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

			return isRegistered;
		} catch (SQLException e) {
			throw new UserException("User login failed!");
		}
	}

	public void transferMoneyToOtherAccount(Account account, double moneyToTransfer, String iban)
			throws IbanException, AccountException {

		Connection connection = DBConnection.getInstance().getConnection();

		if (moneyToTransfer > 0) {
			if (IbanValidator.isValidIban(iban)) {
				try {
					connection.setAutoCommit(false);

					PreparedStatement ps = connection.prepareStatement(SELECT_USER_ACCOUNT_SQL);
					ps.setInt(1, account.getAccountId());
					ResultSet rs = ps.executeQuery();
					rs.next();
					double availableBalance = rs.getDouble(1);

					if (availableBalance >= moneyToTransfer) {
						ps = connection.prepareStatement(UPDATE_ACCOUNT_SQL);
						ps.setDouble(1, account.getNetAvlbBalance() - moneyToTransfer);
						ps.setDouble(2, moneyToTransfer);
						ps.setInt(3, account.getAccountId());
						ps.executeUpdate();

						ps = connection.prepareStatement(SELECT_RECEIVING_ACCOUNT_SQL);
						ps.setString(1, iban);
						rs = ps.executeQuery();

						int recipientAccountId;
						if (rs.next()) {
							recipientAccountId = rs.getInt(1);

							ps = connection.prepareStatement(UPDATE_RECIPIENT_ACCOUNT_ID);
							ps.setInt(1, account.getAccountId());
							ps.setInt(2, recipientAccountId);
							ps.executeUpdate();
						}

						connection.commit();
					} else {
						throw new AccountException("Insufficient funds");
					}
				} catch (SQLException e) {
					try {
						connection.rollback();
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
					throw new AccountException("Account not found");
				}
			}
		} else {
			throw new AccountException("Incorrect transfer sum! Must be positive.");
		}
	}

}
