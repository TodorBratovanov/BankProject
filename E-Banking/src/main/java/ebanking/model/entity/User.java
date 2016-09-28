package ebanking.model.entity;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

import ebanking.exceptions.AddressException;
import ebanking.exceptions.IdException;
import ebanking.exceptions.InvalidEmailException;
import ebanking.exceptions.InvalidNameException;
import ebanking.exceptions.InvalidPhoneNumberException;
import ebanking.exceptions.InvalidStringException;
import ebanking.validators.EmailValidator;
import ebanking.validators.IValidator;
import ebanking.validators.NameValidator;
import ebanking.validators.PhoneNumberValidator;

public class User {

	private long userId;
	private String firstName;
	private String middleName;
	private String lastName;
	private String phoneNumber;
	private String email;
	private String address;
	private String type;
	private Set<Message> messages = new LinkedHashSet<>();
	private Set<Account> accounts = new HashSet<>();
	private Set<UserSession> userSessions = new LinkedHashSet<>();
	
	public User(long userId, String firstName, String middleName, String lastName, String phoneNumber, String email,
			String address, String type) throws IdException, InvalidNameException,
			InvalidPhoneNumberException, InvalidEmailException, AddressException, InvalidStringException {
		if (userId > 0) {
			this.userId = userId;
		} else {
			throw new IdException("Invalid user ID");
		}
		if (NameValidator.isValidName(firstName)) {
			this.firstName = firstName;
		}
		if (NameValidator.isValidName(middleName)) {
			this.middleName = middleName;
		}
		if (NameValidator.isValidName(lastName)) {
			this.lastName = lastName;
		}
		if (PhoneNumberValidator.isValidPhoneNumber(phoneNumber)) {
			this.phoneNumber = phoneNumber;
		}
		if (EmailValidator.isValidEmail(email)) {
			this.email = email;
		}
		if (address != null && !address.trim().isEmpty()) {
			this.address = address;
		} else {
			throw new AddressException("Incorrect address");
		}
		if (IValidator.isValidString(type)) {
			this.type = type;
		}
	}

}
