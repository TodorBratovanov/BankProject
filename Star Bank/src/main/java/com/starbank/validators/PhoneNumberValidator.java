package com.starbank.validators;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.starbank.exceptions.InvalidPhoneNumberException;

public class PhoneNumberValidator implements IValidator {
	private static final String PHONE_NUMBER_REGEX = "(08)[7-9][0-9]{7}";

	public static boolean isValidPhoneNumber(String phoneNumber) throws InvalidPhoneNumberException {
		Pattern pattern = Pattern.compile(PHONE_NUMBER_REGEX);
		Matcher matcher = pattern.matcher(phoneNumber);
		if ((!phoneNumber.isEmpty()) && (phoneNumber != null) && (matcher.matches())) {
			return true;
		}
		throw new InvalidPhoneNumberException("Incorrect phone number!");
	}
}
