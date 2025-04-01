package com.orangehrm.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.orangehrm.actiondriver.ActionDriver;
import com.orangehrm.base.BaseClass;

public class LoginPage {

	private ActionDriver actDriver;

	// Define all locators using By class
	private By usernameField = By.name("username");
	private By passwordField = By.cssSelector("input[type='password']");
	private By loginButton = By.xpath("//button[@type='submit']");
	private By errorMessage = By.xpath("//p[text()='Invalid credentials']");

	/*
	 * 
	 * // LoginPage Constructor 
	 * public LoginPage(WebDriver driver) {
	 * 
	 * this.actDriver = new ActionDriver(driver); }
	 * 
	 * 
	 */

	// LoginPage Constructor for Singleton Design Pattern
	public LoginPage(WebDriver driver) {

		this.actDriver = BaseClass.getActionDriver();
	}

	// Perform Login
	public void login(String username, String password) {

		actDriver.enterText(usernameField, username);
		actDriver.enterText(passwordField, password);
		actDriver.click(loginButton);

	}

	// Check if Error Message is Displayed.
	public boolean isErrorMeassageDiaplayed() {
		return actDriver.isDisplayed(errorMessage);
	}

	// Get text from Error Message
	public String getErrorMesaage() {
		return actDriver.getText(errorMessage);
	}

	// Verify Error Message is correct or Not
	public boolean verifyErrorMessage(String expectedErrorMsg) {

		return actDriver.compareText(errorMessage, expectedErrorMsg);
	}

}
