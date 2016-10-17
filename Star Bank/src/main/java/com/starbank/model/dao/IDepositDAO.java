package com.starbank.model.dao;

import java.util.List;

import org.springframework.stereotype.Component;

import com.starbank.exceptions.UserException;
import com.starbank.model.entity.Deposit;

@Component
public interface IDepositDAO {

	String SELECT_DEPOSITS_ALL_SQL = "select * from accounts a, deposits d where a.user_id = ? and a.account_id = d.account_id;";
	String INSERT_DEPOSIT_SQL = "INSERT INTO Deposits VALUES (null, ?, ?, ?, ?)";
	String SELECT_COUNT_OF_USER_DEPOSITS = "select count(*) from deposits d join accounts a on (d.account_id=a.account_id) "
			+ "join users u on (a.user_id=u.user_id) where u.email = ?;";

	public List<Deposit> showDeposits (int userId) throws UserException;
	
}
