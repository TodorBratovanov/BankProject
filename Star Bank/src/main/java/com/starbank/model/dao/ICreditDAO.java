package com.starbank.model.dao;

import java.util.List;

import org.springframework.stereotype.Component;

import com.starbank.exceptions.UserException;
import com.starbank.model.entity.Credit;

@Component
public interface ICreditDAO {

	String SELECT_CREDITS_ALL_SQL = "select * from accounts a, credits c where a.user_id = ? and a.account_id = c.account_id;";
	String INSERT_CREDIT_SQL = "INSERT INTO Credits VALUES (null, ?, ?, ?, ?)";
	String SELECT_COUNT_OF_USER_CREDITS = "select count(*) from credits c join accounts a on (c.account_id=a.account_id) "
			+ "join users u on (a.user_id=u.user_id) where u.email = ?;";

	public List<Credit> showCredits (int userId) throws UserException;
	
}
