package com.wundergroundTests.SeleniumTests;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.pagefactory.*;

public class CityWeatherWebPage {
	
	@FindBy(how = How.ID, using = "city-nav-history")
	private WebElement historyTab;
	
	@FindBy(how = How.ID, using = "editMode")
	private WebElement customize;
	
	@FindBy(how = How.CLASS_NAME, using = "plot-needle")
	private WebElement plotVisible;
	@FindBy(how = How.CLASS_NAME, using = "plot-needle-now")
	private WebElement plotNeedleNow;
	
	@FindBy(how = How.CLASS_NAME, using = "flot-text")
	private WebElement plotn1;
	
	@FindBy(how = How.XPATH, using ="//*[@id=\"forecast-graph\"]/div/div[2]/div[12]")
	private WebElement flotNeedle;
	
	@FindBy(how = How.XPATH, using ="//*[@id='forecast-graph']/div/div[2]/div[3]/div[2]/div/div[3]")
	private WebElement tempNeedleValue;
	
	@FindBy(how = How.XPATH, using ="//*[@id='forecast-graph']/div/div[2]/div[5]/div[2]/div/div[6]")
	private WebElement precipNeedleValue;
	
	@FindBy(how = How.XPATH, using ="//*[@id='forecast-graph']/div/div[2]/div[1]/div[3]/div/div")
	private WebElement timeNeedleValue;
	
	@FindBy(how = How.XPATH, using ="//*[@id='forecast-graph']/div/div[2]/div[9]/div[2]/div")
	private WebElement windSpeedNeedleValue;
	
	public void historyTabClick() {
		this.historyTab.click();
	}

	public boolean historyTabDisplayed() {
		try {
	        this.historyTab.getTagName();
	        return true;
	    } catch (NoSuchElementException e) {
	        e.printStackTrace();
	        return false;
	    }
	}
	public boolean customizeDisplayed() {
		try {
	        this.customize.getLocation();
	        return true;
	    } catch (NoSuchElementException e) {
	        e.printStackTrace();
	        return false;
	    }
	}
	public void customizeClick() {
		this.customize.click();
	}
	public boolean classContainsTrailing(String classVal) {
		StringBuilder sb = new StringBuilder("trailing");
		if(classVal.contains(sb)) {
			return false;
		}
		return true;
	}
	public boolean isTempOnNeedleVisible() {
		return classContainsTrailing(this.tempNeedleValue.getAttribute("class"));
	}
	public boolean isPrecipValueOnNeedleVisible() {
		return classContainsTrailing(this.precipNeedleValue.getAttribute("class"));
	}
	public boolean isWindSpeedNeedleValueVisible() {
		return classContainsTrailing(this.windSpeedNeedleValue.getAttribute("class"));
	}
	public boolean isFlotNeedleVisible(){
		String displayValue = flotNeedle.getCssValue("display");
		if(displayValue.compareTo("block")==0) {
			return true;
		}
		return false;
	}
	public void hoverOverPlotNeedle(WebDriver driver) {
		Actions actions = new Actions(driver);
		actions.moveToElement(plotn1, 0, 0).build().perform();
	}
	
	public void hoverOverHistory(WebDriver driver) {
		Actions actions = new Actions(driver);
		actions.moveToElement(this.historyTab).build().perform();
	}
	
	public boolean isPlotVisible() {
		
		return  this.plotVisible.isEnabled(); 
	}
}
