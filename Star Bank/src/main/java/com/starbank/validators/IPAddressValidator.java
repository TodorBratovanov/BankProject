package com.starbank.validators;

import java.util.regex.Pattern;

import com.starbank.exceptions.InvalidIPException;

public class IPAddressValidator {

	private static final Pattern PATTERN = Pattern.compile(
	        "^(([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.){3}([01]?\\d\\d?|2[0-4]\\d|25[0-5])$");

	public static boolean isValidIpAddress(final String ip) throws InvalidIPException {
	    if (PATTERN.matcher(ip).matches()) {
	    	return true;
	    }
	    throw new InvalidIPException("Incorrect IP address");
	}
	
}
