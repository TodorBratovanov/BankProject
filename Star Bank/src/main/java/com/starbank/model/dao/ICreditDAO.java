package com.starbank.model.dao;

import java.util.List;

import org.springframework.stereotype.Component;

import com.starbank.exceptions.UserException;
import com.starbank.model.entity.Credit;

@Component
public interface ICreditDAO {

	String SELECT_CREDITS_ALL_SQL = "select * from accounts a, credits c where a.user_id = ? and a.account_id = c.account_id;";

	public List<Credit> showCredits (int userId) throws UserException;
	
}
