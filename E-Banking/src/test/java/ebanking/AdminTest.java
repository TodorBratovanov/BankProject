package ebanking;

import static org.junit.Assert.*;

import org.junit.Test;

import ebanking.exceptions.UserException;
import ebanking.model.dao.AdminDAO;

public class AdminTest {

	@Test
	public void testUserRegisterConfirmation() throws UserException {
		new AdminDAO().confirmUserRegistration(1);
	}

}
