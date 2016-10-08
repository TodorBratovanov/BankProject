package com.starbank.model.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.starbank.model.entity.Account;
import com.starbank.model.entity.CurrentAccount;

public class AccountMapper implements RowMapper<Account> {

	@Override
	public Account mapRow(ResultSet rs, int rowNum) throws SQLException {

		Account account = null;
		try {
			account = new CurrentAccount(rs.getInt("account_id"), rs.getDouble("net_avlb_balance"), rs.getDouble("current_balance"), 
					rs.getString("iban"), rs.getInt("user_id"), rs.getString("currency"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return account;
	}

}
