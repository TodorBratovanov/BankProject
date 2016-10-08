package com.starbank.model.dao.repo;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.TransactionException;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import com.starbank.model.dao.ITransactionFinalizerDAO;
import com.starbank.model.dao.mapper.AccountMapper;
import com.starbank.model.entity.Account;

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
		boolean isComplete = false;

		try {
			isComplete = transactionTemplate.execute(new TransactionCallback<Boolean>() {

				@Override
				public Boolean doInTransaction(TransactionStatus status) {
					try {
						List<Account> accounts = jdbcTemplate.query(
								ITransactionFinalizerDAO.SELECT_ALL_ACCOUNTS_FOR_TRANSACTION_SQL, new AccountMapper());
						for (Account account : accounts) {

							double blockedAmount = account.getBlockedAmount();
							Integer recipient = account.getRecipientAccountId();
							jdbcTemplate.update(ITransactionFinalizerDAO.FINALIZE_SENDER_TRANSACTION_SQL,
									account.getCurrentBalance() - blockedAmount, account.getAccountId());

							if (recipient != null) {
								Account recipientAccount = jdbcTemplate.queryForObject(
										ITransactionFinalizerDAO.SELECT_USER_ACCOUNT_SQL, new Object[] { recipient },
										new AccountMapper());

								jdbcTemplate.update(ITransactionFinalizerDAO.FINALIZE_RECIPIENT_TRANSACTION_SQL,
										recipientAccount.getNetAvlbBalance() + blockedAmount,
										recipientAccount.getCurrentBalance() + blockedAmount, recipient);
							}

						}
					} catch (Exception e) {
						e.printStackTrace();
						status.setRollbackOnly();
						return false;
					}
					return true;
				}
			});
		} catch (TransactionException e) {
			e.printStackTrace();
		}

		return isComplete;
	}
}
