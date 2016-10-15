package com.starbank.model.dao.repo;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.support.TransactionTemplate;

import com.starbank.exceptions.UserException;
import com.starbank.model.dao.IAccountDAO;
import com.starbank.model.dao.ICurrentAccountDAO;
import com.starbank.model.dao.mapper.AccountMapper;
import com.starbank.model.dao.mapper.CurrentAccountMapper;
import com.starbank.model.entity.Account;
import com.starbank.model.entity.CurrentAccount;

public class CurrentAccountRepository implements ICurrentAccountDAO {

	private JdbcTemplate jdbcTemplate;

	public CurrentAccountRepository() {
		// TODO Auto-generated constructor stub
	}

	public CurrentAccountRepository(DataSource dataSource) {
		jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public List<CurrentAccount> showCurrentAccounts(int userId) throws UserException {

		List<CurrentAccount> accounts = new ArrayList<CurrentAccount>();
		try {
			accounts = this.jdbcTemplate.query(ICurrentAccountDAO.SELECT_CURRENT_ACCOUNTS_ALL_SQL, new Object[] { userId }, new CurrentAccountMapper());
		} catch (Exception e) {
			e.printStackTrace();
			throw new UserException("Something went wrong!");
		}

		return accounts;
	}

}
