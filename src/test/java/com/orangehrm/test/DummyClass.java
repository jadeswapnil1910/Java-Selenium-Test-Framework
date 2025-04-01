package com.orangehrm.test;

import org.jspecify.annotations.Nullable;
import org.testng.SkipException;
import org.testng.annotations.Test;

import com.orangehrm.base.BaseClass;
import com.orangehrm.utilities.ExtentManager;

public class DummyClass extends BaseClass {

	@Test
	public void dummyTest() {
		
//		ExtentManager.startTest("Dummy test");

		@Nullable
		String title = getDriver().getTitle();
		assert title.equals("OrangeHRM") : "Test Failed - Title is not matched";

		ExtentManager.logStep("Title is Matched Successfully");
		ExtentManager.logSkip("Test Case is Skipped...");
		throw new SkipException("Skipping Test for Extent Reporting");
		
		
	}

	

}
