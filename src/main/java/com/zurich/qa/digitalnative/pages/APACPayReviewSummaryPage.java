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

public class APACPayReviewSummaryPage {
	public WebDriver driver;
	WaitUtil wUtil;
	private static Logger log = LogManager.getLogger(APACPayPage.class);
	ElementActionsUtil elementActionsUtil;
	JavaScriptUtil js;

	@FindBy(id = "accordion-item-traveldetails-toggle")
	WebElement travelsDetailsExpand;

	@FindBy(id = "accordion-item-plandetails-toggle")
	WebElement planDetailsExpand;

	@FindBy(id = "editStepOne")
	WebElement travelDetailsEdit;

	@FindBy(id = "editStepTwo")
	WebElement planDetailsEdit;

	@FindBy(id = "editStepThree")
	WebElement travellersDetailsEdit;

	@FindBy(id = "value-summary-myPlan")
	WebElement planDetailsAfterEdit;

	@FindBy(id = "value-summary-myGender")
	WebElement travellersDetailsGenderEdit;

	@FindBy(xpath = "//div[@class='row mb-4']")
	WebElement travellersDetailsAfterEdit;

	@FindBy(id = "summaryDetailsSideBar-btn-dismiss")
	WebElement closeReviewSummary;

	@FindBy(id = "productsummary")
	WebElement productSummary;

	@FindBy(id = "value-summary-departingOn")
	WebElement dateCheck;

	@FindBy(id = "ZsideBar-title-summaryDetailsSideBar")
	WebElement summarySidebar;

	@FindBy(id = "premium-main-amount")
	WebElement amountPremium;

	@FindBy(id = "premium-addon-0-amount")
	WebElement amountPremiumAddon;

	@FindBy(id = "value-summary-travelingWith")
	WebElement travellingWith;

	@FindBy(id = "value-summary-myPlan")
	WebElement travellingPlanDetails;

	@FindBy(id = "ZprogressBar-step-1-icon")
	WebElement progressBarStep1;

	@FindBy(id = "ZprogressBar-step-2-icon")
	WebElement progressBarStep2;

	@FindBy(id = "ZprogressBar-step-3-icon")
	WebElement progressBarStep3;

	@FindBy(id = "ZprogressBar-step-4-icon")
	WebElement progressBarStep4;

	@FindBy(id = "paymentSuccess")
	WebElement paymentSuccessfulMsg;

	@FindBy(id = "policyIssuance")
	WebElement policyIssuance;

	@FindBy(id = "paymentFailure")
	WebElement paymentFailureMsg;

	@FindBy(id = "myPolicies")
	WebElement myPolicy;

	@FindBy(id = "customerPortfolio")
	WebElement customerPortfolioScreen;

	@FindBy(id = "helplineNumber")
	WebElement helplineNumber;

	@FindBy(id = "imageDisplayed")
	WebElement imageDisplayed;

	@FindBy(id = "summary-title")
	WebElement premiumText;

	@FindBy(id = "header-paymentMethod")
	WebElement payWithText;

	@FindBy(id = "label-terms")
	WebElement termsText;

	@FindBy(id = "buttonReview")
	WebElement reviewBtn;

	@FindBy(id = "accordion-item-travelersdetails-toggle")
	WebElement travellersDetailsExpand;

	public APACPayReviewSummaryPage(WebDriver driver) {
		this.driver = driver;
		log.info("Page title {}", driver.getTitle());
		wUtil = new WaitUtil(driver);
		elementActionsUtil = new ElementActionsUtil(driver);
		js = new JavaScriptUtil(driver);
		PageFactory.initElements(driver, this);
	}

	public void progressBar1() {
		js.clickElementByJS(progressBarStep1);
	}

	public void progressBar2() {
		js.clickElementByJS(progressBarStep2);
	}

	public void progressBar3() {
		js.clickElementByJS(progressBarStep3);
	}

	public void progressBar4() {
		js.clickElementByJS(progressBarStep4);
	}

	public Boolean verifyProgressBar1() {
		Boolean progressBar1 = null;
		try {
			progressBar1 = progressBarStep1.isEnabled();
		} catch (Exception e) {
			log.info("Status of pay button is {}", progressBarStep1.isEnabled(), e);
		}
		return progressBar1;
	}

