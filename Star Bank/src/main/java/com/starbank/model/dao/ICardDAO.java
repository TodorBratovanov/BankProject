package com.starbank.model.dao;

import java.util.List;

import org.springframework.stereotype.Component;

import com.starbank.exceptions.UserException;
import com.starbank.model.entity.Card;

@Component
public interface ICardDAO {

	String SELECT_CARDS_ALL_SQL = "select * from accounts a, cards c where a.user_id = ? and a.account_id = c.account_id;";
	String INSERT_CARD_SQL = "INSERT INTO Cards VALUES (null, ?, ?, ?, ?, ?, ?)";

	public List<Card> showCards (int userId) throws UserException;
	
}
