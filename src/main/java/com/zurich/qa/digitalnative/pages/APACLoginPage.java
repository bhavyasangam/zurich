package com.zurich.qa.digitalnative.pages;

import java.util.Scanner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.zurich.qa.digitalnative.utils.ElementActionsUtil;
import com.zurich.qa.digitalnative.utils.JavaScriptUtil;
import com.zurich.qa.digitalnative.utils.WaitUtil;

public class APACLoginPage {

	public WebDriver driver;
	WaitUtil wUtil;
	private static Logger log = LogManager.getLogger(APACLoginPage.class);
	ElementActionsUtil elementActionsUtil;
	JavaScriptUtil js;
	
	@FindBy(xpath = "//h1[@class='ml-banner-label font-size-banner line-height-banner']")
	WebElement loginScreen;
	
	//Need to be updated giving without xpath
	@FindBy(xpath = "//button[@id='SignIn-WithEmail']")
	WebElement signInWithEmailBtn;
	
	@FindBy(xpath = "//a[@class='btn btn--primary']")
	WebElement signIn;
	
	@FindBy(xpath = "//button[@class='accountButton claims-provider-selection btn text-center min-w-button mb-4']")
	WebElement signUpBtn;
	
	// Need to be updated
	@FindBy(xpath = "//input[@id='emailAddress']")
	WebElement emailAddressTxt;
	
	@FindBy(xpath = "//button[@id='emailVerificationControl_but_send_code']")
	WebElement sendCodeBtn;
	
	@FindBy(id = "emailVerificationControl_but_verify_code")
	WebElement verifyCodeBtn;
	
	@FindBy(id = "emailVerificationControl_error_message")
	WebElement invalidEmail;
	
	@FindBy(id = "//input[@id='verificationCode']")
	WebElement OTPCodeTxt;
	
	
	public APACLoginPage(WebDriver driver) {
		this.driver = driver;
		log.info("Page title {}", driver.getTitle());
		wUtil = new WaitUtil(driver);
		elementActionsUtil = new ElementActionsUtil(driver);
		js = new JavaScriptUtil(driver);
		PageFactory.initElements(driver, this);
	}
	
	public void signinWithEmail(String emailAddress) {
		try {
			wUtil.waitForElementToBeVisible(signIn);
			js.clickElementByJS(signIn);
			wUtil.waitForElementToBeVisible(signInWithEmailBtn);
			js.clickElementByJS(signInWithEmailBtn);
			
			wUtil.waitForElementToBeVisible(emailAddressTxt);
			elementActionsUtil.doSendKeys(emailAddressTxt, emailAddress);
			wUtil.waitForElementToBeVisible(sendCodeBtn);
			js.clickElementByJS(sendCodeBtn);
			Thread.sleep(20000);
			//wUtil.waitForElementToBeVisible(OTPCodeTxt);
			//elementActionsUtil.doSendKeys(OTPCodeTxt, my_str);
		
		} catch (Exception e) {
			log.info("Unable to signIn in portfolio with registered user", e);
		}
	}
	
	public APACPortfolioPage clickVerifyCode() {
		wUtil.waitForElementToBeVisible(verifyCodeBtn);
		js.clickElementByJS(verifyCodeBtn);
		return  new APACPortfolioPage(driver);
	}
	
	public Boolean verifyLoginMessageDisplayed() {
		Boolean loginMessage = null;
		try {
			loginMessage = loginScreen.isDisplayed();
		} catch (Exception e) {
			log.info("Status of login screen is {}", loginScreen.isDisplayed(), e);
		}
		return loginMessage;
	}
	
	public Boolean verifyinvalidEmailMessageDisplayed() {
		Boolean invalidEmailAddress = null;
		try {
			invalidEmailAddress = invalidEmail.isDisplayed();
		} catch (Exception e) {
			log.info("Status of login screen is {}", invalidEmail.isDisplayed(), e);
		}
		return invalidEmailAddress;
	}
	
}
