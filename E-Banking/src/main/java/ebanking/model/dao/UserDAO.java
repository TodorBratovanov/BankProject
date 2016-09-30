package ebanking.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import ebanking.exceptions.UserException;
import ebanking.model.entity.User;

public class UserDAO {
	
	private static final String INSERT_USER_SQL = "INSERT INTO Users VALUES (null, ?, ?, ?, ?, ?, md5(?), ?, ?, ?, ?)";
	private static final String SELECT_USER_SQL = "SELECT user_id FROM Users WHERE email = ? AND password = md5(?);";

	
	public int registerUser(User user) throws UserException {
		Connection connection = DBConnection.getInstance().getConnection();
		
		try {
			PreparedStatement ps = 
					connection.prepareStatement(INSERT_USER_SQL, 
							Statement.RETURN_GENERATED_KEYS);
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
			
			ResultSet rs  = ps.getGeneratedKeys();
			rs.next();
			return rs.getInt(1);
		} catch (SQLException e) {
			
			throw new UserException("User registration failed!");
		}
	}
	
	public boolean loginUser(User user) throws UserException {
		Connection connection = DBConnection.getInstance().getConnection();
		
		try {
			PreparedStatement ps = 
					connection.prepareStatement(SELECT_USER_SQL);
			ps.setString(1, user.getEmail());
			ps.setString(2, user.getPassword());
			ResultSet rs = ps.executeQuery();
			boolean isRegistered = rs.next();
			if (isRegistered == false) {
				throw new UserException("User login failed!");
			}

			return isRegistered;
		}
		catch (SQLException e) {
			throw new UserException("Something went wrong!");
		}
	}
	
	
}
