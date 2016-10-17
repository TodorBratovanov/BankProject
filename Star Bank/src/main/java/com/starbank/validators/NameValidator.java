package com.starbank.validators;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.starbank.exceptions.InvalidNameException;
import com.starbank.exceptions.InvalidStringException;

public class NameValidator implements IValidator{
	
	private final static String NAME_REGIX = "[A-Z][a-zA-Z]+";

	public static boolean isValidName(String enteredName) throws InvalidNameException, InvalidStringException {
		Pattern pattern = Pattern.compile(NAME_REGIX);
		Matcher matcher = pattern.matcher(enteredName);
		if((IValidator.isValidString(enteredName)) && (matcher.matches())){
			return true;
		}
		throw new InvalidNameException("Incorrect name!");
	}
}
