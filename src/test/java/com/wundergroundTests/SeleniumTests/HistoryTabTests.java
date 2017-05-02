package com.wundergroundTests.SeleniumTests;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class HistoryTabTests {
	private static String urlInTest="https://www.wunderground.com/us/ca/san-francisco/zmw:94102.1.99999";
	WebDriver driver;
	//Ensure a click on the "History" tab properly navigates to the history page
	@BeforeMethod
	public void setup() {
		System.setProperty("webdriver.chrome.driver","/Users/HA/Downloads/chromedriver");
		this.driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		 this.driver.get(urlInTest);
	}
	
	@Test
	public void checkHistoryTabIsDisplayed() {
		CityWeatherWebPage temp = PageFactory.initElements(this.driver, CityWeatherWebPage.class);
		Assert.assertTrue(temp.historyTabDisplayed());
	}
	
	@Test
	public void clickHistoryTabVerifyUsingTitle() {
		CityWeatherWebPage temp = PageFactory.initElements(this.driver, CityWeatherWebPage.class);
		try {
			temp.historyTabClick();
		} catch (org.openqa.selenium.TimeoutException te) {
	        ((JavascriptExecutor)this.driver).executeScript("window.stop();");
	    } 
		Assert.assertTrue(this.driver.getTitle().startsWith("Weather History"));
	}
	
	@Test
	public void clickHistoryTabVerifyUsingFrame() {
		CityWeatherWebPage temp = PageFactory.initElements(this.driver, CityWeatherWebPage.class);
		try {
			temp.historyTabClick();
		} catch (org.openqa.selenium.TimeoutException te) {
	        ((JavascriptExecutor)this.driver).executeScript("window.stop();");
	    } 
		HistoryTabFrame historyFrame = PageFactory.initElements(this.driver, HistoryTabFrame.class);
		historyFrame.validFrame();
	}
	
	@AfterMethod
	public void cleanup() {
		this.driver.close();
	}
}
