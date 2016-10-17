package com.starbank.model.dao;

import org.springframework.stereotype.Component;

import com.starbank.exceptions.AccountException;
import com.starbank.exceptions.UserException;

@Component
public interface IAdminDAO {

	static final String NUMBER_OF_ACCOUNTS = "SELECT count(a.account_id) FROM accounts a JOIN users u ON(a.user_id = u.?);";
	static final String SELECT_USER_ID_SQL = "SELECT user_id FROM accounts WHERE account_id = ?;";
	static final String CONFIRM_USER_SQL = "UPDATE Users SET registered = true WHERE email = ?";
	static final String DELETE_USER_SQL = "DELETE FROM Users WHERE user_id = ?";
	static final String SELECT_IS_USER_REGISTERED_SQL = "SELECT registered FROM Users WHERE email = ?";
	static final String DELETE_ACCOUNT_SQL = "DELETE FROM Accounts WHERE account_id = ?";
	static final String SELECT_ACCOUNT_SQL = "SELECT FROM Accounts WHERE account_id = ?";

	
	public boolean confirmUserRegistration(String userEmail) throws UserException;

	public boolean deleteUser(int userId) throws UserException;

	public boolean deleteAccount(int accountId) throws AccountException, UserException;

}
