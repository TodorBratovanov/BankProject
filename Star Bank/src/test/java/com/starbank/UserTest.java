package com.starbank;

import static org.junit.Assert.assertNotEquals;

import org.junit.Test;

import com.starbank.exceptions.AddressException;
import com.starbank.exceptions.IdException;
import com.starbank.exceptions.InvalidEgnException;
import com.starbank.exceptions.InvalidEmailException;
import com.starbank.exceptions.InvalidNameException;
import com.starbank.exceptions.InvalidPasswordException;
import com.starbank.exceptions.InvalidPhoneNumberException;
import com.starbank.exceptions.InvalidStringException;
import com.starbank.exceptions.UserException;
import com.starbank.model.dao.UserDAO;
import com.starbank.model.entity.User;

public class UserTest {

	@Test
	public void testLoginUser()
			throws UserException, IdException, InvalidNameException, InvalidPhoneNumberException, InvalidEmailException,
			AddressException, InvalidStringException, InvalidPasswordException, InvalidEgnException {

		int userid = new UserDAO().loginUser(new User(1, "SvaSe", "SKk", "SKjj", "+359877706176",
				"kochev.svetoslav@gmail.com", "asdAa09875dcs", "asdzcd", "8805193502", false));

		assertNotEquals(userid, 0);
	}

}
