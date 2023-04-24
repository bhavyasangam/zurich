package com.zurich.qa.digitalnative.pages;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import com.zurich.qa.digitalnative.utils.DatePickerUtil;
import com.zurich.qa.digitalnative.utils.ElementActionsUtil;
import com.zurich.qa.digitalnative.utils.JavaScriptUtil;
import com.zurich.qa.digitalnative.utils.WaitUtil;

public class APACPortfolioPage {

	public WebDriver driver;
	WaitUtil wUtil;
	private static Logger log = LogManager.getLogger(APACPortfolioPage.class);
	ElementActionsUtil elementActionsUtil;
	JavaScriptUtil js;
	DatePickerUtil datePickerUtil;
	
	@FindBy(id = "getAQuote")
	WebElement getAQuoteBtn;

	@FindBy(id = "btn-view-all")
	WebElement viewAllBtn;
	
	@FindBy(xpath = "//ul[@class='splide__list']/li")
	List<WebElement>  policiesList;
	
	@FindBy(xpath = "//div[@class='mb-4']//h4")
	WebElement policyHeader;
	
	@FindBy(id = "policy-id-0")
	WebElement policyNumber;
	
	@FindBy(id = "view-edit-0")
	WebElement viewPolicyDetails;
	
	@FindBy(id = "btn-view-policydocument")
	WebElement policyDocument;
	
	
	//--------------------------------not used
	@FindBy(id = "segmentedControl-portfolio-1")
	WebElement policiesTab;

	@FindBy(id = "segmentedControl-portfolio-2")
	WebElement quotesTab;

	@FindBy(id = "segmentedControl-portfolio-3")
	WebElement claimsTab;
	
	@FindBy(xpath = "//div[@class='my-3 text-center']")
	WebElement noActiveQuotes;
		
	//update id's
	@FindBy(id = "btn-view-all")
	WebElement viewByQuote;
	
	@FindBy(id = "btn-view-all")
	WebElement viewByDropdowns;
	
	@FindBy(id = "annualTrip")
	WebElement tripType;
	
	@FindBy(id = "executivePlan")
	WebElement planType;
	
	@FindBy(id = "Travel")
	WebElement product;
	
	@FindBy(id = "startdate")
	WebElement startDate;
	
	@FindBy(id = "expiryDate")
	WebElement expiryDate;
	
	@FindBy(id = "premiumAmt")
	WebElement premium;
	
	@FindBy(id = "Zheader-toggle")
	WebElement hamburgerMenuOpen;
	
	@FindBy(id = "header-toggle mr-2 ml-auto")
	WebElement hamburgerMenuClose;
	
	@FindBy(xpath = "//span[contains(text(),'Log Out')]")
	WebElement logoutButton;

	public APACPortfolioPage(WebDriver driver) {
		this.driver = driver;
		log.info("Page title {}", driver.getTitle());
		wUtil = new WaitUtil(driver);
		elementActionsUtil = new ElementActionsUtil(driver);
		js = new JavaScriptUtil(driver);
		datePickerUtil = new DatePickerUtil();
		PageFactory.initElements(driver, this);
	}
	
	public Boolean verifyAccountPolicies() {
		Boolean isPoliciesSize = null;
		try {
			wUtil.waitForElementToBeVisible(viewAllBtn);
			js.clickElementByJS(viewAllBtn);
			if (policiesList.size() <= 5) {
				isPoliciesSize = true;
			}
		} catch (Exception e) {
			log.info("Total policies of the account {} should be less than 5 in policy page", isPoliciesSize, e);
		}
		return isPoliciesSize;
	}
	
	public void verifyPolicyViewDetails() {
		try {
			wUtil.waitForElementToBeVisible(policyHeader);
			String actPolicyHeader = policyHeader.getText();
			Assert.assertEquals(actPolicyHeader, "Active policies");
			if (policiesList.size() > 1) {
				String policyNum = policyNumber.getText();
				Assert.assertTrue(policyNum.contains("PZTIID00000"), "Policy number is not displayed");

				Boolean actExpDate = driver.findElement(By.xpath("//*[@id='0']//label[contains(text(),'Expiration')]"))
						.isDisplayed();
				Assert.assertTrue(actExpDate, "Expiration Date in policy details is not displayed");
				Boolean actPlan = driver.findElement(By.xpath("//*[@id='0']//label[contains(text(),'Plan')]"))
						.isDisplayed();
				Assert.assertTrue(actExpDate, "Plan in policy details is not displayed");
			}
		} catch (Exception e) {
			log.info("Policies details not matching", e);
		}
	}
	
	public void verifyViewAllPolicies() {
		try {
			wUtil.waitForElementToBeVisible(viewPolicyDetails);
			String actPoliciesTxt = driver.findElement(By.xpath("//a[@id='view-edit-0']/a")).getText();
			Assert.assertEquals(actPoliciesTxt, "View/edit policy details","The link is not matching");
			if (policiesList.size() <= 5) {
				js.clickElementByJS(viewPolicyDetails);
				Assert.assertTrue(policyDocument.isDisplayed(), "Policy document is not displayed");
				js.clickElementByJS(policyDocument);
			}
		} catch (Exception e) {
			log.info("Total policies of the account {} should be less than 5 in policy page", e);
		}
	}

