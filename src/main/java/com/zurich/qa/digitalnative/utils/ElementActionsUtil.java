package com.zurich.qa.digitalnative.utils;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;

public class ElementActionsUtil {
	private static Logger logger = LogManager.getLogger(ElementActionsUtil.class);
	WaitUtil wUtil;
	private WebDriver driver;
	 private JavaScriptUtil jsUtil;

	public  ElementActionsUtil(WebDriver driver) {
		this.driver = driver;
		jsUtil = new JavaScriptUtil(driver);
	}

	public WebElement perfElementClick(String elementValue) throws InterruptedException {
		WebElement element = null;
	    try {
	         element=driver.findElement(By.xpath("//div[text()='"+elementValue+"']"));
	         jsUtil.clickElementByJS(element);
	    } catch (AssertionError Ae) {
	        Ae.printStackTrace();
	    }
	    return element;
	}

	
	public By getBy(String locatorType, String locatorValue) {
		By locator = null;
		switch (locatorType) {
		case "id":
			locator = By.id(locatorValue);
			break;
		case "name":
			locator = By.name(locatorValue);
			break;
		case "className":
			locator = By.className(locatorValue);
			break;
		case "linkText":
			locator = By.linkText(locatorValue);
			break;
		case "partialLinkText":
			locator = By.partialLinkText(locatorValue);
			break;
		case "xpath":
			locator = By.xpath(locatorValue);
			break;
		case "cssSelector":
			locator = By.cssSelector(locatorValue);
			break;
		case "tagName":
			locator = By.tagName(locatorValue);
			break;
		default:
			System.out.println("Please pass the correct locator " + locatorType);
			break;
		}
		return locator;
	}

	public WebElement getElement(By locator) {
		WebElement element = driver.findElement(locator);
		/*
		 * if(Boolean.parseBoolean(DriverFactory.highlight.trim())) {
		 * jsUtil.flash(element); }
		 */
		return element;
	}

	public WebElement getElement(String locatorType, String locatorValue) {
		return driver.findElement(getBy(locatorType, locatorValue));
	}

	public List<WebElement> getElements(By locator) {
		return driver.findElements(locator);
	}

	public void doSendKeys(By locator, String value) {
		WebElement ele = getElement(locator);
		ele.clear();
		ele.sendKeys(value);
	}

	public void doClick(By locator) {
		getElement(locator).click();
	}

	public String doGetText(By locator) {
		return getElement(locator).getText();
	}

	public String doGetAttribute(By locator, String attrName) {
		return getElement(locator).getAttribute(attrName);
	}

	public boolean doIsDisplayed(By locator) {
		return getElement(locator).isDisplayed();
	}

	public boolean doIsEnabled(By locator) {
		return getElement(locator).isEnabled();
	}

	public void clickOnElement(By locator, String value) {
		List<WebElement> eleList = getElements(locator);
		System.out.println(eleList.size());
		for (WebElement e : eleList) {
			if (e.getText().equals(value)) {
				e.click();
				break;
			}
		}
	}

	public List<String> getLinksTextList(By locator) {
		List<String> eleTextList = new ArrayList<String>();

		List<WebElement> eleList = getElements(locator);
		System.out.println("element count: " + eleList.size());

		for (WebElement e : eleList) {
			String text = e.getText();
			if (!text.isEmpty()) {
				eleTextList.add(text);
			}
		}
		return eleTextList;
	}

	public boolean isElementExist(By locator) {
		List<WebElement> listEle = getElements(locator);
		if (listEle.size() > 0) {
			System.out.println("element is present");
			return true;
		}
		System.out.println("element is not present");
		return false;
	}

	/*Drop Down Utils (Select tag)*/
	public void doSelectByVisibleText(WebElement element, String text) {
		Select select = new Select(element);
		select.selectByVisibleText(text);
	}

	public void doSelectByValue(By locator, String value) {
		Select select = new Select(getElement(locator));
		select.selectByValue(value);
	}

	public void doSelectByIndex(WebElement travelType, int index) {
		Select select = new Select(travelType);
		select.selectByIndex(index);
	}

	public List<String> getDropDownOptionsList(By locator) {
		List<String> optionsValList = new ArrayList<String>();
		Select select = new Select(getElement(locator));
		List<WebElement> optionsList = select.getOptions();

		System.out.println(optionsList.size());

		for (WebElement e : optionsList) {
			String text = e.getText();
			optionsValList.add(text);
		}
		return optionsValList;
	}

	public void selectDropDownValue(By locator, String value) {
		Select select = new Select(getElement(locator));
		List<WebElement> optionsList = select.getOptions();
		System.out.println(optionsList.size());

		for (WebElement e : optionsList) {
			String text = e.getText();
			if (text.equals(value)) {
				e.click();
				break;
			}
		}
	}

	public void clickDropDownValue(By locator, String value) {
		List<WebElement> optionsList = getElements(locator);
		System.out.println(optionsList.size());
		for (WebElement e : optionsList) {
			String text = e.getText();
			if (text.equals(value)) {
				e.click();
				break;
			}
		}
	}

	/* User Actions Utils */

	public void twoLevelMenuHandle(By parentMenu, By childMenu) throws InterruptedException {
		Actions act = new Actions(driver);
		act.moveToElement(getElement(parentMenu)).perform();
		Thread.sleep(2000);
		getElement(childMenu).click();
	}

	public void threeLevelMenuHandle(By parentMenu1, By parentMenu2, By childMenu) throws InterruptedException {
		Actions act = new Actions(driver);
		act.moveToElement(getElement(parentMenu1)).perform();
		Thread.sleep(2000);
		act.moveToElement(getElement(parentMenu2)).perform();
		Thread.sleep(2000);
		getElement(childMenu).click();
	}

	public void doActionsSendKeys(WebElement element, String value) {
		Actions act = new Actions(driver);
		act.sendKeys(element, value).perform();
	}
	
	public void doSendKeys(WebElement element, String value) {
		element.sendKeys(value);
	}
	
	public void doActionsClick(WebElement element) {
		Actions act = new Actions(driver);
		act.click(element).perform();
	}
	
	public String getUrlOfTheScreen() throws Exception {
		return driver.getCurrentUrl();
	}
	
	public String getHref(WebElement element) throws Exception {
		return element.getAttribute("href");
	}
	
	
	
}
