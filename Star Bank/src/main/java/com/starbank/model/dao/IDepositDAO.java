package com.starbank.model.dao;

import java.util.List;

import org.springframework.stereotype.Component;

import com.starbank.exceptions.UserException;
import com.starbank.model.entity.Deposit;

@Component
public interface IDepositDAO {

	String SELECT_DEPOSITS_ALL_SQL = "select * from accounts a, deposits d where a.user_id = ? and a.account_id = d.account_id;";

	public List<Deposit> showDeposits (int userId) throws UserException;
	
}
