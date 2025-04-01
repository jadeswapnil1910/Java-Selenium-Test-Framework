package com.orangehrm.utilities;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class ExtentManager {

	private static ExtentReports extent;
	private static ThreadLocal<ExtentTest> test = new ThreadLocal<>();

	// for Thread Safe WebDriver Parallel Testing { Long* for Thread ID }
	private static Map<Long, WebDriver> driverMap = new HashMap<>();

	// Report path
	private static String extentReportFolder = System.getProperty("user.dir")
			+ "/src/test/resources/ExtentReport/ExtentReport.html";

	// Screenshots path
	private static String screenshotsFolder = System.getProperty("user.dir") + "/src/test/resources/screenshots/";

	// Register WebDriver for Current Thread
	public static void registerDriver(WebDriver driver) {

		driverMap.put(Thread.currentThread().getId(), driver);
	}

	// Initialized extent report
	public synchronized static ExtentReports getReporter() {

		if (extent == null) {

			String reportPath = extentReportFolder;
			ExtentSparkReporter spark = new ExtentSparkReporter(reportPath);
			spark.config().setReportName("TestNg Automation Extent Report");
			spark.config().setDocumentTitle("Orange HRM");
			spark.config().setTheme(Theme.DARK);

			extent = new ExtentReports();
			extent.attachReporter(spark);

			// Add System Information like OS, Java version
			extent.setSystemInfo("Opearting System", System.getProperty("os.name"));
			extent.setSystemInfo("Java Version", System.getProperty("java.version"));
			extent.setSystemInfo("User Name", System.getProperty("user.name"));
		}

		return extent;
	}

	// Start the Test
	public synchronized static ExtentTest startTest(String testName) {

		ExtentTest extentTest = getReporter().createTest(testName);
		test.set(extentTest);
		return extentTest;
	}

	// End of Test
	public synchronized static void endTest() {

		getReporter().flush();
	}

	// Get Current Thread Test Name
	public synchronized static ExtentTest getTest() {

		return test.get();
	}

	// Get the name of current Test
	public static String getTestName() {

		if (getTest() != null) {
			return getTest().getModel().getName();
		} else {
			return "No test Name Found!!!";
		}
	}

	// Get Step details
	public static void logStep(String logMessage) {
		getTest().info(logMessage);
	}

	// Log Step with ScreenShot
	public static void logStepWithScreenshot(WebDriver driver, String logMessage, String screenShotMessage) {

		getTest().pass(logMessage);

		// ScreenShot method
		attachScreentshot(driver, screenShotMessage);
	}

	// Log Failure Message
	public static void logFailure(WebDriver driver, String logMessage, String screenShotMessage) {

		String failRedColorMessage = String.format("<span style='color:red;'>%s</span>", logMessage);

		getTest().fail(failRedColorMessage);

		// Fail ScreenShot method
		attachScreentshot(driver, screenShotMessage);
	}

	// Log a Skip
	public static void logSkip(String logSkipMessage) {

		String skipColorMessage = String.format("<span style='color:orange;'>%s</span>", logSkipMessage);
		getTest().skip(skipColorMessage);
	}

	// Attach screenshot to report using base64
	public synchronized static void attachScreentshot(WebDriver drievr, String message) {

		// Get Screenshot in String format
		try {
			String screenshotBase64 = takeScreenShot(drievr, getTestName());
			getTest().info(message, com.aventstack.extentreports.MediaEntityBuilder
					.createScreenCaptureFromBase64String(screenshotBase64).build());
		} catch (Exception e) {
			getTest().fail("Failed to attached screenshot:" + message);
			e.printStackTrace();
		}
	}

	// Take Screenshot with Date and Time Time stamp
	public synchronized static String takeScreenShot(WebDriver driver, String screenshotName) {

		TakesScreenshot ts = (TakesScreenshot) driver;
		File screenshotAs = ts.getScreenshotAs(OutputType.FILE);

		// Format Date and Time for filename
		String timeStamp = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").format(new Date());

		// Save ss to destination path
		String destPath = screenshotsFolder + screenshotName + "_" + timeStamp;

		File ss_path = new File(destPath);
		try {
			FileUtils.copyFile(screenshotAs, ss_path);
		} catch (IOException e) {
			e.printStackTrace();
		}

		// Convert Screenshot to BASE64 encoding
		return convertToBase64(screenshotAs);
	}

	// Convert Screenshot to BASE64 encoding
	public static String convertToBase64(File screenShotFile) {

		String base64Value = "";

		// Read file content into a Byte array
		try {

			byte[] fileContent = FileUtils.readFileToByteArray(screenShotFile);
			base64Value = Base64.getEncoder().encodeToString(fileContent);

		} catch (IOException e) {
			e.printStackTrace();
		}

		return base64Value;
	}

}
