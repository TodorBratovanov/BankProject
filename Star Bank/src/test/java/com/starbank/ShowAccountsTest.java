package com.starbank;

import static org.junit.Assert.assertNotEquals;

import java.util.List;

import org.junit.Test;

import com.starbank.exceptions.AccountException;
import com.starbank.exceptions.DateTimeException;
import com.starbank.exceptions.IbanException;
import com.starbank.exceptions.IdException;
import com.starbank.exceptions.InterestException;
import com.starbank.exceptions.InvalidStringException;
import com.starbank.exceptions.UserException;
import com.starbank.model.dao.ShowAccountsDAO;
import com.starbank.model.dao.UserDAO;
import com.starbank.model.entity.Account;

public class ShowAccountsTest {
	
	@Test
	public void testShowUserAccounts() throws UserException, AccountException, IbanException, InvalidStringException,
			IdException, DateTimeException, InterestException {

		List<Account> accounts = new ShowAccountsDAO().showUserAccounts(1);

		assertNotEquals(accounts.size(), 0);
	}
}
