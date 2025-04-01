package com.orangehrm.actiondriver;

import java.time.Duration;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import com.orangehrm.base.BaseClass;
import com.orangehrm.utilities.ExtentManager;

public class ActionDriver {

	private WebDriver driver;
	private WebDriverWait wait;
	public static final Logger logger = BaseClass.logger;

	// Initialization
	public ActionDriver(WebDriver driver) {

		this.driver = driver;
		int explicitWait = Integer.parseInt(BaseClass.getProp().getProperty("explicitWait"));
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(explicitWait));
	}

	// Wait for the Page Load
	public void waitForPageLoad(int timeOutInSec) {

		try {
			wait.withTimeout(Duration.ofSeconds(timeOutInSec)).until(WebDriver -> ((JavascriptExecutor) WebDriver)
					.executeScript("return document.readyState;").equals("complete"));
			logger.info("Page loaded successfully.");
		} catch (Exception e) {

			logger.error("Page did not load within " + timeOutInSec + " seconds. Exception: -->" + e.getMessage());
		}
	}

	// Click Element
	public void click(By by) {

		try {
			borderElement(by, "green");
			waitForElementToBeClickable(by);
			logger.info("Clicked an Element --> " + getElementDescription(by));
			ExtentManager.logStep("Clicked an Element --> " + getElementDescription(by));
			driver.findElement(by).click();
		} catch (Exception e) {
			borderElement(by, "red");
			logger.error("Unable to Click Element: -->" + e.getMessage());
			ExtentManager.logFailure(BaseClass.getDriver(), "Unable to Click element",
					getElementDescription(by) + " --> Unable to Click");
		}
	}

	// Scroll to an Element
	public void scrollToElement(By by) {

		try {
			JavascriptExecutor js = (JavascriptExecutor) driver;
			WebElement element = driver.findElement(by);
			js.executeScript("arguments[0].scrollIntoView(true);", element);
		} catch (Exception e) {

			logger.error(" Unable to locate element: -->" + e.getMessage());
		}

	}

	// Method to Enter Text in Input Field or in Text Box
	public void enterText(By by, String value) {

		try {
			borderElement(by, "green");
			waitForElementToBeVisible(by);
			WebElement element = driver.findElement(by);
			logger.info("Enter Text on: " + getElementDescription(by) + " --> " + value);
			element.clear();
			element.sendKeys(value);
		} catch (Exception e) {
			borderElement(by, "red ");
			logger.error(" Unable to Enter Value: -->" + e.getMessage());
		}
	}

	// Method to get Value from text Field
	public String getText(By by) {

		try {
			waitForElementToBeVisible(by);
			borderElement(by, "green");
			ExtentManager.logStepWithScreenshot(driver, "Element is Visible",
					"get Text of Element --> " + getElementDescription(by));
			return driver.findElement(by).getText();
		} catch (Exception e) {
			borderElement(by, "red");
			logger.error(" Unable to get the text: -->" + e.getMessage());
			ExtentManager.logFailure(BaseClass.getDriver(), "Unable to get the text",
					getElementDescription(by) + " --> Unable to get the text");
			return "";
		}

	}

	// Method to Compare two text/Strings
	public boolean compareText(By by, String expectedText) {

		try {
			waitForElementToBeVisible(by);
			String actualText = getText(by);
			if (!actualText.equals(expectedText)) {
				borderElement(by, "red");
				logger.error(" Texts are Not Matching --> Actual: " + actualText + " | Expected: " + expectedText);
				ExtentManager.logFailure(BaseClass.getDriver(),
						" Texts are Not Matching --> Actual: " + actualText + " | Expected: " + expectedText,
						" Texts are Not Matching --> Actual: " + actualText + " | Expected: " + expectedText);

				return false;
			} else {
				borderElement(by, "green");
				logger.info("[PASS] : Texts are Matching --> Actual: " + actualText + " | Expected: " + expectedText);
				ExtentManager.logStepWithScreenshot(driver,
						"[PASS] : Texts are Matching --> Actual: " + actualText + " | Expected: " + expectedText,
						"Texts are Matching");

				return true;
			}
		} catch (Exception e) {
			
			logger.error(" Unable to compare texts: -->" + e.getMessage());
			ExtentManager.logFailure(BaseClass.getDriver(), " Unable to compare texts: -->" + e.getMessage(),
					"Unable to compare texts");
			return false;
		}

	}

	// Wait for Element to be Clickable
	public void waitForElementToBeClickable(By by) {

		try {
			wait.until(ExpectedConditions.elementToBeClickable(by));
		} catch (Exception e) {
			logger.error(" Element is not Clickable: -->" + e.getMessage());
			ExtentManager.logFailure(BaseClass.getDriver(), " Element is not Clickable: -->" + e.getMessage(),
					"Unable to Click Element");
		}
	}

	// Wait for Element to be Visible
	public void waitForElementToBeVisible(By by) {

		try {
			wait.until(ExpectedConditions.visibilityOfElementLocated(by));
			ExtentManager.logStepWithScreenshot(driver, getElementDescription(by) + " is Visible",
					"Visible Element");
		} catch (Exception e) {
			logger.error(" Element is not Visible: -->" + e.getMessage());
			ExtentManager.logFailure(BaseClass.getDriver(), "Not Visible: -->" + e.getMessage(),
					"Not Visible: -->" + getElementDescription(by));
		}
	}

	// Method to Check Element is Displayed
	public boolean isDisplayed(By by) {

		try {
			borderElement(by, "green");
			waitForElementToBeVisible(by);
			logger.info("Element is Displayed --> " + getElementDescription(by));
			ExtentManager.logStepWithScreenshot(driver, "Element is Displayed --> " + getElementDescription(by),
					"Element is Displayed");
			return driver.findElement(by).isDisplayed();

		} catch (Exception e) {
			borderElement(by, "red");
			logger.error(" Unable to fetch Element status: -->" + e.getMessage());
			ExtentManager.logFailure(BaseClass.getDriver(), " Unable to fetch Element status: -->" + e.getMessage(),
					" Unable to fetch Element status: -->" + getElementDescription(by));
			return false;
		}
	}

	// Method to get the description of an element using By locator
	public String getElementDescription(By locator) {

		if (driver == null)
			return "Driver is NULL";
		if (locator == null)
			return "Locator is NULL";

		try {
			// find element using locator
			WebElement ele = driver.findElement(locator);

			// Get Element description
			String text = ele.getText();
			String name = ele.getDomAttribute("name");
			String placeholder = ele.getDomAttribute("placeholder");
			String id = ele.getDomAttribute("id");
			String className = ele.getDomAttribute("class");

			if (isNotEmpty(text))
				return "Element with text:" + truncateString(text, 20);
			else if (isNotEmpty(name))
				return "Element with text:" + name;
			else if (isNotEmpty(placeholder))
				return "Element with text:" + placeholder;
			else if (isNotEmpty(id))
				return "Element with text:" + id;
			else if (isNotEmpty(className))
				return "Element with text:" + className;
		} catch (Exception e) {
			logger.error("Unable to describe the element");
		}

		return "Unable to describe the element";
	}

	private boolean isNotEmpty(String value) {
		return value != null && !value.isEmpty();
	}

	private String truncateString(String value, int maxLength) {
		if (value == null || value.length() <= maxLength) {
			return value;
		}
		return value.substring(0, maxLength);
	}
	
	
	public void borderElement(By by, String color) {
		
		try {
			WebElement element = driver.findElement(by);
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("arguments[0].style.border='6px solid "+color+"'", element);
			logger.info("Applied the border with color "+color+" to element "+getElementDescription(by));
		} catch (Exception e) {
			logger.warn("Failed to apply border to an element: "+getElementDescription(by), e);
		}
	}

}
