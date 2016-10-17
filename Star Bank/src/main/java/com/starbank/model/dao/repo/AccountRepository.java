package com.starbank.model.dao.repo;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.transaction.TransactionException;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import com.starbank.exceptions.AccountException;
import com.starbank.exceptions.CardException;
import com.starbank.exceptions.DateTimeException;
import com.starbank.exceptions.IbanException;
import com.starbank.exceptions.IdException;
import com.starbank.exceptions.InterestException;
import com.starbank.exceptions.InvalidEmailException;
import com.starbank.exceptions.InvalidStringException;
import com.starbank.exceptions.UserException;
import com.starbank.model.dao.IAccountDAO;
import com.starbank.model.dao.ICardDAO;
import com.starbank.model.dao.ICreditDAO;
import com.starbank.model.dao.ICurrentAccountDAO;
import com.starbank.model.dao.IDepositDAO;
import com.starbank.model.dao.IUserDAO;
import com.starbank.model.dao.mapper.AccountMapper;
import com.starbank.model.dao.mapper.UserMapper;
import com.starbank.model.entity.Account;
import com.starbank.model.entity.User;
import com.starbank.validators.EmailValidator;
import com.starbank.validators.IbanValidator;

public class AccountRepository implements IAccountDAO {

	private static final int MONTHS_IN_YEAR = 12;
	private static final double DEPOSIT_INTEREST = 0.02;
	private static final double CREDIT_INTEREST = 0.05;
	private JdbcTemplate jdbcTemplate;
	private TransactionTemplate transactionTemplate;