	public Boolean verifyProgressBar2() {
		Boolean progressBar2 = null;
		try {
			progressBar2 = progressBarStep2.isEnabled();
			
			String bar2=progressBarStep2.getCssValue("color");
			System.out.println("enabled"+bar2);
		} catch (Exception e) {
			log.info("Status of pay button is {}", progressBarStep2.isEnabled(), e);
		}
		return progressBar2;
	}

	public Boolean verifyProgressBar3() {
		Boolean progressBar3 = null;
		try {
			progressBar3 = progressBarStep3.isEnabled();
		} catch (Exception e) {
			log.info("Status of pay button is {}", progressBarStep3.isEnabled(), e);
		}
		return progressBar3;
	}

	public Boolean verifyProgressBar4() {
		Boolean progressBar4 = null;
		try {
			progressBar4 = progressBarStep4.isEnabled();
		} catch (Exception e) {
			log.info("Status of pay button is {}", progressBarStep4.isEnabled(), e);
		}
		return progressBar4;
	}

	public Boolean paymentSuccess() {
		Boolean paymentSuccessMessage = null;
		try {
			paymentSuccessMessage = paymentSuccessfulMsg.isDisplayed();
		} catch (Exception e) {
			log.info("Status of pay button is {}", paymentSuccessfulMsg.isDisplayed(), e);
		}
		return paymentSuccessMessage;
	}

	public Boolean paymentFailure() {
		Boolean paymentFailureMessage = null;
		try {
			paymentFailureMessage = paymentFailureMsg.isDisplayed();
		} catch (Exception e) {
			log.info("Status of pay button is {}", paymentFailureMsg.isDisplayed(), e);
		}
		return paymentFailureMessage;
	}

	public Boolean helplineNumberText() {
		Boolean helpline = null;
		try {
			helpline = helplineNumber.isDisplayed();
		} catch (Exception e) {
			log.info("Status of helpline number is {}", helplineNumber.isDisplayed(), e);
		}
		return helpline;
	}

	public Boolean imageDisplayedInConfirmationPage() {
		Boolean imageDisplay = null;
		try {
			imageDisplay = imageDisplayed.isDisplayed();
		} catch (Exception e) {
			log.info("Status of display image is {}", imageDisplayed.isDisplayed(), e);
		}
		return imageDisplay;
	}

	public void clickMyPolicy() {
		js.clickElementByJS(myPolicy);
	}

	public Boolean customerPortfolio() {
		Boolean customerPortfolioMessage = null;
		try {
			customerPortfolioMessage = customerPortfolioScreen.isEnabled();
		} catch (Exception e) {
			log.info("Status of pay button is {}", customerPortfolioScreen.isEnabled(), e);
		}
		return customerPortfolioMessage;
	}

	public Boolean verifyTravellingWithEditable() {
		Boolean travelWith = null;
		try {
			travelWith = travellingWith.isEnabled();
		} catch (Exception e) {
			log.info("Status of pay button is {}", travellingWith.isEnabled(), e);
		}
		return travelWith;
	}

	public Boolean verifyPayWithText() {
		Boolean payTextDisplayed = null;
		try {
			payTextDisplayed = payWithText.isDisplayed();
		} catch (Exception e) {
			log.info("Status of pay button is {}", payWithText.isDisplayed(), e);
		}
		return payTextDisplayed;
	}

	public Boolean verifyPremiumText() {
		Boolean premiumTextDisplayed = null;
		try {
			premiumTextDisplayed = premiumText.isDisplayed();
		} catch (Exception e) {
			log.info("Status of pay button is {}", premiumText.isDisplayed(), e);
		}
		return premiumTextDisplayed;
	}

	public Boolean verifyTermsText() {
		Boolean termsTextDisplayed = null;
		try {
			termsTextDisplayed = termsText.isDisplayed();
		} catch (Exception e) {
			log.info("Status of pay button is {}", termsText.isDisplayed(), e);
		}
		return termsTextDisplayed;
	}

