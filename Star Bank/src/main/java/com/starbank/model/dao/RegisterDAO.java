package com.starbank.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.starbank.exceptions.UserException;
import com.starbank.model.entity.User;

public class RegisterDAO {
	
	private static final String INSERT_USER_SQL = "INSERT INTO Users VALUES (null, ?, ?, ?, ?, ?, md5(?), ?, ?, ?, ?)";
	private static final String SELECT_ISREGISTERED_SQL = "SELECT registered FROM Users WHERE email = ?;";
	private static final String SELECT_USER_EMAIL_SQL = "SELECT * FROM Users WHERE email = ?;";

	
	public int registerUser(User user) throws UserException {
		Connection connection = DBConnection.getInstance().getConnection();

		try {
		
			PreparedStatement ps = connection.prepareStatement(INSERT_USER_SQL, 
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

			ResultSet rs = ps.getGeneratedKeys();
			rs.next();
			return rs.getInt(1);
		} catch (SQLException e) {

			throw new UserException("User registration failed!");
		}
	}
	
	public boolean isRegistrationConfirmed(String userEmail) throws SQLException, UserException {
		Connection connection = DBConnection.getInstance().getConnection();
		
		PreparedStatement ps = connection.prepareStatement(SELECT_ISREGISTERED_SQL);
		ps.setString(1, userEmail);
		ResultSet rs = ps.executeQuery();
		rs.next();
		
		return rs.getBoolean(1);
	}
	
	public boolean isRegistered(String userEmail) throws SQLException, UserException {
		Connection connection = DBConnection.getInstance().getConnection();
		
		PreparedStatement ps = connection.prepareStatement(SELECT_USER_EMAIL_SQL);
		ps.setString(1, userEmail);
		ResultSet rs = ps.executeQuery();
		
		return rs.next();
	}
	
}
