package com.starbank.model.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.starbank.model.entity.Credit;

public class CreditMapper implements RowMapper<Credit> {

	@Override
	public Credit mapRow(ResultSet rs, int rowNum) throws SQLException {

		Credit account = null;
		try {
			account = new Credit(rs.getInt("account_id"), rs.getDouble("net_avlb_balance"),
					rs.getDouble("current_balance"), rs.getDouble("blocked_amount"), rs.getString("iban"), rs.getInt("user_id"),
					rs.getString("currency"), rs.getInt("recipient_account_id"), rs.getInt("credit_id"), 
					rs.getDouble("interest"), rs.getDate("expire_date").toLocalDate(), rs.getDouble("payment"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return account;

	}
}
