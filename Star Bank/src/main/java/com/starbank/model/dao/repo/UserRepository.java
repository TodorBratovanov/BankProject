package com.starbank.model.dao.repo;

import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

import com.starbank.exceptions.UserException;
import com.starbank.model.dao.IUserDAO;
import com.starbank.model.entity.User;

public class UserRepository implements IUserDAO {

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
			userId = jdbcTemplate.queryForObject(IUserDAO.SELECT_USER_SQL, new Object[] { email, password }, Integer.class);
		} catch (EmptyResultDataAccessException e) {
			e.printStackTrace();
			return userId;
		}
		return userId;
	}
	
	@Override
	public int registerUser(User user) throws UserException {
		
		try {
			jdbcTemplate.update(IUserDAO.SELECT_USER_SQL, user.getFirstName(), user.getMiddleName(), user.getLastName(), user.getPhoneNumber(),
					user.getEmail(), user.getPassword(), user.getAddress(), user.getEgn(), user.isAdmin(), user.isRegistered());
			return jdbcTemplate.queryForObject(IUserDAO.SELECT_USER_SQL, new Object[] { user.getEmail(), user.getPassword() }, Integer.class);

		} catch (EmptyResultDataAccessException e) {
			e.printStackTrace();
			return 0;
		}
	}
	
	@Override
	public boolean isRegistrationConfirmed(String userEmail) throws SQLException, UserException {
		
		try {
			return jdbcTemplate.queryForObject(IUserDAO.SELECT_ISREGISTERED_SQL, new Object[] { userEmail }, Boolean.class);
		} catch (EmptyResultDataAccessException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	@Override
	public boolean isRegistered(String userEmail) throws SQLException, UserException {
		
		try {
			return jdbcTemplate.queryForObject(IUserDAO.SELECT_USER_EMAIL_SQL, new Object[] { userEmail }, Boolean.class);
		} catch (EmptyResultDataAccessException e) {
			e.printStackTrace();
			return false;
		}
	}

}
