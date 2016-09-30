package ebanking.validators;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import ebanking.exceptions.InvalidPasswordException;

public class EgnValidator {
	private static final String EGN_REGIX = "[0-9]{2}[0,1,2,4][0-9][0-9]{2}[0-9]{4}";

	public static boolean isValidPassword(String enteredEgn) throws InvalidPasswordException {
		Pattern pattern = Pattern.compile(EGN_REGIX);

		Matcher matcher = pattern.matcher(enteredEgn);

		if ((!enteredEgn.isEmpty()) && (enteredEgn != null) && (matcher.matches())) {
			return true;
		}

		throw new InvalidPasswordException("Incorrect EGN!");
	}
}
