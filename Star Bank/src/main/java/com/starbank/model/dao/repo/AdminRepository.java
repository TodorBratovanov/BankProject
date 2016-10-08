package com.starbank.model.dao.repo;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.support.TransactionTemplate;

import com.starbank.exceptions.AccountException;
import com.starbank.exceptions.UserException;
import com.starbank.model.dao.IAdminDAO;
import com.starbank.model.entity.User;

public class AdminRepository {

	private JdbcTemplate jdbcTemplate;

	public AdminRepository() {
		// TODO Auto-generated constructor stub
	}

	@Autowired
	public AdminRepository(DataSource dataSource, TransactionTemplate template) {
		jdbcTemplate = new JdbcTemplate(dataSource);
	}

	public boolean confirmUserRegistration(User user) throws UserException {
		try {
			jdbcTemplate.update(IAdminDAO.CONFIRM_USER_SQL, user.getUserId());
			return jdbcTemplate.queryForObject(IAdminDAO.SELECT_IS_USER_REGISTERED_SQL,
					new Object[] { user.getUserId() }, Boolean.class);

		} catch (EmptyResultDataAccessException e) {
			e.printStackTrace();
			return false;
		}

	}

	public boolean deleteUser(int userId) throws UserException {
		try {
			jdbcTemplate.update(IAdminDAO.DELETE_USER_SQL, userId);
			return jdbcTemplate.queryForObject(IAdminDAO.SELECT_USER_ID_SQL, new Object[] { userId }, Boolean.class);
		} catch (EmptyResultDataAccessException e) {
			e.printStackTrace();
			return false;
		}

	}

	public boolean deleteAccount(int accountId) throws AccountException, UserException {
		try {
			int deletedAccounts = jdbcTemplate.update(IAdminDAO.DELETE_ACCOUNT_SQL, accountId);
			int resultSetUserId = jdbcTemplate.queryForInt(IAdminDAO.SELECT_USER_ID_SQL, accountId);
			int numberOfAccounts = jdbcTemplate.queryForInt(IAdminDAO.NUMBER_OF_ACCOUNTS, resultSetUserId);
			if (numberOfAccounts <= 0) {
				deleteUser(resultSetUserId);
			}

			return deletedAccounts >= 1;
		} catch (EmptyResultDataAccessException e) {
			e.printStackTrace();
			return false;
		}

	}

}
