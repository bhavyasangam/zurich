package com.zurich.qa.digitalnative.pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.zurich.qa.digitalnative.utils.ElementActionsUtil;
import com.zurich.qa.digitalnative.utils.JavaScriptUtil;
import com.zurich.qa.digitalnative.utils.WaitUtil;

public class APACRegisterPage {

	public WebDriver driver;
	WaitUtil wUtil;
	private static Logger log = LogManager.getLogger(APACRegisterPage.class);
	ElementActionsUtil elementActionsUtil;
	JavaScriptUtil js;
	
	public APACRegisterPage(WebDriver driver) {
		this.driver = driver;
		log.info("Page title {}", driver.getTitle());
		wUtil = new WaitUtil(driver);
		elementActionsUtil = new ElementActionsUtil(driver);
		js = new JavaScriptUtil(driver);
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(xpath = "//input[@id='givenName']")
	WebElement firstName;
	
	@FindBy(xpath = "//input[@id='surname']")
	WebElement lastName;
	
	@FindBy(xpath = "//input[@id='extension_countryCode']")
	WebElement countryCode;
	
	@FindBy(xpath = "//input[@id='extension_mobilePhone']")
	WebElement mobilePhone;
	
	@FindBy(xpath = "//input[@id='email']")
	WebElement email;
	
	@FindBy(xpath = "//input[@id='email_ver_input']")
	WebElement verfificationCode;
	
	@FindBy(xpath = "//button[@id='email_ver_but_send']")
	WebElement verfificationButton;
	
	@FindBy(xpath = "//button[@id='continue']")
	WebElement continueButton;
	
	@FindBy(xpath = "//button[@id='email_ver_but_resend']")
	WebElement sendNewCode;
}
