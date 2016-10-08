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
import org.springframework.stereotype.Component;

import com.starbank.exceptions.UserException;
import com.starbank.model.dao.DBConnection;
import com.starbank.model.dao.IUserDAO;
import com.starbank.model.dao.mapper.UserMapper;
import com.starbank.model.entity.User;
@Component
public class UserRepository implements IUserDAO{

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
	@Override
	public int loginUser(String email, String password) throws UserException {
		int userId = 0;
		try {
			userId = jdbcTemplate.queryForObject(SELECT_USER_SQL, new Object[] { email, password }, Integer.class);
		} catch (EmptyResultDataAccessException e) {
			e.printStackTrace();
			return userId;
		}
		return userId;

	}
	@Override
	public int registerUser(User user) throws UserException {
		
		try {
			jdbcTemplate.update(SELECT_USER_SQL, user.getFirstName(), user.getMiddleName(), user.getLastName(), user.getPhoneNumber(),
					user.getEmail(), user.getPassword(), user.getAddress(), user.getEgn(), user.isAdmin(), user.isRegistered());
			
		} catch (EmptyResultDataAccessException e) {
			e.printStackTrace();
		}
		return jdbcTemplate.queryForObject(SELECT_USER_SQL, new Object[] { user.getEmail(), user.getPassword() }, Integer.class);
		
		

	}
	@Override
	public boolean isRegistrationConfirmed(String userEmail) throws SQLException, UserException {
		Connection connection = DBConnection.getInstance().getConnection();
		
		PreparedStatement ps = connection.prepareStatement(SELECT_ISREGISTERED_SQL);
		ps.setString(1, userEmail);
		ResultSet rs = ps.executeQuery();
		rs.next();
		
		return rs.getBoolean(1);
	}
	@Override
	public boolean isRegistered(String userEmail) throws SQLException, UserException {
		Connection connection = DBConnection.getInstance().getConnection();
		
		PreparedStatement ps = connection.prepareStatement(SELECT_USER_EMAIL_SQL);
		ps.setString(1, userEmail);
		ResultSet rs = ps.executeQuery();
		
		return rs.next();
	}

}
