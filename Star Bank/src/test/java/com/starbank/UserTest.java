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
import com.starbank.model.dao.RegisterDAO;
import com.starbank.model.dao.repo.UserRepository;
import com.starbank.model.entity.Account;
import com.starbank.model.entity.CurrentAccount;
import com.starbank.model.entity.Deposit;
import com.starbank.model.entity.User;

public class UserTest {

	@Test
	public void testLoginUser()
			throws UserException, IdException, InvalidNameException, InvalidPhoneNumberException, InvalidEmailException,
			AddressException, InvalidStringException, InvalidPasswordException, InvalidEgnException {

		int userid = new UserRepository().loginUser(new User(1, "SvaSe", "SKk", "SKjj", "+359877706176",
				"kochev.svetoslav@gmail.com", "asdAa09875dcs", "asdzcd", "8805193502", false));

		assertNotEquals(userid, 0);
	}

}
