package ebanking.validators;

import org.iban4j.IbanFormatException;
import org.iban4j.IbanUtil;
import org.iban4j.InvalidCheckDigitException;
import org.iban4j.UnsupportedCountryException;

import ebanking.exceptions.IbanException;

public class IbanValidator {

	public static boolean isValidIban(String iban) throws IbanException {
		 try {
		     IbanUtil.validate(iban);
		     return true;
		 } catch (IbanFormatException |
		          InvalidCheckDigitException |
		          UnsupportedCountryException e) {
			 throw new IbanException("Incorrect IBAN");
		 }
	}
	
}
