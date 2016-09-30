package ebanking;

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
import ebanking.model.dao.UserDAO;
import ebanking.model.entity.User;
import static org.junit.Assert.*;

public class UserTest {

	@Test
	public void testRegisterUser() throws UserException, IdException, InvalidNameException, 
	InvalidPhoneNumberException, InvalidEmailException, AddressException, InvalidStringException,
	InvalidPasswordException, InvalidEgnException {
		
		int id = new UserDAO().registerUser(new User(1,"Sve", "SKk", "SKjj", "+359877706176", 
				"kochev.svetoslav@gmail.com", "asdAa09875dcs", "asdzcd", "8805193502",false));
		
		assertNotEquals(id, 0);
	}

}
