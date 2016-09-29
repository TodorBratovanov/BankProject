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

	public void confirmUserRegistration(long id) throws UserException {
		Connection connection = DBConnection.getInstance().getConnection();
		
		try {
			connection.setAutoCommit(false);
			
			PreparedStatement ps = connection.prepareStatement(CONFIRM_USER_SQL);
			ps.setLong(1, id);
			ps.executeUpdate();
			
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
