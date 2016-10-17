package com.starbank.model.dao;

import java.util.List;

import org.springframework.stereotype.Component;

import com.starbank.exceptions.UserException;
import com.starbank.model.entity.CurrentAccount;

@Component
public interface ICurrentAccountDAO {

	String SELECT_CURRENT_ACCOUNTS_ALL_SQL = "select * from accounts a, current_accounts c where a.user_id = ? and a.account_id = c.account_id;";
	String INSERT_CURRENT_ACCOUNT_SQL = "INSERT INTO Current_accounts VALUES (null, ?, ?)";
	String SELECT_COUNT_OF_USER_CURRENT_ACCOUNTS = "select count(*) from current_accounts c join accounts a on (c.account_id=a.account_id) "
			+ "join users u on (a.user_id=u.user_id) where u.email = ?;";

	public List<CurrentAccount> showCurrentAccounts(int userId) throws UserException;

}
