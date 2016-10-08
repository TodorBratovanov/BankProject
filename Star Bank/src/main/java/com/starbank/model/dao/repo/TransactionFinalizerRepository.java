package com.starbank.model.dao.repo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.support.TransactionTemplate;

import com.starbank.model.dao.DBConnection;
import com.starbank.model.dao.ITransactionFinalizerDAO;

public class TransactionFinalizerRepository {

	private JdbcTemplate jdbcTemplate;
	private TransactionTemplate transactionTemplate;

	public TransactionFinalizerRepository() {
		// TODO Auto-generated constructor stub
	}
	
	@Autowired
	public TransactionFinalizerRepository(DataSource dataSource, TransactionTemplate template) {
		jdbcTemplate = new JdbcTemplate(dataSource);
		transactionTemplate = template;
	}
	
	public boolean finalizeAllUserTransactions() {

		Connection connection = DBConnection.getInstance().getConnection();

		try {
			connection.setAutoCommit(false);

			PreparedStatement ps = connection
					.prepareStatement(ITransactionFinalizerDAO.SELECT_ALL_ACCOUNTS_FOR_TRANSACTION_SQL);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				int accountId = rs.getInt(1);
				ps = connection.prepareStatement(ITransactionFinalizerDAO.SELECT_USER_ACCOUNT_SQL);
				ps.setInt(1, accountId);
				rs = ps.executeQuery();

				rs.next();
				double senderAvailableBalance = rs.getDouble(2);
				double senderBlockedAmount = rs.getDouble(4);
				Integer recipient = rs.getInt(8);

				ps = connection.prepareStatement(ITransactionFinalizerDAO.FINALIZE_SENDER_TRANSACTION_SQL);
				ps.setDouble(1, senderAvailableBalance);
				ps.setInt(2, accountId);
				ps.executeUpdate();

				if (recipient != null) {
					ps = connection.prepareStatement(ITransactionFinalizerDAO.SELECT_USER_ACCOUNT_SQL);
					ps.setInt(1, recipient);
					rs = ps.executeQuery();

					rs.next();
					double recipientAvailableBalance = rs.getDouble(2);
					double recipientCurrentBalance = rs.getInt(3);

					ps = connection.prepareStatement(ITransactionFinalizerDAO.FINALIZE_RECIPIENT_TRANSACTION_SQL);
					ps.setDouble(1, recipientAvailableBalance + senderBlockedAmount);
					ps.setDouble(2, recipientCurrentBalance + senderBlockedAmount);
					ps.setInt(3, recipient);
					ps.executeUpdate();
				}
				
				connection.commit();
			}
			return true;

		} catch (SQLException e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
		return false;
	}
}
