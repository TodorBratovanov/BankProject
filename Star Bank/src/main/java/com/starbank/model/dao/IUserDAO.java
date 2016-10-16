package com.starbank.model.dao;

import java.sql.SQLException;

import org.springframework.stereotype.Component;

import com.starbank.exceptions.UserException;
import com.starbank.model.entity.User;


public interface IUserDAO {

	static final String SELECT_USER_SQL = "SELECT user_id FROM Users WHERE email = ? AND password = md5(?);";
	static final String SELECT_USERID_SQL = "SELECT user_id FROM users WHERE user_id = ?;";
	static final String INSERT_USER_SQL = "INSERT INTO Users VALUES (null, ?, ?, ?, ?, ?, md5(?), ?, ?, ?, ?, ?)";
	static final String SELECT_ISREGISTERED_SQL = "SELECT registered FROM Users WHERE email = ?;";
	static final String SELECT_USER_EMAIL_SQL = "SELECT COUNT(*) FROM Users WHERE email = ?;";
	static final String COUNT_USERS_SQL = "SELECT COUNT(user_id) FROM Users;";
	static final String COUNT_LIKES_SQL = "SELECT COUNT(user_id) FROM e_banking.Users WHERE likes = true;";
	static final String UPDATE_USER_LIKE_SQL = "UPDATE Users SET likes=true WHERE user_id= ?;";

	public int loginUser(String email, String password) throws UserException;

	public int registerUser(User user) throws UserException;

	public boolean isRegistrationConfirmed(String userEmail) throws SQLException, UserException;

	public boolean isRegistered(String userEmail) throws SQLException, UserException;

	public int countUsers();
	
	public int countLikes();
	
	public void clickLike(int userId);
}
