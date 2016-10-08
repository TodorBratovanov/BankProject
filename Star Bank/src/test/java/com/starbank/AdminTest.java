package com.starbank;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.starbank.exceptions.AccountException;
import com.starbank.exceptions.AddressException;
import com.starbank.exceptions.IdException;
import com.starbank.exceptions.InvalidEgnException;
import com.starbank.exceptions.InvalidEmailException;
import com.starbank.exceptions.InvalidNameException;
import com.starbank.exceptions.InvalidPasswordException;
import com.starbank.exceptions.InvalidPhoneNumberException;
import com.starbank.exceptions.InvalidStringException;
import com.starbank.exceptions.MessageException;
import com.starbank.exceptions.UserException;
import com.starbank.model.dao.AdminDAO;
import com.starbank.model.entity.User;

public class AdminTest {

	@Test
	public void testUserRegisterConfirmation()
			throws UserException, IdException, InvalidNameException, InvalidPhoneNumberException, InvalidEmailException,
			AddressException, InvalidStringException, InvalidPasswordException, InvalidEgnException {
		assertTrue(new AdminDAO().confirmUserRegistration(new User(1, "Ivan", "Ivanov", "Ivanov", "+35987555555",
				"ivan@abv.bg", "Ivan1234", "Sofia", "9005159015", false)));
	}

	@Test
	public void testDeleteUser() throws UserException {
		assertTrue(new AdminDAO().deleteUser(3));
	}

	@Test
	public void testDeleteAccount() throws MessageException, AccountException, UserException {
		assertTrue(new AdminDAO().deleteAccount(3));
	}

}
