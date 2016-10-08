package com.starbank;

import static org.junit.Assert.assertNotEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.starbank.exceptions.AddressException;
import com.starbank.exceptions.IdException;
import com.starbank.exceptions.InvalidEgnException;
import com.starbank.exceptions.InvalidEmailException;
import com.starbank.exceptions.InvalidNameException;
import com.starbank.exceptions.InvalidPasswordException;
import com.starbank.exceptions.InvalidPhoneNumberException;
import com.starbank.exceptions.InvalidStringException;
import com.starbank.exceptions.UserException;
import com.starbank.model.dao.repo.UserRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
public class UserTest {

	@Test
	public void testLoginUser()
			throws UserException, IdException, InvalidNameException, InvalidPhoneNumberException, InvalidEmailException,
			AddressException, InvalidStringException, InvalidPasswordException, InvalidEgnException {

		int userid = new UserRepository().loginUser("asd.abv.bg", "Admin123");

		assertNotEquals(userid, 0);
	}

}
