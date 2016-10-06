package com.starbank.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.starbank.exceptions.AccountException;
import com.starbank.exceptions.IbanException;
import com.starbank.model.entity.Account;
import com.starbank.validators.IbanValidator;

public class TransferMoneyDAO {
	
	private static final String SELECT_USER_ACCOUNT_SQL = "SELECT * FROM Accounts WHERE account_id = ?";
	private static final String SELECT_RECEIVING_ACCOUNT_SQL = "SELECT account_id FROM Accounts WHERE iban = ?";
    
	
	public boolean transferMoneyToOtherAccount(Account account, double moneyToTransfer, String recipientIban)
			throws IbanException, AccountException {

		Connection connection = DBConnection.getInstance().getConnection();

		if (moneyToTransfer > 0) {
			if (IbanValidator.isValidIban(recipientIban)) {
				try {
					connection.setAutoCommit(false);

					PreparedStatement ps = connection.prepareStatement(SELECT_USER_ACCOUNT_SQL);
					ps.setInt(1, account.getAccountId());
					ResultSet rs = ps.executeQuery();
					rs.next();
					double availableBalance = rs.getDouble(2);
					double blockedAmount = rs.getDouble(4);

					if(availableBalance >= moneyToTransfer) {
						UpdateAccountDAO.updateCurrentAccount(account, moneyToTransfer, connection, availableBalance, blockedAmount);
                        
						ps = connection.prepareStatement(SELECT_RECEIVING_ACCOUNT_SQL);
						ps.setString(1, recipientIban);
						rs = ps.executeQuery();

						int recipientAccountId;
						if (rs.next()) {
							recipientAccountId = rs.getInt(1);

							UpdateAccountDAO.updateRecipientAccount(account, connection, recipientAccountId);
						}

						connection.commit();
					} else {
						connection.rollback();
						throw new AccountException("Insufficient funds");
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
			}
		} else {
			throw new AccountException("Incorrect transfer sum! Must be positive.");
		}
		return false;
	}

	public boolean transferMoneyToMyAccount(Account senderAccount, Account recipientAccount, double moneyToTransfer)
			throws IbanException, AccountException {
		
		Connection connection = DBConnection.getInstance().getConnection();

		if (moneyToTransfer > 0) {
			if ((IbanValidator.isValidIban(senderAccount.getIban()))
					&& (IbanValidator.isValidIban(senderAccount.getIban()))) {
				try {
					connection.setAutoCommit(false);

					PreparedStatement ps = connection.prepareStatement(SELECT_USER_ACCOUNT_SQL);
					ps.setInt(1, senderAccount.getAccountId());
					ResultSet rs = ps.executeQuery();
					rs.next();
					double availableBalance = rs.getDouble(1);
					double blockedAmount = rs.getDouble(2);

					if (availableBalance >= moneyToTransfer) {
						UpdateAccountDAO.updateCurrentAccount(senderAccount, moneyToTransfer, connection, availableBalance,
								blockedAmount);

						int recipientAccountId = recipientAccount.getAccountId();
						
						UpdateAccountDAO.updateRecipientAccount(senderAccount, connection, recipientAccountId);

						connection.commit();
						
					} else {
						connection.rollback();
						throw new AccountException("Insufficient funds");
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
			}
		} else {
			throw new AccountException("Incorrect transfer sum! Must be positive.");
		}
		return false;
	}

	
}
