package com.wundergroundTests.SeleniumTests;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DateFormatSymbols;

public class TemperatureConverter {
	public static double round(double value, int places) {
	    if (places < 0) throw new IllegalArgumentException();

	    BigDecimal bd = new BigDecimal(value);
	    bd = bd.setScale(places, RoundingMode.HALF_UP);
	    return bd.doubleValue();
	}
	public static double convertCToF(double celsius) {
		Double fahrenheit;
		fahrenheit = ((celsius * 9) / 5) + 32;
		return fahrenheit;
	}
	public static double convertFToC(double fahrenheit) {
		Double celsius;
		celsius = ((fahrenheit - 32)*5)/9;
		return celsius;
	}
	
	public static int getMonthForStr(String month) {
		month = month.trim();
		int ret = -1;
        DateFormatSymbols dfs = new DateFormatSymbols();
        String[] months = dfs.getMonths();
        for(int i =0; i<months.length; i++) {
        	String temp = months[i];
        	if (temp.equalsIgnoreCase(month)) {
        		ret = i+1;
        	}
        }
        return ret;
    }
}
