package com.starbank.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TransactionFinalizerDAO {
	private static final String SELECT_ALL_ACCOUNTS_FOR_TRANSACTION_SQL = "SELECT account_id FROM Accounts WHERE blocked_amount > 0";
	private static final String SELECT_USER_ACCOUNT_SQL = "SELECT * FROM Accounts WHERE account_id = ?";
	private static final String FINALIZE_SENDER_TRANSACTION_SQL = "UPDATE Accounts SET current_balance = ?, blocked_amount = 0, "
			+ "recipient_account_id = null WHERE account_id = ?";
	private static final String FINALIZE_RECIPIENT_TRANSACTION_SQL = "UPDATE Accounts SET net_avlb_balance = ?, current_balance = ? "
			+ "WHERE account_id = ?";

	public boolean finalizeAllUserTransactions() {

		Connection connection = DBConnection.getInstance().getConnection();

		try {
			connection.setAutoCommit(false);

			PreparedStatement ps = connection.prepareStatement(SELECT_ALL_ACCOUNTS_FOR_TRANSACTION_SQL);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				int accountId = rs.getInt(1);
				ps = connection.prepareStatement(SELECT_USER_ACCOUNT_SQL);
				ps.setInt(1, accountId);
				rs = ps.executeQuery();

				rs.next();
				double senderAvailableBalance = rs.getDouble(2);
				double senderBlockedAmount = rs.getDouble(4);
				Integer recipient = rs.getInt(8);

				ps = connection.prepareStatement(FINALIZE_SENDER_TRANSACTION_SQL);
				ps.setDouble(1, senderAvailableBalance);
				ps.setInt(2, accountId);
				ps.executeUpdate();

				if (recipient != null) {
					ps = connection.prepareStatement(SELECT_USER_ACCOUNT_SQL);
					ps.setInt(1, recipient);
					rs = ps.executeQuery();

					rs.next();
					double recipientAvailableBalance = rs.getDouble(2);
					double recipientCurrentBalance = rs.getInt(3);

					ps = connection.prepareStatement(FINALIZE_RECIPIENT_TRANSACTION_SQL);
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
