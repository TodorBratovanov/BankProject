package com.starbank.model.dao.repo;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;

import com.starbank.exceptions.UserException;
import com.starbank.model.dao.IDepositDAO;
import com.starbank.model.dao.mapper.DepositMapper;
import com.starbank.model.entity.Deposit;

public class DepositRepository implements IDepositDAO {

	private JdbcTemplate jdbcTemplate;

	public DepositRepository() {
	}
	
	public DepositRepository(DataSource dataSource) {
		jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public List<Deposit> showDeposits(int userId) throws UserException {
		List<Deposit> accounts = new ArrayList<Deposit>();
		try {
			accounts = this.jdbcTemplate.query(IDepositDAO.SELECT_DEPOSITS_ALL_SQL, new Object[] { userId }, new DepositMapper());
		} catch (Exception e) {
			e.printStackTrace();
			throw new UserException("Something went wrong!");
		}

		return accounts;
	}
	
}
