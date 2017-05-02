package com.wundergroundTests.SeleniumTests;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
public class MouseHover {
 
	public static WebDriver driver;
 
   public static void main(String[] args) {
	   System.setProperty("webdriver.chrome.driver","/Users/HA/Downloads/chromedriver");
        driver = new ChromeDriver();
 
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
 
        driver.get("https://www.wunderground.com/us/ca/san-francisco/zmw:94102.1.99999?MR=1");
 
        WebElement needle = driver.findElement(By.className("flot-needle"));
        WebElement historyTab = driver.findElement(By.id("city-nav-history"));
 
        Actions action = new Actions(driver);
 
        action.moveToElement(needle).build().perform();
        try {
			Thread.sleep(5000);
			System.out.println("howvering now");
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        action.moveToElement(historyTab).build().perform();
        try {
			Thread.sleep(5000);
			System.out.println("howvering now");
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        action.moveToElement(needle).build().perform();
        driver.close();
        }
 
}
