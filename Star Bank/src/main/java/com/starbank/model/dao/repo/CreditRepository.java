package com.starbank.model.dao.repo;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.support.TransactionTemplate;

import com.starbank.exceptions.UserException;
import com.starbank.model.dao.ICreditDAO;
import com.starbank.model.dao.mapper.CreditMapper;
import com.starbank.model.entity.Credit;

public class CreditRepository implements ICreditDAO {

	private JdbcTemplate jdbcTemplate;

	public CreditRepository() {
		// TODO Auto-generated constructor stub
	}
	
	public CreditRepository(DataSource dataSource) {
		jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public List<Credit> showCredits(int userId) throws UserException {
		
		List<Credit> accounts = new ArrayList<Credit>();
		try {
			accounts = this.jdbcTemplate.query(ICreditDAO.SELECT_CREDITS_ALL_SQL, new Object[] { userId }, new CreditMapper());
		} catch (Exception e) {
			e.printStackTrace();
			throw new UserException("Something went wrong!");
		}

		return accounts;
	}
	
}
