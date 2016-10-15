package com.starbank.model.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.starbank.model.entity.Deposit;


public class DepositMapper implements RowMapper<Deposit> {

	@Override
	public Deposit mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		Deposit account = null;
		try {
			account = new Deposit(rs.getInt("account_id"), rs.getDouble("net_avlb_balance"),
					rs.getDouble("current_balance"), rs.getDouble("blocked_amount"), rs.getString("iban"), rs.getInt("user_id"),
					rs.getString("currency"), rs.getInt("recipient_account_id"), rs.getInt("deposit_id"),
					rs.getDate("date_open").toLocalDate(), rs.getDate("maturity").toLocalDate(), rs.getDouble("interest"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return account;
		
	}

}
