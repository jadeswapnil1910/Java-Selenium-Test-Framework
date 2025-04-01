package com.orangehrm.test;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.orangehrm.base.BaseClass;
import com.orangehrm.pages.HomePage;
import com.orangehrm.pages.LoginPage;
import com.orangehrm.utilities.ExtentManager;

public class HomePageTest extends BaseClass{
	
	private LoginPage loginPage;
	private HomePage homePage;

	@BeforeMethod
	public void setupPages() {

		loginPage = new LoginPage(getDriver());
		homePage = new HomePage(getDriver());
	}
	
	
	@Test
	public void TC04_VerifyHomeTabs() {
		
//		System.out.println("Running testMethod1 on thread: " + Thread.currentThread().getId());
//		Thread.currentThread().setName("verifyHomeTabs");
//		System.out.println("Running testMethod1 on thread: " + Thread.currentThread().getName());
		
//		ExtentManager.startTest("verifyHomeTabs");	-- This has been implemented in TestListener class
		
		ExtentManager.logStep("Navigate to Login page and Enter Username and Password.");
		loginPage.login(getProp().getProperty("username"), getProp().getProperty("password"));
		ExtentManager.logStep("User Logged In Successfully.");
		homePage.waitForHomePageToBeLoaded();
		ExtentManager.logStep("Verifying all Home Page tabs are Visiable or Not.");
		Assert.assertTrue(homePage.isAdminTabVisible(), "Admin Tab should be visible after successful login.");
		homePage.getAllHomePageTabNames();
		ExtentManager.logStep("Home Page Tab Validation is Successfull.");
//		homePage.getAllDashboardTabWidgets();
		homePage.logoutUser();
		ExtentManager.logStep("User Logged Out Successfully.");
	}
	
	@Test
	public void TC05_VerifyDashboardWidgets() {
		
//		System.out.println("Running testMethod1 on thread: " + Thread.currentThread().getId());
//		Thread.currentThread().setName("verifyDashboardWidgets");
//		System.out.println("Running testMethod1 on thread: " + Thread.currentThread().getName());
		
//		ExtentManager.startTest("verifyDashboardWidgets");	-- This has been implemented in TestListener class
		
		ExtentManager.logStep("Navigate to Login page and Enter Username and Password.");
		loginPage.login(getProp().getProperty("username"), getProp().getProperty("password"));
		ExtentManager.logStep("User Logged In Successfully.");
		homePage.waitForHomePageToBeLoaded();
		Assert.assertTrue(homePage.isAdminTabVisible(), "Admin Tab should be visible after successful login.");
		ExtentManager.logStep("Verifying Dashboard Tab Widgets.");
		homePage.getAllDashboardTabWidgets();
		ExtentManager.logStep("Dashboard Tab Widgets validation is Successful.");
		homePage.logoutUser();
		ExtentManager.logStep("User Logged Out Successfully.");
	}

}
