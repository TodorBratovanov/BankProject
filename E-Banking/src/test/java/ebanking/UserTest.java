package ebanking;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import ebanking.exceptions.AccountException;
import ebanking.exceptions.IbanException;
import ebanking.exceptions.IdException;
import ebanking.exceptions.InvalidStringException;
import ebanking.model.dao.UserDAO;
import ebanking.model.entity.Deposit;

public class UserTest {

//	@Test
//	public void testRegisterUser() throws UserException, IdException, InvalidNameException, 
//	InvalidPhoneNumberException, InvalidEmailException, AddressException, InvalidStringException,
//	InvalidPasswordException, InvalidEgnException {
//		
//			int userid = new UserDAO().registerUser(new User(1,"SvaSe", "SKk", "SKjj", "+359877706176", 
//				"kochev.svetoslav@gmail.com", "asdAa09875dcs", "asdzcd", "8805193502",false));
//		
//		assertNotEquals(userid, 0);
//	}
//	
//	@Test
//	public void testLoginUser() throws UserException, IdException, InvalidNameException, 
//	InvalidPhoneNumberException, InvalidEmailException, AddressException, InvalidStringException, 
//	InvalidPasswordException, InvalidEgnException {
//		
//
//		int userid = new UserDAO().loginUser(new User(1,"SvaSe", "SKk", "SKjj", "+359877706176", 
//				"kochev.svetoslav@gmail.com", "asdAa09875dcs", "asdzcd", "8805193502",false));
//		
//		assertNotEquals(userid, 0);
//	}
//	
//	@Test
//	public void testShowUserAccounts() throws UserException, AccountException, IbanException, 
//	InvalidStringException, IdException, DateTimeException, InterestException{
//		
//
//		List<Account> accounts = new UserDAO().showUserAccounts(1);
//		
//		assertNotEquals(accounts.size(),0);
//	}
//
//	@Test
//	public void testTransferMoneyToOtherAccount() throws IbanException, AccountException, InvalidStringException, IdException {
//		assertTrue(new UserDAO().transferMoneyToOtherAccount(new Deposit(1, 100, 100, "BG80BNBG96611020345678", 1, "GBP"), 
//				20, "BG61TTBB94003115068734"));
//	}
	
	@Test
	public void testFinalizeAllUserTransactions() {
		assertTrue(new UserDAO().finalizeAllUserTransactions());
	}
}
