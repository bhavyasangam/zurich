package com.zurich.qa.digitalnative.utils;

import static org.testng.Assert.assertEquals;

import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.google.common.base.Function;

public class WaitUtil {

	private WebDriver driver;
	private long elmentLoadWeight = 300; // 3 minutes wait by default
	private static Logger logger = LogManager.getLogger(WaitUtil.class);

	public WaitUtil(WebDriver driver) {
		this.driver = driver;
	}
	
	public void waitForPageLoaded(String expPageTitle) {
		final String actPageTitle = driver.getCurrentUrl();
		System.out.println("actPageTitle  : "+actPageTitle);   
		ExpectedCondition<Boolean> expect = new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver driver) {
				return (driver.getTitle().contains(actPageTitle));
			}
		};
		Wait<WebDriver> wait = new WebDriverWait(driver, elmentLoadWeight);
		try {
			wait.until(expect);
		} catch (Throwable error) {
			logger.info("Timeout waiting for page to load. Page cannot be loaded");
		}
	}

	public WebElement waitForElementToBeClickble(WebElement element) {
		try {
			WebDriverWait wait = new WebDriverWait(driver, elmentLoadWeight);
			element = wait.until(ExpectedConditions.elementToBeClickable(element));
		} catch (NoSuchElementException e) {
			logger.info("Exception in waiting for element {} to be clickable", element);
		}
		return element;
	}

	public WebElement waitForElementToBeClickble(WebElement element, long customWait) {
		try {
			WebDriverWait wait = new WebDriverWait(driver, customWait);
			element = wait.until(ExpectedConditions.elementToBeClickable(element));
		} catch (NoSuchElementException e) {
			logger.info("Exception in waiting for element {} to be clickable", element);
			Assert.fail("Element not found" + element);
		}
		return element;
	}

	public WebElement waitForElementToBeVisible(WebElement element) {
		try {
			WebDriverWait wait = new WebDriverWait(driver, elmentLoadWeight);
			element = wait.until(ExpectedConditions.visibilityOf(element));
		} catch (NoSuchElementException e) {
			logger.info("Exception in waiting for element {} to be visible", element);
			Assert.fail("Element not found" + element);
		}
		return element;
	}

	public WebElement waitForElementToBeVisible(WebElement element, long customWait) {
		try {
			WebDriverWait wait = new WebDriverWait(driver, customWait);
			element = wait.until(ExpectedConditions.visibilityOf(element));
		} catch (NoSuchElementException e) {
			logger.info("Exception in waiting for element {} to be visible", element);
			Assert.fail("Element not found" + element);
		}
		return element;
	}
	
	@SuppressWarnings("deprecation")
	public void waitforElementToBeVisibleByFleuntwait(final By locator) {
		 FluentWait<WebDriver>  wait=new  FluentWait<WebDriver>(driver);
		 //wait.pollingEvery(250, TimeUnit.MILLISECONDS);
		 //wait.withTimeout(2,TimeUnit.MINUTES);
		 wait.ignoring(NoSuchElementException.class);
		  Function<WebDriver, WebElement>  function=new  Function<WebDriver, WebElement>(){

			public WebElement apply(WebDriver input) {
				WebElement  element= driver.findElement(locator);
				if(element!=null) {}
				else {}
				return element;
			}
			  
		  };
		  wait.until(function);
	}
	
	public void waitForEleemntToBeVisibleInFrame(WebElement element, String frame) {
		WebDriverWait wait = new WebDriverWait(driver, elmentLoadWeight);
		try {
		driver.switchTo().frame(frame);
		element =wait.until(ExpectedConditions.visibilityOf(element));
				}
		catch(Exception e) {
			logger.info("Exception in waiting for element {} to be visible in frame", e.getMessage());
		}
	}
	
	public void isAlertPresent() {
		WebDriverWait  wait=new  WebDriverWait(driver,elmentLoadWeight);
		wait.until(ExpectedConditions.alertIsPresent());
		
	}			
	

}
