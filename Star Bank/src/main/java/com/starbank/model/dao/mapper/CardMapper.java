package com.starbank.model.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.starbank.model.entity.Card;

public class CardMapper implements RowMapper<Card> {

	@Override
	public Card mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		Card account = null;
		try {
			account = new Card(rs.getInt("card_id"), rs.getString("name"), rs.getString("type"), rs.getString("number"), 
					rs.getDate("issued_on").toLocalDate(), rs.getDate("valid_through").toLocalDate(), rs.getInt("account_id"), 
					rs.getString("iban"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return account;
		
	}
}
