package com.wundergroundTests.SeleniumTests;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class PlotVariableMenuFrame {
	@FindBy(how = How.ID, using = "plotVariablesMenu")
	private WebElement  plotVariablesMenu;
	
	@FindBy(how = How.ID, using = "cp_var_dewpoint")
	private WebElement  dewpointCheckBox;
	
	public boolean dewPointCheckBoxEnabled() {
		return dewpointCheckBox.isEnabled();
	}
}
