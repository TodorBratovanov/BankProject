package com.starbank.exceptions;

public class InvalidPhoneNumberException extends Exception {

	private static final long serialVersionUID = -4584604690676238020L;

	public InvalidPhoneNumberException() {
		super();
		// TODO Auto-generated constructor stub
	}

	public InvalidPhoneNumberException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public InvalidPhoneNumberException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public InvalidPhoneNumberException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

}