	public Boolean verifyAmountPremiumText() {
		Boolean amountDisplayed = null;
		try {
			amountDisplayed = amountPremium.isDisplayed();
		} catch (Exception e) {
			log.info("Status of pay button is {}", amountPremium.isDisplayed(), e);
		}
		return amountDisplayed;
	}

	public Boolean verifyReviewBtnEnabled() {
		Boolean reviewBtnEnabled = null;
		try {
			reviewBtnEnabled = reviewBtn.isEnabled();
		} catch (Exception e) {
			log.info("Status of pay button is {}", reviewBtn.isEnabled(), e);
		}
		return reviewBtnEnabled;
	}

	public void selectEditStep1AndEditDetails() {
		wUtil.waitForElementToBeClickble(reviewBtn, 1000);
		js.clickElementByJS(reviewBtn);
		wUtil.waitForElementToBeClickble(travelsDetailsExpand, 1000);
		js.clickElementByJS(travelsDetailsExpand);
		wUtil.waitForElementToBeClickble(travelDetailsEdit, 1000);
		js.clickElementByJS(travelDetailsEdit);
	}

	public void selectEditStep1EditDetails() {
		wUtil.waitForElementToBeClickble(reviewBtn, 1000);
		js.clickElementByJS(reviewBtn);
		wUtil.waitForElementToBeClickble(travelsDetailsExpand, 1000);
		js.clickElementByJS(travelsDetailsExpand);
	}

	public void selectEditStep2AndEditDetails() {
		wUtil.waitForElementToBeClickble(reviewBtn, 1000);
		js.clickElementByJS(reviewBtn);
		wUtil.waitForElementToBeClickble(planDetailsExpand, 1000);
		js.clickElementByJS(planDetailsExpand);
		wUtil.waitForElementToBeClickble(planDetailsEdit, 1000);
		js.clickElementByJS(planDetailsEdit);
	}

	public void selectEditStep2EditDetails() {
		wUtil.waitForElementToBeClickble(reviewBtn, 1000);
		js.clickElementByJS(reviewBtn);
		wUtil.waitForElementToBeClickble(planDetailsExpand, 1000);
		js.clickElementByJS(planDetailsExpand);
	}

	public void selectEditStep3AndEditDetails() {
		wUtil.waitForElementToBeClickble(reviewBtn, 1000);
		js.clickElementByJS(reviewBtn);
		wUtil.waitForElementToBeClickble(travellersDetailsExpand, 1000);
		js.clickElementByJS(travellersDetailsExpand);
		wUtil.waitForElementToBeClickble(travellersDetailsEdit, 1000);
		js.clickElementByJS(travellersDetailsEdit);
	}

	public void selectEditStep3EditDetails() {
		wUtil.waitForElementToBeClickble(reviewBtn, 1000);
		js.clickElementByJS(reviewBtn);
		wUtil.waitForElementToBeClickble(travellersDetailsExpand, 1000);
		js.clickElementByJS(travellersDetailsExpand);
	}

	public String dateValidate() {
		return dateCheck.getText();
	}

	public String verifyPlanAfterEdit() {
		return planDetailsAfterEdit.getText();
	}

	public String verifyStep3EditDetails() {
		return travellersDetailsGenderEdit.getText();
	}

	public String verifyTravelWithDetails() {
		return travellingWith.getText();
	}

	public String verifyTravellingPlanDetails() {
		return travellingPlanDetails.getText();
	}

	public void quoteSummary() {
		wUtil.waitForElementToBeClickble(reviewBtn, 1000);
		js.clickElementByJS(reviewBtn);
		wUtil.waitForElementToBeClickble(travellersDetailsExpand, 3000);
		js.clickElementByJS(travellersDetailsExpand);
		wUtil.waitForElementToBeClickble(planDetailsExpand, 3000);
		js.clickElementByJS(planDetailsExpand);
		wUtil.waitForElementToBeClickble(travelsDetailsExpand, 3000);
		js.clickElementByJS(travelsDetailsExpand);
	}

	public void downloadProductSummary() {
		js.clickElementByJS(productSummary);
	}

	public void closeSummary() {
		js.clickElementByJS(closeReviewSummary);
	}
}
