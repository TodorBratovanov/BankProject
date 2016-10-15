package com.starbank;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
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
import com.starbank.model.dao.IAdminDAO;
import com.starbank.model.entity.User;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
public class AdminTest {
	
	@Autowired
	private IAdminDAO adminRepo;
	@Autowired
	private User user;
	
	
	@Test
	public void testUserRegisterConfirmation()
			throws UserException, IdException, InvalidNameException, InvalidPhoneNumberException, InvalidEmailException,
			AddressException, InvalidStringException, InvalidPasswordException, InvalidEgnException {
		user.setUserId(8);
		user.setFirstName("Ivan");
		user.setMiddleName("Ivanov");
		user.setLastName("Ivanov");
		user.setPhoneNumber("+35987555555");
		user.setEmail("ivan@abv.bg");
		user.setPassword("Ivan1234");
		user.setAddress("Sofia");
		user.setEgn("9005159015");
		user.setAdmin(false);
		
		assertTrue(adminRepo.confirmUserRegistration(user));
	}

//	@Test
//	public void testDeleteUser() throws UserException {
//		assertTrue(new AdminRepository().deleteUser(3));
//	}
//
//	@Test
//	public void testDeleteAccount() throws MessageException, AccountException, UserException {
//		assertTrue(new AdminRepository().deleteAccount(3));
//	}

}
