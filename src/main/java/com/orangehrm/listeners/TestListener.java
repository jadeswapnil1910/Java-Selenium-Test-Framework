package com.orangehrm.listeners;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import com.orangehrm.base.BaseClass;
import static com.orangehrm.utilities.ExtentManager.*;

public class TestListener implements ITestListener {

	// Trigger when a Suite Start
	@Override
	public void onStart(ITestContext context) {

		// Initialize the Extent Report
		getReporter();
	}

	// Trigger when the Suite Ends
	@Override
	public void onFinish(ITestContext context) {

		// Flush Extent Report
		endTest();
	}

	// Trigger When Test Starts / Before each methods
	@Override
	public void onTestStart(ITestResult result) {

		String testName = result.getMethod().getMethodName();

		// Start Extent report logging
		startTest(testName); // --ExtentManager Static Import
		logStep("Test Started : " + testName);
	}

	// Trigger when test succeeds.
	@Override
	public void onTestSuccess(ITestResult result) {

		String testName = result.getMethod().getMethodName();

		logStepWithScreenshot(BaseClass.getDriver(), "Test Passed Successfully!",
				"Test End: " + testName + " - ✔ Test Passed");
	}

	// Trigger when test failed
	@Override
	public void onTestFailure(ITestResult result) {

		String testName = result.getMethod().getMethodName();

		String message = result.getThrowable().getMessage();
		logStep(message);
		logFailure(BaseClass.getDriver(), "Test Failed!", "Test End: " + testName + " - ❌ Test Failed");
	}

	// Trigger when test Skipped.
	@Override
	public void onTestSkipped(ITestResult result) {

		String testName = result.getMethod().getMethodName();

		logSkip("Test Skipped! : " + testName);

	}

}
