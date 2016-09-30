package ebanking.model.entity;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

import ebanking.exceptions.AddressException;
import ebanking.exceptions.IdException;
import ebanking.exceptions.InvalidEmailException;
import ebanking.exceptions.InvalidNameException;
import ebanking.exceptions.InvalidPasswordException;
import ebanking.exceptions.InvalidPhoneNumberException;
import ebanking.exceptions.InvalidStringException;
import ebanking.validators.EgnValidator;
import ebanking.validators.EmailValidator;
import ebanking.validators.IValidator;
import ebanking.validators.NameValidator;
import ebanking.validators.PasswordValidator;
import ebanking.validators.PhoneNumberValidator;

public class User {

	private long userId;
	private String firstName;
	private String middleName;
	private String lastName;
	private String phoneNumber;
	private String email;
	private String password;
	private String address;
	private String egn;
	private boolean isAdmin;
	private boolean isRegistered;
	private Set<Message> messages = new LinkedHashSet<>();
	private Set<Account> accounts = new HashSet<>();
	private Set<UserSession> userSessions = new LinkedHashSet<>();

	public User(long userId, String firstName, String middleName, String lastName, String phoneNumber, String email,
			String password, String address, String egn, boolean isAdmin)
			throws IdException, InvalidNameException, InvalidPhoneNumberException, InvalidEmailException,
			AddressException, InvalidStringException, InvalidPasswordException {
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

		if (PasswordValidator.isValidPassword(password)) {
			this.password = password;
		}

		if (address != null && !address.trim().isEmpty()) {
			this.address = address;
		} else {
			throw new AddressException("Incorrect address");
		}
		if (EgnValidator.isValidEgn()) {
			this.egn = egn;
		}
		this.isAdmin = isAdmin;
	}

	public long getUserId() {
		return userId;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getMiddleName() {
		return middleName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public String getEmail() {
		return email;
	}

	public String getPassword() {
		return password;
	}

	public String getAddress() {
		return address;
	}

	public boolean isAdmin() {
		return isAdmin;
	}

	public boolean isRegistered() {
		return isRegistered;
	}

	public void setRegistered(boolean isRegistered) {
		this.isRegistered = isRegistered;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}
}
