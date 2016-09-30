package ebanking;

import static org.junit.Assert.*;

import org.junit.Test;

import ebanking.exceptions.AddressException;
import ebanking.exceptions.IdException;
import ebanking.exceptions.InvalidEgnException;
import ebanking.exceptions.InvalidEmailException;
import ebanking.exceptions.InvalidNameException;
import ebanking.exceptions.InvalidPasswordException;
import ebanking.exceptions.InvalidPhoneNumberException;
import ebanking.exceptions.InvalidStringException;
import ebanking.exceptions.UserException;
import ebanking.model.dao.AdminDAO;
import ebanking.model.entity.User;

public class AdminTest {

	@Test
	public void testUserRegisterConfirmation() throws UserException, IdException, InvalidNameException, InvalidPhoneNumberException, 
	InvalidEmailException, AddressException, InvalidStringException, InvalidPasswordException, InvalidEgnException {
		
		new AdminDAO().confirmUserRegistration(new User(1, "Ivan", "Ivanov", "Ivanov", "+35987555555", "ivan@abv.bg", "Ivan1234", "Sofia", 
				"9005159015", false));
	}

}
