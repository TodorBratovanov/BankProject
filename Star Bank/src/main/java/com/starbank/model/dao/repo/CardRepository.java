package com.starbank.model.dao.repo;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.support.TransactionTemplate;

import com.starbank.exceptions.UserException;
import com.starbank.model.dao.ICardDAO;
import com.starbank.model.dao.mapper.CardMapper;
import com.starbank.model.entity.Card;

public class CardRepository implements ICardDAO{

	private JdbcTemplate jdbcTemplate;

	public CardRepository() {
		// TODO Auto-generated constructor stub
	}

	public CardRepository(DataSource dataSource) {
		jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public List<Card> showCards(int userId) throws UserException {

		List<Card> accounts = new ArrayList<Card>();
		try {
			accounts = this.jdbcTemplate.query(ICardDAO.SELECT_CARDS_ALL_SQL, new Object[] { userId }, new CardMapper());
		} catch (Exception e) {
			e.printStackTrace();
			throw new UserException("Something went wrong!");
		}

		return accounts;
	}
	
}
