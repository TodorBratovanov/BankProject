package com.starbank;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import java.sql.SQLException;
import java.util.List;

import org.junit.Test;

import com.starbank.exceptions.AccountException;
import com.starbank.exceptions.AddressException;
import com.starbank.exceptions.DateTimeException;
import com.starbank.exceptions.IbanException;
import com.starbank.exceptions.IdException;
import com.starbank.exceptions.InterestException;
import com.starbank.exceptions.InvalidEgnException;
import com.starbank.exceptions.InvalidEmailException;
import com.starbank.exceptions.InvalidNameException;
import com.starbank.exceptions.InvalidPasswordException;
import com.starbank.exceptions.InvalidPhoneNumberException;
import com.starbank.exceptions.InvalidStringException;
import com.starbank.exceptions.UserException;
import com.starbank.model.dao.UserDAO;
import com.starbank.model.entity.Account;
import com.starbank.model.entity.CurrentAccount;
import com.starbank.model.entity.Deposit;
import com.starbank.model.entity.User;

public class UserTest {

//	@Test
//	public void testRegisterUser()
//			throws UserException, IdException, InvalidNameException, InvalidPhoneNumberException, InvalidEmailException,
//			AddressException, InvalidStringException, InvalidPasswordException, InvalidEgnException {
//
//		int userid = new UserDAO().registerUser(new User(1, "SvaSe", "SKk", "SKjj", "+359877706176",
//				"kochev.svetoslav@gmail.com", "asdAa09875dcs", "asdzcd", "8805193502", false));
//
//		assertNotEquals(userid, 0);
//	}
//
//	@Test
//	public void testLoginUser()
//			throws UserException, IdException, InvalidNameException, InvalidPhoneNumberException, InvalidEmailException,
//			AddressException, InvalidStringException, InvalidPasswordException, InvalidEgnException {
//
//		int userid = new UserDAO().loginUser(new User(1, "SvaSe", "SKk", "SKjj", "+359877706176",
//				"kochev.svetoslav@gmail.com", "asdAa09875dcs", "asdzcd", "8805193502", false));
//
//		assertNotEquals(userid, 0);
//	}
//
//	@Test
//	public void testShowUserAccounts() throws UserException, AccountException, IbanException, InvalidStringException,
//			IdException, DateTimeException, InterestException {
//
//		List<Account> accounts = new UserDAO().showUserAccounts(1);
//
//		assertNotEquals(accounts.size(), 0);
//	}
//
//	@Test
//	public void testTransferMoneyToOtherAccount()
//			throws IbanException, AccountException, InvalidStringException, IdException {
//		assertTrue(new UserDAO().transferMoneyToOtherAccount(
//				new Deposit(1, 100, 100, "BG80BNBG96611020345678", 1, "GBP"), 20, "BG61TTBB94003115068734"));
//	}
//
//	@Test
//	public void testFinalizeAllUserTransactions() {
//		assertTrue(new UserDAO().finalizeAllUserTransactions());
//	}
//
//	@Test
//	public void testTransferMoneyToMyAccounts()
//			throws IbanException, AccountException, InvalidStringException, IdException {
//
//		assertTrue(
//				new UserDAO().transferMoneyToMyAccount(new CurrentAccount(1, 80, 80, "BG62TTBB94001524310814", 1, "BG"),
//						new Deposit(1, 100, 100, "BG80BNBG96611020345678", 1, "GBP"), 1));
//	}
//	
//	@Test
//	public void testIsRegistrationConfirmed() throws SQLException, UserException {
//
//		assertTrue(new UserDAO().isRegistrationConfirmed("kochev.svetoslav@gmail.com"));
//	}
	
	@Test
	public void testIsRegistred() throws SQLException, UserException {

		assertTrue(new UserDAO().isRegistered("varbata@abv.bg"));
	}
}
