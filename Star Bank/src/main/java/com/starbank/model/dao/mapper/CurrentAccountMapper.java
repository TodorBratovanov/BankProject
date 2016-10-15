package com.starbank.model.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.starbank.model.entity.CurrentAccount;

public class CurrentAccountMapper implements RowMapper<CurrentAccount> {

	@Override
	public CurrentAccount mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		CurrentAccount account = null;
		try {
			account = new CurrentAccount(rs.getInt("account_id"), rs.getDouble("net_avlb_balance"),
					rs.getDouble("current_balance"), rs.getDouble("blocked_amount"), rs.getString("iban"), rs.getInt("user_id"),
					rs.getString("currency"), rs.getInt("recipient_account_id"), rs.getInt("current_account_id"), 
					rs.getDouble("credit_limit"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return account;
		
	}



}
