package com.starbank.model.dao.repo;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

import com.starbank.exceptions.AccountException;
import com.starbank.exceptions.UserException;
import com.starbank.model.dao.IAdminDAO;


public class AdminRepository implements IAdminDAO {
	
	private JdbcTemplate jdbcTemplate;

	public AdminRepository() {
	}

	@Autowired
	public AdminRepository(DataSource dataSource) {
		jdbcTemplate = new JdbcTemplate(dataSource);
	}

	public boolean confirmUserRegistration(String userEmail) throws UserException {
		try {
			this.jdbcTemplate.update(IAdminDAO.CONFIRM_USER_SQL, userEmail);
			return this.jdbcTemplate.queryForObject(IAdminDAO.SELECT_IS_USER_REGISTERED_SQL,
					new Object[] { userEmail }, Boolean.class);
		} catch (EmptyResultDataAccessException e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean deleteUser(int userId) throws UserException {
		try {
			this.jdbcTemplate.update(IAdminDAO.DELETE_USER_SQL, userId);
			return this.jdbcTemplate.queryForObject(IAdminDAO.SELECT_USER_ID_SQL, new Object[] { userId }, Boolean.class);
		} catch (EmptyResultDataAccessException e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean deleteAccount(int accountId) throws AccountException, UserException {
		try {
			int deletedAccounts = this.jdbcTemplate.update(IAdminDAO.DELETE_ACCOUNT_SQL, accountId);
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
