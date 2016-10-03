package ebanking.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;

import ebanking.exceptions.MessageException;
import ebanking.exceptions.UserException;
import ebanking.model.entity.Message;
import ebanking.model.entity.User;

public class AdminDAO {

	private static final String CONFIRM_USER_SQL = "UPDATE Users SET registered = true WHERE user_id = ?";
	private static final String DELETE_USER_SQL = "DELETE FROM Users WHERE user_id = ?";
	private static final String SELECT_IS_USER_REGISTERED_SQL = "SELECT registered FROM Users WHERE user_id = ?";
	private static final String INSERT_MESSAGE_SQL = "INSERT INTO Messages VALUES (null, ?, ?, ?, ?)";
	private static final String DELETE_ACCOUNT_SQL = "DELETE FROM Accounts WHERE account_id = ?";

	public void confirmUserRegistration(User user) throws UserException {
		Connection connection = DBConnection.getInstance().getConnection();

		try {
			PreparedStatement ps = connection.prepareStatement(CONFIRM_USER_SQL);
			ps.setLong(1, user.getUserId());
			ps.executeUpdate();
			ps = connection.prepareStatement(SELECT_IS_USER_REGISTERED_SQL, Statement.RETURN_GENERATED_KEYS);
			ps.setLong(1, user.getUserId());
			ps.executeQuery();
			user.setRegistered(true);

		} catch (SQLException e) {
			throw new UserException("User registration failed!");
		}
	}

	public boolean deleteUser(int userId) throws UserException {
		Connection connection = DBConnection.getInstance().getConnection();

		try {
			PreparedStatement ps = connection.prepareStatement(DELETE_USER_SQL);
			ps.setInt(1, userId);
			int deletedRows = ps.executeUpdate();
			return deletedRows >= 1;
		} catch (Exception e) {
			throw new UserException("Something went wrong!");
		}
	}

	public void sendMessageToUser(Message message, int userId) throws MessageException {
		Connection connection = DBConnection.getInstance().getConnection();

		try {
			PreparedStatement ps = connection.prepareStatement(INSERT_MESSAGE_SQL);
			ps.setString(1, message.getTitle());
			ps.setString(2, message.getText());
			ps.setTimestamp(3, Timestamp.valueOf(LocalDateTime.now()));
			ps.setInt(4, userId);

			ps.executeUpdate();

		} catch (SQLException e) {
			throw new MessageException("Sending message failed");
		}
	}

	public boolean deleteAccount(int accountId) throws MessageException {
		Connection connection = DBConnection.getInstance().getConnection();

		try {
			PreparedStatement ps = connection.prepareStatement(DELETE_ACCOUNT_SQL);
			ps.setInt(1, accountId);
			
			int deletedAccounts = ps.executeUpdate();
			return deletedAccounts >= 1;
			
		} catch (SQLException e) {
			throw new MessageException("Sending message failed");
		}
	}

}