	public AccountRepository() {
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
			loadAccounts(userId, ICurrentAccountDAO.SELECT_CURRENT_ACCOUNTS_ALL_SQL, accounts);
			loadAccounts(userId, IDepositDAO.SELECT_DEPOSITS_ALL_SQL, accounts);
			loadAccounts(userId, ICreditDAO.SELECT_CREDITS_ALL_SQL, accounts);
		} catch (Exception e) {
			e.printStackTrace();
			throw new UserException("Cannot load users!");
		}
		return accounts;
	}

	private void loadAccounts(int userId, String sql, List<Account> accounts) {
		try {
			List<Account> currentAccounts = this.jdbcTemplate.query(sql, new Object[] { userId }, new AccountMapper());
			accounts.addAll(currentAccounts);
		} catch (EmptyResultDataAccessException e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean transferMoneyToOtherAccount(String senderIban, double moneyToTransfer, String recipientIban)
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
										new Object[] { getAccountId(senderIban) }, new AccountMapper());
								
								if (accountDB.getNetAvlbBalance() >= moneyToTransfer) {
									updateCurrentAccount(accountDB, moneyToTransfer, accountDB.getNetAvlbBalance(),
											moneyToTransfer);
										int recipientAccountId = jdbcTemplate.queryForObject(
												IAccountDAO.SELECT_RECEIVING_ACCOUNT_SQL,
												new Object[] { recipientIban }, Integer.class);
										updateRecipientAccount(accountDB, recipientAccountId);
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
	public boolean transferMoneyToMyAccount(String senderIban, String recipientIban, double moneyToTransfer)
			throws IbanException, AccountException {
		boolean isComplete = false;
		if (moneyToTransfer > 0) {
			if ((IbanValidator.isValidIban(senderIban)) && (IbanValidator.isValidIban(recipientIban))) {
				try {
					isComplete = this.transactionTemplate.execute(new TransactionCallback<Boolean>() {
						@Override
						public Boolean doInTransaction(TransactionStatus transactionStatus) {
							try {
								Account accountDB = jdbcTemplate.queryForObject(IAccountDAO.SELECT_USER_ACCOUNT_SQL,
										new Object[] { getAccountId(senderIban) }, new AccountMapper());
								
								if (accountDB.getNetAvlbBalance() >= moneyToTransfer) {
									updateCurrentAccount(accountDB, moneyToTransfer, accountDB.getNetAvlbBalance(),
											moneyToTransfer);
									updateRecipientAccount(accountDB, getAccountId(recipientIban));
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
			this.jdbcTemplate.update(IAccountDAO.UPDATE_ACCOUNT_SQL,
					senderAccount.getNetAvlbBalance() - moneyToTransfer,
					senderAccount.getBlockedAmount() + blockedAmount, senderAccount.getAccountId());
		} catch (EmptyResultDataAccessException e) {
			e.printStackTrace();
		}
	}

	@Override
	public int getAccountId(String iban) {
		int accountId = 0;
		try {
			accountId = jdbcTemplate.queryForInt(IAccountDAO.SELECT_ACCOUNT_ID, iban);
		} catch (EmptyResultDataAccessException e) {
			e.printStackTrace();
		}
		return accountId;
	}

	@Override
	public boolean openAccount(String email, String type, double amount, String iban, int validation, String cardNumber)
			throws InvalidEmailException, InvalidStringException, AccountException {
		boolean isComplete = false;
		if (amount > 0) {
			if (EmailValidator.isValidEmail(email)
					&& (type != null && (type.equals("Current") || type.equals("Deposit") || type.equals("Credit")))) {
				try {
					isComplete = this.transactionTemplate.execute(new TransactionCallback<Boolean>() {
						@Override
						public Boolean doInTransaction(TransactionStatus transactionStatus) {
							try {
								int userId = jdbcTemplate.queryForInt(IUserDAO.SELECT_USERID_SQL, email);
								
								KeyHolder keyHolder = insertAccount(amount, iban, userId);
								
								long accountId = (long) keyHolder.getKey();
								User userDB = jdbcTemplate.queryForObject(IUserDAO.SELECT_USER_BY_EMAIL_SQL,
										new Object[] { email }, new UserMapper());
								
								insertConcreteAccount(email, type, amount, validation, cardNumber, transactionStatus,
										accountId, userDB);
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
			throw new AccountException("Incorrect account amount! Must be positive.");
		}
		return isComplete;
	}

	private void createCard(String type, int validation, String cardNumber, User userDB, long accountId) {
		jdbcTemplate.update(ICardDAO.INSERT_CARD_SQL, (userDB.getFirstName() + " " + userDB.getLastName()), type,
				cardNumber, Timestamp.valueOf(LocalDateTime.now()),
				Timestamp.valueOf(LocalDateTime.now().plusYears(validation)), accountId);
	}
	
	private void insertConcreteAccount(String email, String type, double amount, int validation,
			String cardNumber, TransactionStatus transactionStatus, long accountId, User userDB)
			throws AccountException, CardException, SQLException {
		Integer countOfAccounts = null;
		switch (type) {
		case "Current":
			countOfAccounts = jdbcTemplate.queryForObject(
					ICurrentAccountDAO.SELECT_COUNT_OF_USER_CURRENT_ACCOUNTS,
					new Object[] { email }, Integer.class);
			if (countOfAccounts != null && countOfAccounts < 2) {
				jdbcTemplate.update(ICurrentAccountDAO.INSERT_CURRENT_ACCOUNT_SQL, amount,
						accountId);
				createCard(type, validation, cardNumber, userDB, accountId);
			} else {
				throw new AccountException("Cannot open more than two accounts of type!");
			}
			break;
		case "Deposit":
			countOfAccounts = jdbcTemplate.queryForObject(
					IDepositDAO.SELECT_COUNT_OF_USER_DEPOSITS, new Object[] { email },
					Integer.class);
			if (countOfAccounts != null && countOfAccounts < 2) {
				jdbcTemplate.update(IDepositDAO.INSERT_DEPOSIT_SQL,
						Timestamp.valueOf(LocalDateTime.now()),
						Timestamp.valueOf(LocalDateTime.now().plusYears(validation)),
						DEPOSIT_INTEREST, accountId);
				createCard(type, validation, cardNumber, userDB, accountId);
			} else {
				throw new AccountException("Cannot open more than two accounts of type!");
			}
			break;
		case "Credit":
			countOfAccounts = jdbcTemplate.queryForObject(
					ICreditDAO.SELECT_COUNT_OF_USER_CREDITS, new Object[] { email },
					Integer.class);
			if (countOfAccounts != null && countOfAccounts < 2) {
				jdbcTemplate.update(ICreditDAO.INSERT_CREDIT_SQL, CREDIT_INTEREST,
						Timestamp.valueOf(LocalDateTime.now().plusYears(validation)),
						((amount / (validation * MONTHS_IN_YEAR))) * (1 + CREDIT_INTEREST),
						accountId);
				createCard(type, validation, cardNumber, userDB, accountId);
			} else {
				throw new AccountException("Cannot open more than two accounts of type!");
			}
			break;
		default:
			transactionStatus.setRollbackOnly();
			throw new CardException("Incorrect type of card!");
		}
	}
	
	private KeyHolder insertAccount(double amount, String iban, int userId) {
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(new PreparedStatementCreator() {
			
			@Override
			public PreparedStatement createPreparedStatement(java.sql.Connection con)
					throws SQLException {
				PreparedStatement ps = con.prepareStatement(IAccountDAO.INSERT_ACCOUNT_SQL,
						new String[] { "account_id" });
				ps.setDouble(1, amount);
				ps.setDouble(2, amount);
				ps.setDouble(3, 0);
				ps.setString(4, iban);
				ps.setInt(5, userId);
				ps.setString(6, "EUR");
				ps.setString(7, null);
				return ps;
			}
		}, keyHolder);
		return keyHolder;
	}

}
