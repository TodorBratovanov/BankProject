package com.starbank.model.dao;

import java.util.List;

import org.springframework.stereotype.Component;

import com.starbank.exceptions.UserException;
import com.starbank.model.entity.CurrentAccount;

@Component
public interface ICurrentAccountDAO {

	String SELECT_CURRENT_ACCOUNTS_ALL_SQL = "select * from accounts a, current_accounts c where a.user_id = ? and a.account_id = c.account_id;";

	public List<CurrentAccount> showCurrentAccounts (int userId) throws UserException;
	
}
