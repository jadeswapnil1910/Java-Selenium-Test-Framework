package com.orangehrm.base;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
// import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
// import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
// import org.openqa.selenium.firefox.FirefoxOptions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
//import org.testng.asserts.SoftAssert;
//import com.aventstack.extentreports.reporter.ExtentReporter;
import com.orangehrm.actiondriver.ActionDriver;
import com.orangehrm.utilities.ExtentManager;
import com.orangehrm.utilities.LoggerManager;

public class BaseClass {

	private static Properties prop;
//	protected static WebDriver driver;

	private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();

	// Only one ActionDriver Object for Singleton Design pattern
//	private static ActionDriver actDriver;

	private static ThreadLocal<ActionDriver> actDriver = new ThreadLocal<>();

	// initialized LogManager
	public static final Logger logger = LoggerManager.getLogger(BaseClass.class);

	/* Load Configuration file. */
	@BeforeSuite
	public void loadConfig() throws IOException {

		prop = new Properties();
		FileInputStream fis = new FileInputStream("src/main/resources/config.properties");
		prop.load(fis);
		logger.info("config.properties file loaded.");
		
		// Start the Extent Report
//		ExtentManager.getReporter();  -- This has been implimented in testListeners
	}

	@BeforeMethod
	public synchronized void setup() {

		logger.info("Setting up Webdriver for: " + this.getClass().getSimpleName());
		initBrowser();
		configureBrowser();
		staticWait(2);
		initActionDriver();

	}

	/* Initialized a WebDriver based on browser define in config.properties file. */
	public synchronized void initBrowser() {

		String browserName = prop.getProperty("BROWSER");

		if (browserName.trim().equalsIgnoreCase("chrome")) {
//			driver = new ChromeDriver();
			driver.set(new ChromeDriver());
			logger.info("ChromeDriver Instance is created.");
			ExtentManager.registerDriver(getDriver());
			
		} else if (browserName.trim().equalsIgnoreCase("edge")) {
//			driver = new EdgeDriver();
			driver.set(new EdgeDriver());
			logger.info("EdgeDriver Instance is created.");
			ExtentManager.registerDriver(getDriver());
			
		} else if (browserName.trim().equalsIgnoreCase("firefox")) {
//			driver = new FirefoxDriver();
			driver.set(new FirefoxDriver());
			logger.info("FirefoxDriver Instance is created.");
			ExtentManager.registerDriver(getDriver());
			
		}
		else {
			logger.info("Invalid Browser Name --> " + browserName);
			throw new IllegalArgumentException("Invalid Browser Name --> " + browserName);
		}

		// Sample logger message
		logger.info("WebDriver Initialized and Browser Maximized");
//		logger.trace("This is a Trace message");
//		logger.error("This is a error message");
//		logger.debug("This is a debug message");
//		logger.fatal("This is a fatal message");
//		logger.warn("This is a warm message");
	}

	// Initialized ActionDriver Instance
	public synchronized void initActionDriver() {

		// Initialize ActionDriver Class object only once
		if (actDriver.get() == null) {
			actDriver.set(new ActionDriver(getDriver()));
			logger.info("ActionDriver Instance is created.");
//			logger.info("Current Thread ID --> " + Thread.currentThread().getId());
		}
	}

	/*
	 * Configure browser settings like Implicit Wait, Maximize Browser and Navigate
	 * to URL.
	 */
	public void configureBrowser() {

		// Implicit Wait
		int implicitWait = Integer.parseInt(prop.getProperty("implicitWait"));
		getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(implicitWait));

		// Maximize Browser
		getDriver().manage().window().maximize();

		// Navigate to URL
		String url = prop.getProperty("url");
		try {
			getDriver().get(url);
			logger.info("Successfully navigated to URL --> " + url);
		} catch (Exception e) {
			logger.info("failed to navigate to url --> " + e.getLocalizedMessage());
		}

	}

	@AfterMethod
	public synchronized void teardown() {

		if (getDriver() != null) {
			try {
				getDriver().quit();
			} catch (Exception e) {

				logger.info("unable to quit the browser --> " + e.getMessage());
			}
		}

		logger.info("WebDriver instance is closed.");

//		driver = null;
//		actDriver = null;

		driver.remove();
		actDriver.remove();

//		ExtentManager.endTest();  -- This has been implemented in TestListener class to flush the Extent report
	}

	// Getter Method for config.properties file
	public static Properties getProp() {
		return prop;
	}



	// Driver Getter Method
	public static WebDriver getDriver() {

		if (driver.get() == null) {
			logger.info("[FAIL] : WebDriver is not initilized");
			throw new IllegalStateException("WebDriver is not initilized");
		}
		return driver.get();
	}

	// ActionDriver Getter Method
	public static ActionDriver getActionDriver() {

		if (actDriver.get() == null) {
			logger.info("[FAIL] : ActionDriver is not initilized");
			throw new IllegalStateException("ActionDriver is not initilized");
		}
		return actDriver.get();
	}

	/* Static Wait for Pause Execution */
	public void staticWait(int seconds) {

		LockSupport.parkNanos(TimeUnit.SECONDS.toNanos(seconds));
	}

}
