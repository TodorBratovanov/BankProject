package com.starbank.model.dao.repo;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.starbank.exceptions.AccountException;
import com.starbank.exceptions.DateTimeException;
import com.starbank.exceptions.IbanException;
import com.starbank.exceptions.IdException;
import com.starbank.exceptions.InvalidStringException;
import com.starbank.model.dao.ITransactionDAO;
import com.starbank.model.entity.Transaction;

@Component
public class TransactionRepository implements ITransactionDAO {
	private JdbcTemplate jdbcTemplate;

	public TransactionRepository() {
	}

	@Autowired
	public TransactionRepository(DataSource dataSource) {
		jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public List<Transaction> getAllTransactions(int userId,String startDate,String endDate) throws AccountException {
		List<Transaction> transactions = new ArrayList<>();
		try {
			transactions =  this.jdbcTemplate.query(ITransactionDAO.SELECT_TRANSACTIONS_SQL,
					new Object[] { userId ,startDate , endDate}, new RowMapper<Transaction>() {
			            public Transaction mapRow(ResultSet rs, int rowNum) throws SQLException {
			            	Transaction transaction = null;
							try {
								transaction = new Transaction(rs.getInt("transaction_id"),
										rs.getTimestamp("date"), rs.getString("sender_iban"),
										rs.getString("recipient_iban"), rs.getDouble("amount"), rs.getString("currency"),rs.getInt("account_id"));
							} catch (IdException | InvalidStringException | AccountException | DateTimeException
									| IbanException e) {
								e.printStackTrace();
							}
			                return transaction;
			            }
			        });
		} catch (EmptyResultDataAccessException e) {
			throw new AccountException("Cannot get transactions!", e);
		}
		return transactions;
	}
	
}
