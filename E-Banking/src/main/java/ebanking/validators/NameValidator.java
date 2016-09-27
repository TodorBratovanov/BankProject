package ebanking.validators;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import ebanking.exceptions.InvalidNameException;

public class NameValidator implements IValidator{
	private final static String NAME_REGIX = "[A-Z][a-zA-Z]*{2,}";

	public static boolean isValidName(String enteredName) throws InvalidNameException {
		Pattern pattern = Pattern.compile(NAME_REGIX);
		
		Matcher matcher = pattern.matcher(enteredName);
		
		if((IValidator.isValidString(enteredName)) && (matcher.matches())){
			return true;
		}
		
		throw new InvalidNameException("Incorrect name!");
	}
}
