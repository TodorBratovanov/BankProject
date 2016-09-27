package ebanking.model.entity;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

import ebanking.exceptions.AddressException;
import ebanking.exceptions.IdException;
import ebanking.exceptions.InvalidEmailException;
import ebanking.exceptions.InvalidNameException;
import ebanking.exceptions.InvalidPhoneNumberException;
import ebanking.exceptions.TypeException;
import ebanking.exceptions.UserException;
import ebanking.validators.EmailValidator;
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

	public User(long userId, String firstName, String middleName, String lastName, String phoneNumber, String email,
			String address, String type) throws TypeException, IdException, InvalidNameException,
			InvalidPhoneNumberException, InvalidEmailException, AddressException {
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
		if (type != null && !type.trim().isEmpty()) {
			this.type = type;
		} else {
			throw new TypeException("Incorrect user type");
		}
	}

}
