package com.orangehrm.base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

public class TestBrowserVersion {

	public static void main(String[] args) throws InterruptedException {

		chrome();
		edge();
		geko();

	}

	public static void chrome() throws InterruptedException {

		ChromeOptions options = new ChromeOptions();
		options.setBrowserVersion("Beta");

		WebDriver driver = new ChromeDriver(options);
		driver.get("https://opensource-demo.orangehrmlive.com/");
		Thread.sleep(5000); // Wait for 5 seconds to observe the browser version
		driver.quit();
	}

	public static void edge() throws InterruptedException {

		EdgeOptions options = new EdgeOptions();
		options.setBrowserVersion("Stable");

		WebDriver driver = new EdgeDriver(options);
		driver.get("https://opensource-demo.orangehrmlive.com/");
		Thread.sleep(5000); // Wait for 5 seconds to observe the browser version
		driver.quit();
	}

	public static void geko() throws InterruptedException {

		FirefoxOptions options = new FirefoxOptions();
		options.setBrowserVersion("stable");

		WebDriver driver = new FirefoxDriver(options);
		driver.get("https://opensource-demo.orangehrmlive.com/");
		Thread.sleep(5000); // Wait for 5 seconds to observe the browser version
		driver.quit();
	}

}
