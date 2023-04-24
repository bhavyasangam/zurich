package com.zurich.qa.digitalnative.pages;

import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.zurich.qa.digitalnative.utils.ElementActionsUtil;
import com.zurich.qa.digitalnative.utils.JavaScriptUtil;
import com.zurich.qa.digitalnative.utils.WaitUtil;

import junit.framework.Assert;

public class APACPayPage {
	public WebDriver driver;
	WaitUtil wUtil;
	private static Logger log = LogManager.getLogger(APACPayPage.class);
	ElementActionsUtil elementActionsUtil;
	JavaScriptUtil js;
	public APACPayPage(WebDriver driver) {
		this.driver = driver;
		log.info("Page title {}", driver.getTitle());
		wUtil = new WaitUtil(driver);
		elementActionsUtil  = new  ElementActionsUtil(driver);
		js = new JavaScriptUtil(driver);
		PageFactory.initElements(driver, this);
	}

	@FindBy(id ="paymentMethod-div")
	WebElement payment;

	@FindBy(xpath ="(//span[@class='checkbox-label'])[1]")
	WebElement chkBox1;

	@FindBy(xpath ="(//span[@class='checkbox-label'])[2]")
	WebElement chkBox2;
	
	@FindBy(id = "premium-total-amount text-right")
	WebElement reviewPremium;

	@FindBy(id ="buttonPay")
	WebElement payButton;
	
	// Review Screen webElements
	@FindBy(id = "label-summary-myFirstName")
	WebElement reviewFirstName;

	@FindBy(id = "label-summary-myLastName")
	WebElement reviewLastName;

	@FindBy(id = "label-summary-bornOn")
	WebElement reviewBornOn;

	@FindBy(id = "label-summary-myGender")
	WebElement reviewGender;

	@FindBy(id = "label-summary-myId")
	WebElement reviewId;

	@FindBy(id = "label-summary-withIdNumber")
	WebElement reviewPassportNum;

	@FindBy(id = "label-summary-email")
	WebElement reviewEmail;

	@FindBy(id = "label-summary-mobileNumber")
	WebElement reviewMobileNum;

	@FindBy(id = "buttonReview")
	WebElement reviewBtn;

	@FindBy(id = "accordion-item-travelersdetails-toggle")
	WebElement travellersDetailsExpand;
	
	//	==================
	@FindBy(xpath="//a[@aria-label='facebook']")
	WebElement facebookIcon;
	
	@FindBy(xpath="//a[@aria-label='twitter']")
	WebElement twitterIcon;
	
	@FindBy(xpath="//a[@aria-label='instagram']")
	WebElement instagramIcon;
	
	@FindBy(xpath="//a[@aria-label='youtube']")
	WebElement youtubeIcon;
	
	@FindBy(id="footer-link-0-a")
	WebElement legal;
	
	@FindBy(id="footer-link-1-a")
	WebElement privacyPolicy;
	
	@FindBy(id="footer-link-2-a")
	WebElement termsOfUse;
	
	@FindBy(id="footer-link-3-a")
	WebElement contactUs;
	
	@FindBy(id="footer-label-disclaimer")
	WebElement footerDisclaimer;
	
	@FindBy(id = "Zheader-logo-img-0")
	WebElement zurichLogo;
	//	========================

	public void paymentPage(String paymentMethod) throws InterruptedException {
		wUtil.waitForElementToBeClickble(payment, 1000);
		js.clickElementByJS(payment);
		try {
			elementActionsUtil.perfElementClick(paymentMethod);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void clickCheckbox() {
		wUtil.waitForElementToBeClickble(chkBox1,1000);
		js.clickElementByJS(chkBox1);

		wUtil.waitForElementToBeClickble(chkBox2,1000);
		js.clickElementByJS(chkBox2);
	}

	public void clickPayBtn() {
		wUtil.waitForElementToBeClickble(payButton,2000);
		js.clickElementByJS(payButton);
	}
	
	public String getReviewPremiumAmt() {
		wUtil.waitForElementToBeClickble(payButton,2000);
		return reviewPremium.getText();
	}
	
	public Boolean verifyPayBtnEnabled() {
		Boolean payBtnEnabled = null;
		try {
			payBtnEnabled = payButton.isEnabled();
		} catch (Exception e) {
			log.info("Status of pay button is {}", payButton.isEnabled(), e);
		}
		return payBtnEnabled;
	}

	public void paymentPageDetails(String paymentMethod) {
		try {
			paymentPage(paymentMethod);
			clickCheckbox();
			clickPayBtn();
		} catch (Exception e) {
			log.info("Quote entry page have some issue", e);
		}
	}

	public boolean verifyReviewTravellersDetails(ArrayList<String>  travellersDetails) throws InterruptedException {
		wUtil.waitForElementToBeClickble(reviewBtn, 1000);
		js.clickElementByJS(reviewBtn);
		wUtil.waitForElementToBeVisible(driver.switchTo().activeElement(), 1000);
		travellersDetailsExpand.click();
		ArrayList<String> listOne = new ArrayList<>();
		String[] travellersLabel = { reviewFirstName.getText(), reviewLastName.getText(), reviewBornOn.getText(),
				reviewGender.getText(), reviewId.getText(), reviewPassportNum.getText(), reviewEmail.getText(),
				reviewMobileNum.getText() };
		for (String str : travellersLabel) {
			listOne.add(str);
		}
		boolean isEqual = false;
		for (int i = 0; i < listOne.size(); i++) {
			isEqual = listOne.get(i).equals(travellersDetails.get(i)) ? true : false;
		}
		return isEqual;
	}
	
	//	========= Footer   *****************===============
	@SuppressWarnings("deprecation")
	public void verifyElementinPayPage(WebElement element) {
		wUtil.waitForElementToBeVisible(element);
		boolean isElementPresent = element.isDisplayed();
		Assert.assertTrue(isElementPresent);
		log.info("Element "+element+"is present in page");
	}
	
	public void verifyFooterElementsPayPage() {
		verifyElementinPayPage(facebookIcon);
		verifyElementinPayPage(twitterIcon);
		verifyElementinPayPage(instagramIcon);
		verifyElementinPayPage(youtubeIcon);
		
		verifyElementinPayPage(legal);
		verifyElementinPayPage(privacyPolicy);
		verifyElementinPayPage(termsOfUse);
		verifyElementinPayPage(contactUs);
	}
	
	public void verifyDisclaimerElementPayPage() {
		verifyElementinPayPage(footerDisclaimer);
	}
	
	public void verifyZurichLogoElementPayPage() {
		verifyElementinPayPage(zurichLogo);
	}
	
	
	//	===================================
}
