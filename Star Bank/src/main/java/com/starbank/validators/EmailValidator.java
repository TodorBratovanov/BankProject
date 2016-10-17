package com.starbank.validators;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.starbank.exceptions.InvalidEmailException;
import com.starbank.exceptions.InvalidStringException;

public class EmailValidator implements IValidator{
	
	private static final String EMAIL_REGIX = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
			+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

	public static boolean isValidEmail(String enteredEmail) throws InvalidEmailException, InvalidStringException {
		Pattern pattern = Pattern.compile(EMAIL_REGIX);
		Matcher matcher = pattern.matcher(enteredEmail);
		if (IValidator.isValidString(enteredEmail) && matcher.matches()) {
			return true;
		}
		throw new InvalidEmailException("Incorrect email!");
	}
}
