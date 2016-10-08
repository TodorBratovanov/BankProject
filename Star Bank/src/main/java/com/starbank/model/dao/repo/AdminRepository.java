package com.starbank.model.dao.repo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.support.TransactionTemplate;

import com.starbank.exceptions.AccountException;
import com.starbank.exceptions.UserException;
import com.starbank.model.dao.DBConnection;
import com.starbank.model.dao.IAdminDAO;
import com.starbank.model.entity.User;

public class AdminRepository {
	
	private JdbcTemplate jdbcTemplate;
	private TransactionTemplate transactionTemplate;

	public AdminRepository() {
		// TODO Auto-generated constructor stub
	}
	
	@Autowired
	public AdminRepository(DataSource dataSource, TransactionTemplate template) {
		jdbcTemplate = new JdbcTemplate(dataSource);
		transactionTemplate = template;
	}
	
	public boolean confirmUserRegistration(User user) throws UserException {
		Connection connection = DBConnection.getInstance().getConnection();

		try {
			PreparedStatement ps = connection.prepareStatement(IAdminDAO.CONFIRM_USER_SQL);
			ps.setLong(1, user.getUserId());
			ps.executeUpdate();
			ps = connection.prepareStatement(IAdminDAO.SELECT_IS_USER_REGISTERED_SQL, Statement.RETURN_GENERATED_KEYS);
			ps.setLong(1, user.getUserId());
			ps.executeQuery();
			user.setRegistered(true);
			return true;

		} catch (SQLException e) {
			throw new UserException("User registration failed!");
		}
	}

	public boolean deleteUser(int userId) throws UserException {
		Connection connection = DBConnection.getInstance().getConnection();

		try {
			PreparedStatement ps = connection.prepareStatement(IAdminDAO.DELETE_USER_SQL);
			ps.setInt(1, userId);
			int deletedRows = ps.executeUpdate();
			return deletedRows >= 1;
		} catch (Exception e) {
			throw new UserException("Something went wrong!");
		}
	}

	public boolean deleteAccount(int accountId) throws AccountException, UserException {
		Connection connection = DBConnection.getInstance().getConnection();

		try {
			PreparedStatement ps = connection.prepareStatement(IAdminDAO.DELETE_ACCOUNT_SQL);
			ps.setInt(1, accountId);
			int deletedAccounts = ps.executeUpdate();

			ps = connection.prepareStatement(IAdminDAO.SELECT_USER_ID_SQL);
			ps.setInt(1, accountId);
			ResultSet rs = ps.executeQuery();
			int resultSetUserId = rs.getInt(1);

			ps = connection.prepareStatement(IAdminDAO.NUMBER_OF_ACCOUNTS);
			ps.setInt(1, resultSetUserId);
			rs = ps.executeQuery();
			int numberOfAccounts = rs.getInt(1);

			if (numberOfAccounts <= 0) {
				deleteUser(resultSetUserId);
			}

			return deletedAccounts >= 1;

		} catch (SQLException e) {
			throw new AccountException("Account has been deleted!");
		}
	}

}
