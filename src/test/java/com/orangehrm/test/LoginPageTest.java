package com.orangehrm.test;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.orangehrm.base.BaseClass;
import com.orangehrm.pages.HomePage;
import com.orangehrm.pages.LoginPage;
import com.orangehrm.utilities.ExtentManager;

public class LoginPageTest extends BaseClass {

	private LoginPage loginPage;
	private HomePage homePage;

	@BeforeMethod
	public void setupPages() {

		loginPage = new LoginPage(getDriver());
		homePage = new HomePage(getDriver());
	}

	@Test
	public void TC01_ValidLoginTest() {

//		System.out.println("Running testMethod1 on thread: " + Thread.currentThread().getId());
//		Thread.currentThread().setName("validLoginTest");
//		System.out.println("Running testMethod1 on thread: " + Thread.currentThread().getName());
		
//		ExtentManager.startTest("validLoginTest"); -- This has been implemented in TestListener class
		
		ExtentManager.logStep("Navigate to Login page and Enter Username and Password.");
		loginPage.login(getProp().getProperty("username"), getProp().getProperty("password"));
		ExtentManager.logStep("User Logged In Successfully.");
		homePage.waitForHomePageToBeLoaded();
		ExtentManager.logStep("Verifying Admin tab is Visiable or Not.");
		Assert.assertTrue(homePage.isAdminTabVisible(), "Admin Tab should be visible after successful login.");
		ExtentManager.logStep("Admin Tab Validation is Successfull.");
		homePage.logoutUser();
		ExtentManager.logStep("User Logged Out Successfully.");

	}

	@Test
	public void TC02_InvalidLoginTest() {

//		System.out.println("Running testMethod1 on thread: " + Thread.currentThread().getId());
//		Thread.currentThread().setName("invalidLoginTest");
//		System.out.println("Running testMethod1 on thread: " + Thread.currentThread().getName());
		
//		ExtentManager.startTest("invalidLoginTest");	-- This has been implemented in TestListener class
		
		ExtentManager.logStep("Navigate to Login page and Enter Username and Password.");
		loginPage.login(getProp().getProperty("invalidUsername"), getProp().getProperty("invalidPassword"));
		String ExpectedErrorMsg = "Invalid credentials";
		staticWait(2);
		ExtentManager.logStep("Verifying Expected Error Message for Invalid Credentials.");
		Assert.assertTrue(loginPage.verifyErrorMessage(ExpectedErrorMsg), "[FAIL] : Invalid Error Message.");
		ExtentManager.logStep("rror Message Validation is Successfull.");
	}

	@Test
	public void TC03_InvalidLoginTestErrorMessage() {

//		System.out.println("Running testMethod1 on thread: " + Thread.currentThread().getId());
//		Thread.currentThread().setName("invalidLoginTestErrorMessage");
//		System.out.println("Running testMethod1 on thread: " + Thread.currentThread().getName());
		
//		ExtentManager.startTest("invalidLoginTestErrorMessage");	-- This has been implemented in TestListener class
		
		loginPage.login("Admin", "admin1234");
		String ExpectedErrorMsg = "Invalid credentials1";
		staticWait(3);
		Assert.assertTrue(loginPage.verifyErrorMessage(ExpectedErrorMsg), "[FAIL] : Invalid Error Message.");
	}
}
