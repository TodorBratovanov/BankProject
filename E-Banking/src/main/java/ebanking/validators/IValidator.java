package ebanking.validators;

import ebanking.exceptions.InvalidStringException;

public interface IValidator {

	static boolean isValidString(String string) throws InvalidStringException {
		if (string != null && !string.trim().isEmpty()) {
			return true;
		}
		throw new InvalidStringException("Incorrect string");
	}

	
	static boolean isPositive(double number) {
		return number > 0;
	}
}
