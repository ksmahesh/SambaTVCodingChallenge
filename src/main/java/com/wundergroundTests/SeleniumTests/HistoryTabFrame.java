package com.wundergroundTests.SeleniumTests;


import java.util.List;

import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.pagefactory.*;

public class HistoryTabFrame {
	
	@FindBy(how = How.CLASS_NAME, using = "history-date-select")
	private WebElement  historyDateSelect;
	
	@FindBy(how = How.CLASS_NAME, using = "daily-history-select")
	private WebElement dailyHistorySelect;
	
	public boolean validFrame(){
		try {
			this.historyDateSelect.getTagName(); 
			this.dailyHistorySelect.getTagName(); 
	        return true;
	    } catch (NoSuchElementException e) {
	        e.printStackTrace();
	        return false;
	    }
		 
	}
}
