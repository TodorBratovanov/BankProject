package com.starbank.model.dao.repo;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.support.TransactionTemplate;

import com.starbank.exceptions.InvalidPasswordException;
import com.starbank.exceptions.UserException;
import com.starbank.model.dao.IUserDAO;
import com.starbank.model.dao.mapper.UserMapper;
import com.starbank.model.entity.User;
import com.starbank.validators.PasswordValidator;

public class UserRepository implements IUserDAO {

	private JdbcTemplate jdbcTemplate;
	private TransactionTemplate transactionTemplate;

	public UserRepository() {
	}

	@Autowired
	public UserRepository(DataSource dataSource, TransactionTemplate template) {
		jdbcTemplate = new JdbcTemplate(dataSource);
		transactionTemplate = template;
	}

	@Override
	public int loginUser(String email, String password) throws UserException {
		int userId = 0;
		try {
			User user = jdbcTemplate.queryForObject(IUserDAO.SELECT_USER_BY_EMAIL_SQL, new Object[] { email }, new UserMapper());
			if (BCrypt.checkpw(password, user.getPassword())) {
				return user.getUserId();
			}
		} catch (EmptyResultDataAccessException e) {
			e.printStackTrace();
			return userId;
		}
		return userId;
	}

	@Override
	public int registerUser(User user) throws UserException, InvalidPasswordException {
		PasswordValidator.isValidPassword(user.getPassword());
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String hashedPassword = passwordEncoder.encode(user.getPassword());
		try {
			jdbcTemplate.update(IUserDAO.INSERT_USER_SQL, user.getFirstName(), user.getMiddleName(), user.getLastName(),
					user.getPhoneNumber(), user.getEmail(), hashedPassword, user.getAddress(), user.getEgn(),
					user.isAdmin(), user.isRegistered(), user.isLiked());

			return jdbcTemplate.queryForObject(IUserDAO.SELECT_USER_SQL,
					new Object[] { user.getEmail(), hashedPassword }, Integer.class);

		} catch (EmptyResultDataAccessException e) {
			throw new UserException("Cannot register user!", e);
		}
	}

	@Override
	public boolean isRegistrationConfirmed(String userEmail) throws SQLException, UserException {
		try {
			return this.jdbcTemplate.queryForObject(IUserDAO.SELECT_ISREGISTERED_SQL, new Object[] { userEmail },
					Boolean.class);
		} catch (EmptyResultDataAccessException e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean isRegistered(String userEmail) throws SQLException, UserException {
		try {
			return jdbcTemplate.queryForObject(IUserDAO.SELECT_USER_EMAIL_SQL, new Object[] { userEmail },
					Boolean.class);
		} catch (EmptyResultDataAccessException e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public int countUsers() {
		int countedUsers = jdbcTemplate.queryForInt(IUserDAO.COUNT_USERS_SQL);
		return countedUsers;
	}

	@Override
	public int countLikes() {
		int countedLikes = jdbcTemplate.queryForInt(IUserDAO.COUNT_LIKES_SQL);
		return countedLikes;
	}

	@Override
	public void clickLike(int userId) {
		jdbcTemplate.update(IUserDAO.UPDATE_USER_LIKE_SQL, userId);
	}

	@Override
	public List<User> showUsersWaitingConfirmation() {
		List<User> users = new ArrayList<>();
		try {
			users = jdbcTemplate.query(IUserDAO.SELECT_USERS_WAITING_CONFIRMATION_SQL, new UserMapper());
		} catch (EmptyResultDataAccessException e) {
			e.printStackTrace();
			return users;
		}
		return users;
	}

	@Override
	public List<User> showAllUsers() {
		List<User> users = new ArrayList<>();
		try {
			users = jdbcTemplate.query(IUserDAO.SELECT_ALL_USERS_SQL, new UserMapper());
		} catch (EmptyResultDataAccessException e) {
			e.printStackTrace();
			return users;
		}
		return users;
	}
	
}
