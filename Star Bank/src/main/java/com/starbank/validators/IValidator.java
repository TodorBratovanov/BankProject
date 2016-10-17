package com.starbank.validators;

import com.starbank.exceptions.InterestException;
import com.starbank.exceptions.InvalidStringException;

public interface IValidator {
	
	 static final int MIN_INTEREST = 0;
	 static final int MAX_INTEREST = 2;
	
	static boolean isValidString(String string) throws InvalidStringException {
		if (string != null && !string.trim().isEmpty()) {
			return true;
		}
		throw new InvalidStringException("Incorrect string");
	}
	
	static boolean isPositive(double number) {
		return number > 0;
	}
	
	static boolean isValidInterest(double interest) throws InterestException {
		boolean isValid = false;
		if ((interest >=MIN_INTEREST) && (interest <= MAX_INTEREST)) {
			isValid = true;
		} else {
			throw new InterestException("Invalid interest!");
		}
		return isValid;
	}
}
