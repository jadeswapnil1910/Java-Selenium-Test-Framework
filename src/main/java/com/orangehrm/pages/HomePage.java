package com.orangehrm.pages;

import java.util.List;

import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.orangehrm.actiondriver.ActionDriver;
import com.orangehrm.base.BaseClass;

public class HomePage {

	private ActionDriver actDriver;
	public static final Logger logger = BaseClass.logger;

	// Define all locators using By class
	private By adminTab = By.xpath("//span[text()='Admin']");
	private By dashboardTab = By.xpath("//span[text()='Dashboard']");
	private By userIDBtn = By.cssSelector("span[class*='userdropdown']");
	private By logoutBtn = By.xpath("//a[text()='Logout']");
	private By orangeHRMLogo = By.cssSelector(".oxd-brand-banner>img");
	private By homePageTabs = By.cssSelector("ul.oxd-main-menu span");
	private By dashboardWidgets = By.cssSelector("div [class*='dashboard-grid'] div[class*='widget-name']>p");

	/*
	 * 
	 * // HomePage Constructor public HomePage(WebDriver driver) {
	 * 
	 * this.actDriver = new ActionDriver(driver); }
	 * 
	 */

	// HomePage Constructor for Singleton Design Pattern
	public HomePage(WebDriver driver) {

		this.actDriver = BaseClass.getActionDriver();
	}

	public void waitForHomePageToBeLoaded() {

		actDriver.waitForPageLoad(5);
	}

	// Verify if Admin Tab is Present
	public boolean isAdminTabVisible() {
		return actDriver.isDisplayed(adminTab);
	}

	public boolean verifyOrangeHRMLogo() {
		return actDriver.isDisplayed(orangeHRMLogo);
	}
	
	public void getAllHomePageTabNames() {
		
		logger.info("Home Page Tabs.");
		List<WebElement> elements = BaseClass.getDriver().findElements(homePageTabs);
		for (int i = 0; i < elements.size(); i++) {
			WebElement ele = elements.get(i);
			logger.info("Tab Name --> "+ele.getText());			
		}
	}
	
	public void getAllDashboardTabWidgets() {
		
		actDriver.click(dashboardTab);
		actDriver.waitForPageLoad(2);
		
		logger.info("Dashboard Tab Widgets.");
		List<WebElement> elements = BaseClass.getDriver().findElements(dashboardWidgets);
		for (int i = 0; i < elements.size(); i++) {
			WebElement ele = elements.get(i);
			logger.info("Widget Name --> "+ele.getText());			
		}
		
	}

	// Logout operation
	public void logoutUser() {

		actDriver.click(userIDBtn);
		actDriver.click(logoutBtn);
	}

}
