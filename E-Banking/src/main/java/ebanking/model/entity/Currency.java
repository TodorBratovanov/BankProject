package ebanking.model.entity;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Currency {

	private static final Map<CurrencyCode, String> availableCurrencies;
	static {
		Map<CurrencyCode, String> tempCurrenciesMap = new HashMap<>();
		String[] currencyNames = {"Australian dollar", "Canadian dollar", "Swiss franc", "Cyprus pound", "Danish krone", 
				"Euro", "British pound", "Iceland Krona", "Japanese yen", "Norwegian krone", "Romanien leu", "Russian ruble", 
				"Swedish krona", "US dollar"};
		CurrencyCode[] codes = CurrencyCode.values();
		for (int index = 0; index < codes.length; index++) {
			tempCurrenciesMap.put(codes[index], currencyNames[index]);
		}
		availableCurrencies = Collections.unmodifiableMap(tempCurrenciesMap);
	}
	
	private long currencyId;
	private String name;
	private CurrencyCode code;
	private LocalDateTime dateTime;
	private int unit;
	private double fixing;
	private double buy;
	private double sell;

	public enum CurrencyCode {
		AUD, CAD, CHF, CYP, DKK, EUR, GBP, ISK, JPY, NOK, RON, RUB, SEK, USD
	}

}
