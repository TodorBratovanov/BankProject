package com.starbank.model.dao.repo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import com.starbank.exceptions.UserException;
import com.starbank.model.dao.DBConnection;
import com.starbank.model.dao.mapper.UserMapper;
import com.starbank.model.entity.User;

public class UserRepository {

	private static final String SELECT_USER_SQL = "SELECT user_id FROM Users WHERE email = ? AND password = md5(?);";
	private static final String SELECT_USERID_SQL = "SELECT user_id FROM users WHERE user_id = ?;";
	private static final String INSERT_USER_SQL = "INSERT INTO Users VALUES (null, ?, ?, ?, ?, ?, md5(?), ?, ?, ?, ?)";
	private static final String SELECT_ISREGISTERED_SQL = "SELECT registered FROM Users WHERE email = ?;";
	private static final String SELECT_USER_EMAIL_SQL = "SELECT * FROM Users WHERE email = ?;";

	private JdbcTemplate jdbcTemplate;
	
	public UserRepository() {
		// TODO Auto-generated constructor stub
	}
	
	@Autowired
	public UserRepository(DataSource dataSource) {
		jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	public int loginUser(String email, String password) throws UserException {
		int userId = 0;
		try {
			userId = jdbcTemplate.queryForObject(SELECT_USER_SQL, new Object[] { email, password }, Integer.class);
		} catch (EmptyResultDataAccessException e) {
			e.printStackTrace();
			return userId;
		}
		return userId;
//		Connection connection = DBConnection.getInstance().getConnection();
//
//		try {
//			PreparedStatement ps = connection.prepareStatement(c);
//			ps.setString(1, user.getEmail());
//			ps.setString(2, user.getPassword());
//			ResultSet rs = ps.executeQuery();
//			rs.next();
//			return rs.getInt(1);
//
//		} catch (SQLException e) {
//			e.printStackTrace();
//			throw new UserException("User login failed!");
//		}
	}
	
	public int registerUser(User user) throws UserException {
		
		try {
			jdbcTemplate.update(SELECT_USER_SQL, user.getFirstName(), user.getMiddleName(), user.getLastName(), user.getPhoneNumber(),
					user.getEmail(), user.getPassword(), user.getAddress(), user.getEgn(), user.isAdmin(), user.isRegistered());
			
		} catch (EmptyResultDataAccessException e) {
			e.printStackTrace();
		}
		return jdbcTemplate.queryForObject(SELECT_USER_SQL, new Object[] { user.getEmail(), user.getPassword() }, Integer.class);
		
		
//		Connection connection = DBConnection.getInstance().getConnection();
//
//		try {
//		
//			PreparedStatement ps = connection.prepareStatement(INSERT_USER_SQL, 
//					Statement.RETURN_GENERATED_KEYS);
//			
//			
//			ps.setString(1, user.getFirstName());
//			ps.setString(2, user.getMiddleName());
//			ps.setString(3, user.getLastName());
//			ps.setString(4, user.getPhoneNumber());
//			ps.setString(5, user.getEmail());
//			ps.setString(6, user.getPassword());
//			ps.setString(7, user.getAddress());
//			ps.setString(8, user.getEgn());
//			ps.setBoolean(9, user.isAdmin());
//			ps.setBoolean(10, user.isRegistered());
//			
//			ps.executeUpdate();
//
//			ResultSet rs = ps.getGeneratedKeys();
//			rs.next();
//			return rs.getInt(1);
//		} catch (SQLException e) {
//			e.printStackTrace();
//			throw new UserException("User registration failed!");
//		}
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
