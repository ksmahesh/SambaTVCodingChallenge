package com.wundergroundTests.SeleniumTests;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class CustomizeTabTests {
	private String urlInTest="https://www.wunderground.com/us/ca/san-francisco/zmw:94102.1.99999";
	WebDriver driver;
	
	@BeforeClass
	public void setup() {
		System.setProperty("webdriver.chrome.driver","/Users/HA/Downloads/chromedriver");
		this.driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
//		 driver.manage().timeouts().pageLoadTimeout(20, TimeUnit.SECONDS);
		 try {
			 this.driver.get(urlInTest);
		 } catch (org.openqa.selenium.TimeoutException te) {
		        ((JavascriptExecutor)this.driver).executeScript("window.stop();");
		 } 
	}
	
	@Test
	public void checkIfCustomizeButtonIsDisplayed() {
		CityWeatherWebPage temp = PageFactory.initElements(this.driver, CityWeatherWebPage.class);
		Assert.assertTrue(temp.customizeDisplayed());
	}
//	Ensure that the dropdown appears when the user clicks on the "Customize" menu within the 10-Day Weather Forecast section. 
//	Also ensure that the first empty checkbox (labelled "Dew Point") can be checked
	@Test
	public void clickCustomizeAndCheckForOverlay() {
		CityWeatherWebPage cwp = PageFactory.initElements(this.driver, CityWeatherWebPage.class);		
		Assert.assertTrue(cwp.isFlotNeedleVisible()); //calling this to ensure that the id=forecast-graph is loaded successfully before clicking on customize button
		cwp.customizeDisplayed();
		cwp.customizeClick();
		PlotVariableMenuFrame plotMenu = PageFactory.initElements(this.driver, PlotVariableMenuFrame.class);
		Assert.assertTrue(plotMenu.dewPointCheckBoxEnabled());
	}
//	On the 10-Day Weather Forecast section within the Forecast Page, ensure that a mouse hover reveals the vertical line displaying metadata 
//	(such as time, temperature, % chance of precipitation, etc.)
	@Test
	public void isPlotNeedleVisible() {
		//This is my first time with something like this
		//the challenge here is that I need to refresh the temp page object repeatedly to get the new values associate with web elements
		//if this is not done, they will continue to have old value
		//also the observation is:
		// when page is loaded initially : the needle is visible
		// when the mouse is hovered over to say historytab: the needle will cease to exist
		// when the mouse is hovered back to the forecast-graph area: the needle is back again
		// so trying to simulate that over here, with  not much success
		//AS the temp.isFlotNeedleVisible() continues to give true through all the steps 
		CityWeatherWebPage temp = PageFactory.initElements(this.driver, CityWeatherWebPage.class);
		temp.isFlotNeedleVisible();
		temp.hoverOverHistory(driver);
		temp = PageFactory.initElements(this.driver, CityWeatherWebPage.class);
		temp.isFlotNeedleVisible();
		temp.hoverOverPlotNeedle(driver);
		temp = PageFactory.initElements(this.driver, CityWeatherWebPage.class);
		Assert.assertTrue(temp.isFlotNeedleVisible());
	}
	
	@Test
	public void isPlotNeedleVisibleWithValues() {
		CityWeatherWebPage temp = PageFactory.initElements(this.driver, CityWeatherWebPage.class);
		//although the span under the div used for the web elements contain the actual value, they are always present in html
		//the only way i could differentiate when the values were visible vs not was :
		//when class contained "plot needle-trailing" the numbers were not visible, so I used that over here
		//However, I am sure, there is a better way to do this.
		Assert.assertTrue(temp.isFlotNeedleVisible());
		Assert.assertTrue(temp.isPrecipValueOnNeedleVisible());
		Assert.assertTrue(temp.isTempOnNeedleVisible());
		Assert.assertTrue(temp.isWindSpeedNeedleValueVisible());
	}
	
	@AfterClass
	public void cleanup() {
		this.driver.close();
	}
}
