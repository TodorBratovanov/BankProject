package com.starbank.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.starbank.model.entity.Account;

public class UpdateAccountDAO {
	private static final String UPDATE_RECIPIENT_ACCOUNT_ID = "UPDATE Accounts SET recipient_account_id = ? WHERE account_id = ?";
	private static final String UPDATE_ACCOUNT_SQL = "UPDATE Accounts SET net_avlb_balance = ?, blocked_amount = ? WHERE account_id = ?";

	
	public static void updateRecipientAccount(Account senderAccount, Connection connection, int recipientAccountId)
			throws SQLException {
		PreparedStatement ps;
		ps = connection.prepareStatement(UPDATE_RECIPIENT_ACCOUNT_ID);
		ps.setInt(1, recipientAccountId);
		ps.setInt(2, senderAccount.getAccountId());
		ps.executeUpdate();
	}

	public static void updateCurrentAccount(Account senderAccount, double moneyToTransfer, Connection connection,
			double availableBalance, double blockedAmount) throws SQLException {
		PreparedStatement ps = connection.prepareStatement(UPDATE_ACCOUNT_SQL);
		ps.setDouble(1, availableBalance - moneyToTransfer);
		ps.setDouble(2, blockedAmount + moneyToTransfer);
		ps.setInt(3, senderAccount.getAccountId());
		ps.executeUpdate();
	}


}
