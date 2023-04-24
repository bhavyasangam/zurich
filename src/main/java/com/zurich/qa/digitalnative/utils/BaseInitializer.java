package com.zurich.qa.digitalnative.utils;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.zurich.qa.digitalnative.Factory.DriverFactory;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;

public class BaseInitializer {

		public PropertiesUtil propUtil;
		public DriverFactory browUtil;
		public String currentModule;

		public static String dirPath;
		public static WebDriver driver;
		private static Logger logger = LogManager.getLogger(BaseInitializer.class);
		public String myBrowser;
		public static String mod_name;
		public static String demoMode;

		public static BaseInitializer base;

		/**
		 * Creating a private constructor so that the BaseInitializer class is not
		 * created outside this class.
		 */
		public BaseInitializer(String module) {
			this.currentModule = module;
		}

		/**
		 * Create a static method to get instance.
		 */
		public static BaseInitializer getInstance(String module) {
			logger.info("Executing :: {}", BaseInitializer.getMethodName(Thread.currentThread().getStackTrace()));
			if (base == null) {
				base = new BaseInitializer(module);
				base.initialize();
			}
			return base;
		}

		/**
		 * @param module
		 *            This method takes the module name - which is got based on in
		 *            which testscript it is running It initializes all the
		 *            properties, creates the output directory in which the
		 *            screenshots and the logfile should go Creates the database
		 *            connection only if the dbConnection is set to on in the
		 *            test.properties Gets the driver and navigates to the url
		 */
		public void initialize() {
			logger.info("Executing :: {}", BaseInitializer.getMethodName(Thread.currentThread().getStackTrace()));
			try {
				propUtil = new PropertiesUtil();
				propUtil.initProperties(this.currentModule);
				browUtil = new DriverFactory();
			} catch (Exception e) {
				
				logger.info("Exception in BaseInitializer {}", e.getMessage());
				e.printStackTrace();
				Assert.fail("Failed to initialize in BaseInitializer");
			}
		}

	
		/**
		 * @param ste
		 * @return This method returns the methodname from where ever it is called
		 *         It is called in the testmethods to get the methodnames for
		 *         logging
		 */
		public static String getMethodName(StackTraceElement ste[]) {
			String methodName = "";
			boolean flag = false;
			for (StackTraceElement s : ste) {
				if (flag) {
					methodName = s.getMethodName();
					break;
				}
				flag = s.getMethodName().equals("getStackTrace");
			}
			return methodName;
		}

		public DriverFactory getBrowserUtil() {
			return browUtil;
		}

		public WebDriver getwebDriver() {
			logger.info("Executing :: {}", BaseInitializer.getMethodName(Thread.currentThread().getStackTrace()));
			driver = browUtil.getDriver(propUtil.myBrowser, propUtil.runType, propUtil.isParallelExe );
			driver.get(propUtil.fullURL);
			logger.info("BaseUrl is :: " + propUtil.fullURL);
			driver.manage().window().maximize();
			driver.manage().deleteAllCookies();
			return driver;
		}

		public static String getPath() {
			return dirPath;
		}

		public static WebDriver getDriver() {
			return driver;
		}

		public String getBaseUrl() {
			return propUtil.fullURL;
		}

		public String getBrowser() {
			return propUtil.myBrowser;
		}
		
		public String getRuntype() {
			return propUtil.runType;
		}

		
		public static String moduleName(Object a) {
			System.out.println("Package name :: " + a.getClass().getPackage());
			logger.info("Package name :: {}", a.getClass().getPackage());
			String moduleName[] = a.getClass().getPackage().toString().split("\\.");
			System.out.println("Module name :: "
					+ moduleName[moduleName.length - 2]);
			logger.info("Module name :: {}", moduleName[moduleName.length - 2]);
			mod_name = moduleName[moduleName.length - 2];
			return moduleName[moduleName.length - 2];
		}
		
		public String getScreenshot() {
			File srcFile = ((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.FILE);
			String path = System.getProperty("user.dir") + "/screenshot/" + System.currentTimeMillis() + ".png";
			File destination = new File(path);

			try {
				FileUtils.copyFile(srcFile, destination);
			} catch (IOException e) {
				e.printStackTrace();
			}
			return path;
		}


		/**
		 * This method kills the instance of the BaseInitializer before the next
		 * module runs So this should be the last testclass called as part of the
		 * module level testng like say payroll.xml
		 */
		@Test
		public void killInstance() {
			//driver.quit();
			base = null;
		}
	
}
