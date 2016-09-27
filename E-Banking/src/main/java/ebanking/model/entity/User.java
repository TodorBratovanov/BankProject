package ebanking.model.entity;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

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
			String address, String type) {
		super();
		this.userId = userId;
		this.firstName = firstName;
		this.middleName = middleName;
		this.lastName = lastName;
		this.phoneNumber = phoneNumber;
		this.email = email;
		this.address = address;
		this.type = type;
	}

}
