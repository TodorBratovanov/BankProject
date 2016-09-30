package ebanking.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import ebanking.exceptions.UserException;
import ebanking.model.entity.User;

public class AdminDAO {

	private static final String CONFIRM_USER_SQL = "UPDATE Users SET registered = true WHERE user_id = ?";
	private static final String DELETE_USER_SQL = "DELETE FROM Users WHERE user_id = ?";
	private static final String SELECT_IS_USER_REGISTERED = "SELECT registered FROM Users WHERE user_id = ?";

	public void confirmUserRegistration(User user) throws UserException {
		Connection connection = DBConnection.getInstance().getConnection();
		
		try {
			PreparedStatement ps = connection.prepareStatement(CONFIRM_USER_SQL);
			ps.setLong(1, user.getUserId());
			ps.executeUpdate();
			ps = connection.prepareStatement(SELECT_IS_USER_REGISTERED, Statement.RETURN_GENERATED_KEYS);
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

}