	public void verifypoliciesViewAll() {
		wUtil.waitForElementToBeVisible(viewAllBtn);
		js.clickElementByJS(viewAllBtn);
		policiesList.size();
	}
	
	public void logout() {
		try {
			menuOpen();
			clickLogout();
		} catch (Exception e) {
			log.info("Unable to logout", e);
		}
	}
		public void menuOpen() {
			wUtil.waitForElementToBeClickble(hamburgerMenuOpen);
			js.clickElementByJS(hamburgerMenuOpen);
		}
		
		public void menuClose() {
			wUtil.waitForElementToBeClickble(hamburgerMenuClose);
			js.clickElementByJS(hamburgerMenuClose);
		}
		
		public void clickLogout() {
			wUtil.waitForElementToBeClickble(logoutButton);
			js.clickElementByJS(logoutButton);
		}
		
		public void quoteSection() {
			wUtil.waitForElementToBeClickble(quotesTab);
			js.clickElementByJS(quotesTab);
		}
		
		public void viewByButton() {
			wUtil.waitForElementToBeClickble(viewByQuote);
			js.clickElementByJS(viewByQuote);
		}
		
		public Boolean verifyPolicyEnabled() {
			Boolean policiesButtonEnabled = null;
			try {
				policiesButtonEnabled = policiesTab.isEnabled();
			} catch (Exception e) {
				log.info("Status of policy button is {}", policiesTab.isEnabled(), e);
			}
			return policiesButtonEnabled;
		}
		
		public Boolean verifyQuotesEnabled() {
			Boolean quotesButtonEnabled = null;
			try {
				quotesButtonEnabled = quotesTab.isEnabled();
			} catch (Exception e) {
				log.info("Status of quotes button is {}", quotesTab.isEnabled(), e);
			}
			return quotesButtonEnabled;
		}
		
		public Boolean verifyClaimsEnabled() {
			Boolean claimsButtonEnabled = null;
			try {
				claimsButtonEnabled = claimsTab.isEnabled();
			} catch (Exception e) {
				log.info("Status of claims button is {}", claimsTab.isEnabled(), e);
			}
			return claimsButtonEnabled;
		}
		
		public Boolean verifyViewByEnabled() {
			Boolean viewByButtonEnabled = null;
			try {
				viewByButtonEnabled = viewByQuote.isEnabled();
			} catch (Exception e) {
				log.info("Status of viewby button is {}", viewByQuote.isEnabled(), e);
			}
			return viewByButtonEnabled;
		}
		
		public Boolean verifyViewByDropdownEnabled() {
			Boolean viewByDropdownEnabled = null;
			try {
				viewByDropdownEnabled = viewByDropdowns.isEnabled();
			} catch (Exception e) {
				log.info("Status of viewByDropdown button is {}", viewByDropdowns.isEnabled(), e);
			}
			return viewByDropdownEnabled;
		}
		
		public Boolean verifyNoActiveQuotesEnabled() {
			Boolean noActiveEnabled = null;
			try {
				noActiveEnabled = noActiveQuotes.isEnabled();
			} catch (Exception e) {
				log.info("Status of quote is {}", noActiveQuotes.isEnabled(), e);
			}
			return noActiveEnabled;
		}
		
		public Boolean verifyTripTypeDisplayed() {
			Boolean tripTypeDisplayed = null;
			try {
				tripTypeDisplayed = tripType.isDisplayed();
			} catch (Exception e) {
				log.info("Status of Triptype is {}", tripType.isDisplayed(), e);
			}
			return tripTypeDisplayed;
		}
		
		public Boolean verifyPlanTypeDisplayed() {
			Boolean planTypeDisplayed = null;
			try {
				planTypeDisplayed = planType.isDisplayed();
			} catch (Exception e) {
				log.info("Status of plantype is {}", planType.isDisplayed(), e);
			}
			return planTypeDisplayed;
		}
		
		public Boolean verifyProductDisplayed() {
			Boolean productDisplayed = null;
			try {
				productDisplayed = product.isDisplayed();
			} catch (Exception e) {
				log.info("Status of product is {}", product.isDisplayed(), e);
			}
			return productDisplayed;
		}
		
		public Boolean verifyStartDateDisplayed() {
			Boolean startDateDisplayed = null;
			try {
				startDateDisplayed = startDate.isDisplayed();
			} catch (Exception e) {
				log.info("Status of startdate is {}", startDate.isDisplayed(), e);
			}
			return startDateDisplayed;
		}
		
		public Boolean verifyExpiryDateDisplayed() {
			Boolean expiryDateDisplayed = null;
			try {
				expiryDateDisplayed = expiryDate.isDisplayed();
			} catch (Exception e) {
				log.info("Status of expiryDate is {}", expiryDate.isDisplayed(), e);
			}
			return expiryDateDisplayed;
		}
		
		public Boolean verifyPremiumDisplayed() {
			Boolean premiumDisplayed = null;
			try {
				premiumDisplayed = premium.isDisplayed();
			} catch (Exception e) {
				log.info("Status of premium is {}", premium.isDisplayed(), e);
			}
			return premiumDisplayed;
		}

}
