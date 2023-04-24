package com.zurich.qa.digitalnative.Factory;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import com.zurich.qa.digitalnative.utils.BaseInitializer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.net.URL;
import io.github.bonigarcia.wdm.WebDriverManager;

public class DriverFactory {
	WebDriver driver;
	public static final String USERNAME = "saujanya_c4Asxp";
	public static final String AUTOMATE_KEY = "LnZprXzXSEucsbviy5nN";
	public static final String URL = "https://" + USERNAME + ":" + AUTOMATE_KEY + "@hub-cloud.browserstack.com/wd/hub";
	private Logger browserUtilLogger = LogManager.getLogger(DriverFactory.class);
	public static ThreadLocal<WebDriver> tlDriver = new ThreadLocal<WebDriver>();

	/**
	 * @param myBrowser
	 * @return WebDriver getDriver returns the driver based on what is set in the
	 *         test.properties in each module else it returns Chrome by default
	 */
	public WebDriver getDriver(String browser, String systemRunType, String isParallelExec) {

		if (systemRunType.equals("local")) {
			if (browser.equals("chrome")) {
				System.out.println("Running: " + systemRunType + " : " + browser);
				WebDriverManager.chromedriver().setup();
				driver = new ChromeDriver();
			} else if (browser.equals("FF")) {
				System.out.println("Running: " + systemRunType + " : " + browser);
				WebDriverManager.firefoxdriver().setup();
				driver = new FirefoxDriver();
			} else if (browser.equals("edge")) {
				System.out.println("Running: " + systemRunType + " : " + browser);
				WebDriverManager.edgedriver().setup();
				driver = new EdgeDriver();
			}
		}
		// from here we'll start checking for browserstack test and also parallel test
		else if (systemRunType.equals("browserstack")) {
			if (browser.equals("chrome")) {
				MutableCapabilities capabilities = new MutableCapabilities();
				capabilities.setCapability("browserName", "Chrome");
				capabilities.setCapability("browserVersion", "latest");

				HashMap<String, Object> browserstackOptions = new HashMap<String, Object>();
				browserstackOptions.put("os", "Windows");
				browserstackOptions.put("osVersion", "10");
				browserstackOptions.put("projectName", "APAC BrowserStack Testing");
				browserstackOptions.put("buildName", "APAC cloud testing");
				browserstackOptions.put("sessionName", "APAC cloud testing");

				capabilities.setCapability("bstack:options", browserstackOptions);

				try {
					driver = new RemoteWebDriver(new java.net.URL(URL), capabilities);
				} catch (MalformedURLException e) {
					e.printStackTrace();
				}
			}
			else if (browser.equals("android")) {
				MutableCapabilities capabilities = new MutableCapabilities();
				HashMap<String, Object> browserstackOptions = new HashMap<String, Object>();
				browserstackOptions.put("osVersion", "12");
				browserstackOptions.put("deviceName", "Samsung Galaxy S22 Ultra");
				browserstackOptions.put("realMobile", "true");
				browserstackOptions.put("projectName", "android");
				browserstackOptions.put("buildName", "androidbuild");
				browserstackOptions.put("sessionName", "androidbrowserstack");

				capabilities.setCapability("bstack:options", browserstackOptions);

				try {
					driver = new RemoteWebDriver(new URL(URL), capabilities);
				} catch (MalformedURLException e) {
					e.printStackTrace();
				}
			}
		}
		/*
		 * else if(system.equals("cloud") && device.equals("iphone")) {
		 * MutableCapabilities capabilities = new MutableCapabilities();
		 * //capabilities.setCapability("browserName", "Chrome");
		 * //capabilities.setCapability("browserVersion", "latest");
		 * 
		 * HashMap<String, Object> browserstackOptions = new HashMap<String, Object>();
		 * browserstackOptions.put("osVersion", "14");
		 * browserstackOptions.put("deviceName", "iPhone 12");
		 * browserstackOptions.put("realMobile", "true");
		 * browserstackOptions.put("projectName", "iphone");
		 * browserstackOptions.put("buildName", "iphoneuild");
		 * browserstackOptions.put("sessionName", "iphonebrowserstack");
		 * 
		 * capabilities.setCapability("bstack:options", browserstackOptions);
		 * 
		 * try { driver = new RemoteWebDriver(new URL(URL), capabilities); } catch
		 * (MalformedURLException e) { // TODO Auto-generated catch block
		 * e.printStackTrace(); } }
		 */

		else if (systemRunType.equals("cloud") && isParallelExec.equals("yes")) {
			/*
			 * nnable one=new DeviceOne(); Thread deviceOne=new Thread(one); //Thread
			 * threadOne = new Thread(one); DeviceOne deviceOnes=new DeviceOne();
			 * Hashtable<String, String> oneHashtable =deviceOnes.;
			 * 
			 * try { driver = new RemoteWebDriver(new
			 * java.net.URL(URL),(Capabilities)oneHashtable );
			 * 
			 * } catch (MalformedURLException e) { e.printStackTrace(); }
			 * 
			 * //threadOne.start(); /*Thread threadTwo = new Thread(new DeviceTwo());
			 * threadTwo.start(); Thread threadThree = new Thread(new DeviceThree());
			 * threadThree.start(); Thread threadFour = new Thread(new DeviceFour());
			 * threadFour.start(); Thread threadFive = new Thread(new DeviceFive());
			 * threadFive.start();
			 */
			/*
			 * Thread threadThree = new Thread(new DeviceThree());
			 * 
			 * threadThree.start();
			 */
		}
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		return driver;
	}

	public void executeTest(Hashtable<String, String> capsHashtable) {
		String key;
		DesiredCapabilities caps = new DesiredCapabilities();
		// Iterate over the hashtable and set the capabilities
		Set<String> keys = capsHashtable.keySet();
		Iterator<String> keysIterator = keys.iterator();
		while (keysIterator.hasNext()) {
			key = keysIterator.next();
			caps.setCapability(key, capsHashtable.get(key));
		}
	}

	public String getScreenshot() {
		File srcFile = ((TakesScreenshot) BaseInitializer.getDriver()).getScreenshotAs(OutputType.FILE);
		String path = System.getProperty("user.dir") + "/screenshot/" + System.currentTimeMillis() + ".png";
		File destination = new File(path);

		try {
			FileUtils.copyFile(srcFile, destination);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return path;
	}
}
