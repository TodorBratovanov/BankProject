package ebanking.validators;

public interface IValidator {

	static boolean isValidString(String string) {
		if (string != null && !string.trim().isEmpty()) {
			return true;
		}
		return false;
	}

	
	static boolean isPositive(double number) {
		return number > 0;
	}
}
