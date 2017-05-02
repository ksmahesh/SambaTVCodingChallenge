package com.wundergroundTests.SeleniumTests;

import static io.restassured.RestAssured.when;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import java.util.Calendar;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class RestApiTest {
	public static Response apiResponse;
	public static String jsonAsString;
	public static double expectedVersion = 0.1;
	 @BeforeClass
    public static void statusCodeValidation()
    {
	 when().get("http://api.wunderground.com/api/5c25f5ea989a5950/conditions/q/CA/San_Francisco.json").then().statusCode(200);
    }
	 @BeforeTest
    public static void initResponse()
    {
        apiResponse =when().get("http://api.wunderground.com/api/5c25f5ea989a5950/conditions/q/CA/San_Francisco.json").then().
                contentType(ContentType.JSON). 
            extract().response(); 
        jsonAsString = apiResponse.asString();
    }
//	 Ensure that the API is returning the expected version (v0.1).
	@Test
	public void validateVersion() {
		JsonObject  jobject = (JsonObject) new JsonParser().parse(jsonAsString);
		jobject = jobject.getAsJsonObject("response");
		double version = jobject.get("version").getAsDouble();
		Assert.assertEquals(expectedVersion, version,"test checking the response.version value to be"+expectedVersion);
	}
//	Ensure that current_conditions.temp_f and current_conditions.temp_c are converting between Celsius and Fahrenheit correctly.
	@Test
	public void checkCtoFconversion() {
		JsonObject  jobject = (JsonObject) new JsonParser().parse(jsonAsString);
		jobject = jobject.getAsJsonObject("current_observation");
		double temp_f = jobject.get("temp_f").getAsDouble();
		double temp_c = jobject.get("temp_c").getAsDouble();
		double expected_f = TemperatureConverter.round(TemperatureConverter.convertCToF(temp_c),1);
		double expected_c = TemperatureConverter.round(TemperatureConverter.convertFToC(temp_f),1);
		Assert.assertEquals(temp_c, expected_c,"test checking temperature in C received from api vs that obtained from conversion using F from api");
		Assert.assertEquals(temp_f, expected_f,"test checking temperature in F received from api vs that obtained from conversion using C from api");
	}
//	Ensure that the current_conditions.observation_time string contains the current date.
	@Test
	public void checkDateInString() {
		// Checking if local clock time is matching with server clock is not a good idea so skipping it
		// Clock Synchronisation protocols are themselves approximation based and prone to unsynced clocks so skipping it
		//First Calculating expect vals
		Calendar now = Calendar.getInstance();
		int expectedMonth =  now.get(Calendar.MONTH) + 1;
		int expectedDate  =  now.get(Calendar.DAY_OF_MONTH);
		//now get string received as a part of Json
		JsonObject  jobject = (JsonObject) new JsonParser().parse(jsonAsString);
		jobject = jobject.getAsJsonObject("current_observation");
		String obsTime = jobject.get("observation_time").getAsString();
		//now get date and month vals from the string
		int actualMonth = -1; 
		int actualDate = -1;
		boolean failedToParseDate = false;
		String[] splitVals = obsTime.split(",");
		splitVals = splitVals[0].split(" +");
		for (int i =0; i<splitVals.length; i++) {
			int possibleMonthVal = TemperatureConverter.getMonthForStr(splitVals[i]);
			if (possibleMonthVal != -1) {
				actualMonth = possibleMonthVal;
				try {
					actualDate  = Integer.parseInt(splitVals[i+1]);
				} catch (NumberFormatException e) {
					failedToParseDate = true;
				} catch (ArrayIndexOutOfBoundsException e) {
					failedToParseDate = true;
			    }
				break;// end of if
			}
		}// end of for loop
		failedToParseDate = (actualMonth==-1||actualDate==-1)? true : false;
		if (failedToParseDate) {
			Assert.fail("Was unable to parse for date and month number in the string observation_time");
		}
		Assert.assertEquals(actualDate, expectedDate,"validating the day of the month expected according to local machine time vs that of the server");
		Assert.assertEquals(actualMonth, expectedMonth,"validating the the month expected according to local machine time vs that of the server");
	}
	
}
