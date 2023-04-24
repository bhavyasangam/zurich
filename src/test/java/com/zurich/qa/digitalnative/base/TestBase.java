package com.zurich.qa.digitalnative.base;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.log4testng.Logger;
import org.testng.xml.XmlTest;

import com.zurich.qa.digitalnative.utils.BaseInitializer;
import com.zurich.qa.digitalnative.utils.PropertiesUtil;
import com.zurich.qa.digitalnative.utils.WaitUtil;

public class TestBase {

	public static WebDriver driver;
	public static Properties prop;
	public BaseInitializer bInit;
	
	public Logger logger;
	public WaitUtil zurichWait;
	public  String testname;
	
	@BeforeSuite()
	public void beforeSuite() {
	}
	
	@BeforeClass
	public void beforeClass() {
	}
	
	@BeforeMethod
	public void beforeMethod() {
		bInit = BaseInitializer.getInstance(BaseInitializer.moduleName(this));
		driver = bInit.getwebDriver();
		zurichWait =new WaitUtil(driver);		
	}

	@AfterMethod
	public void afterMethod() {
		driver.quit();
	}

	@BeforeTest
	public void setOptions(XmlTest test) {
		test.setGroupByInstances(true);
		try {
				testname= test.getName();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@AfterClass
	public void afterClass() {
		driver.quit();
	}	
	
}
