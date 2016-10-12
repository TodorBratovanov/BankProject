package com.starbank.model.dao.repo;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.TransactionException;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import com.starbank.exceptions.AccountException;
import com.starbank.exceptions.DateTimeException;
import com.starbank.exceptions.IbanException;
import com.starbank.exceptions.IdException;
import com.starbank.exceptions.InterestException;
import com.starbank.exceptions.InvalidStringException;
import com.starbank.exceptions.UserException;
import com.starbank.model.dao.IAccountDAO;
import com.starbank.model.dao.mapper.AccountMapper;
import com.starbank.model.entity.Account;
import com.starbank.validators.IbanValidator;

public class AccountRepository implements IAccountDAO {

	private JdbcTemplate jdbcTemplate;
	private TransactionTemplate transactionTemplate;

	public AccountRepository() {
		// TODO Auto-generated constructor stub
	}

	@Autowired
	public AccountRepository(DataSource dataSource, TransactionTemplate template) {
		jdbcTemplate = new JdbcTemplate(dataSource);
		transactionTemplate = template;
	}

	@Override
	public List<Account> showUserAccounts(int userId) throws UserException, AccountException, IbanException,
			InvalidStringException, IdException, DateTimeException, InterestException {

		List<Account> accounts = new ArrayList<Account>();
		try {
			loadAccounts(userId, IAccountDAO.SELECT_CURRENT_ACCOUNTS_SQL, accounts);
			loadAccounts(userId, IAccountDAO.SELECT_DEPOSIT_ACCOUNTS_SQL, accounts);
			loadAccounts(userId, IAccountDAO.SELECT_CREDIT_ACCOUNTS_SQL, accounts);
		} catch (Exception e) {
			e.printStackTrace();
			throw new UserException("Something went wrong!");
		}

		return accounts;
	}

	private void loadAccounts(int userId, String sql, List<Account> accounts) {
		try {
			List<Account> currentAccounts = this.jdbcTemplate.query(sql,
					new Object[] { userId }, new AccountMapper());
			accounts.addAll(currentAccounts);
		} catch (EmptyResultDataAccessException e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean transferMoneyToOtherAccount(Account senderAccount, double moneyToTransfer, String recipientIban)
			throws IbanException, AccountException {

		boolean isComplete = false;
		if (moneyToTransfer > 0) {
			if (IbanValidator.isValidIban(recipientIban)) {
				try {
					isComplete = this.transactionTemplate.execute(new TransactionCallback<Boolean>() {
						@Override
						public Boolean doInTransaction(TransactionStatus transactionStatus) {
							try {
								Account accountDB = jdbcTemplate.queryForObject(IAccountDAO.SELECT_USER_ACCOUNT_SQL,
										new Object[] { senderAccount.getAccountId() }, new AccountMapper());

								if (accountDB.getNetAvlbBalance() >= moneyToTransfer) {
									updateCurrentAccount(accountDB, moneyToTransfer, accountDB.getNetAvlbBalance(),
											accountDB.getBlockedAmount());
									try {
										int recipientAccountId = jdbcTemplate.queryForObject(
												IAccountDAO.SELECT_RECEIVING_ACCOUNT_SQL,
												new Object[] { recipientIban }, Integer.class);
										updateRecipientAccount(accountDB, recipientAccountId);
									} catch (EmptyResultDataAccessException e) {
										e.printStackTrace();
									}
								} else {
									throw new AccountException("Insufficient funds");
								}
							} catch (Exception e) {
								transactionStatus.setRollbackOnly();
								e.printStackTrace();
								return false;
							}
							return true;
						}
					});
				} catch (TransactionException e) {
					e.printStackTrace();
				}
			}
		} else {
			throw new AccountException("Incorrect transfer sum! Must be positive.");
		}
		return isComplete;
	}

	@Override
	public boolean transferMoneyToMyAccount(Account senderAccount, Account recipientAccount, double moneyToTransfer)
			throws IbanException, AccountException {

		boolean isComplete = false;
		if (moneyToTransfer > 0) {
			if ((IbanValidator.isValidIban(senderAccount.getIban()))
					&& (IbanValidator.isValidIban(senderAccount.getIban()))) {
				try {
					isComplete = this.transactionTemplate.execute(new TransactionCallback<Boolean>() {
						@Override
						public Boolean doInTransaction(TransactionStatus transactionStatus) {
							try {
								Account accountDB = jdbcTemplate.queryForObject(IAccountDAO.SELECT_USER_ACCOUNT_SQL,
										new Object[] { senderAccount.getAccountId() }, new AccountMapper());

								if (accountDB.getNetAvlbBalance() >= moneyToTransfer) {
									updateCurrentAccount(accountDB, moneyToTransfer, accountDB.getNetAvlbBalance(),
											accountDB.getBlockedAmount());
									updateRecipientAccount(accountDB, recipientAccount.getAccountId());
								} else {
									throw new AccountException("Insufficient funds");
								}
							} catch (Exception e) {
								transactionStatus.setRollbackOnly();
								e.printStackTrace();
								return false;
							}
							return true;
						}
					});
				} catch (TransactionException e) {
					e.printStackTrace();
				}
			}
		} else {
			throw new AccountException("Incorrect transfer sum! Must be positive.");
		}
		return isComplete;
	}

	@Override
	public void updateRecipientAccount(Account senderAccount, int recipientAccountId) throws SQLException {

		try {
			this.jdbcTemplate.update(IAccountDAO.UPDATE_RECIPIENT_ACCOUNT_ID, recipientAccountId,
					senderAccount.getAccountId());
		} catch (EmptyResultDataAccessException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void updateCurrentAccount(Account senderAccount, double moneyToTransfer, double availableBalance,
			double blockedAmount) throws SQLException {

		try {
			this.jdbcTemplate.update(IAccountDAO.UPDATE_ACCOUNT_SQL, senderAccount.getNetAvlbBalance() - moneyToTransfer,
					senderAccount.getBlockedAmount() - blockedAmount);
		} catch (EmptyResultDataAccessException e) {
			e.printStackTrace();
		}
	}

}
