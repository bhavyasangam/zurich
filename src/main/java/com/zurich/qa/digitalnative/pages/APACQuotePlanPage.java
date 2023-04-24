package com.zurich.qa.digitalnative.pages;

import java.util.Arrays;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.Color;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import com.zurich.qa.digitalnative.utils.Constants;

import com.zurich.qa.digitalnative.utils.ElementActionsUtil;
import com.zurich.qa.digitalnative.utils.JavaScriptUtil;
import com.zurich.qa.digitalnative.utils.WaitUtil;

public class APACQuotePlanPage {

	public WebDriver driver;
	WaitUtil wUtil;
	private static Logger log = LogManager.getLogger(APACQuotePlanPage.class);
	ElementActionsUtil elementActionsUtil;
	JavaScriptUtil js;
	
	@FindBy(xpath = "//*[@id='splide01-list']//li//h1")
	List<WebElement> getPlanList;
	
	@FindBy(xpath = "//div[@class='checkbox-label']//span[contains(@id,'title')]")
	List<WebElement> getAddOnList;

	@FindBy(id ="Zcheckbox-WINSPO-label")
	WebElement checkAddOns;
	
	@FindBy(xpath = "//button[@class='accordion-button d-flex']//span[starts-with(@id,'accordion-item-item')]")
	List<WebElement> getCoveragesList;
	
	@FindBy(id = "navFooter-total-amount")
	WebElement chosePlanPremium;
	
	@FindBy(id = "planCardCoverage-btn-dismiss")
	WebElement closeCoverageBtn;

	@FindBy(id = "planCardCoverage-x-dismiss")
	WebElement closeXCoverageBtn;

	@FindBy(xpath = "//button[@id='Save']")
	WebElement selectSaveButton;

	@FindBy(id = "nextButtonClick")
	WebElement selectNextButton;

	@FindBy(id = "backButton")
	WebElement selectBackButton;
	
	@FindBy(id = "download-plan-summary")
	WebElement insurancePlanSummary;
	
	@FindBy(id = "ZprogressBar-step-2-icon")
	WebElement quotePlanProgressBar;
	
	@FindBy(id = "title-plan-addon")
	WebElement  addOnTitle;

	@FindBy(id="addon_0_shortdesc")
	WebElement shortDesc;
	
	@FindBy(id="Zbutton-0")
	WebElement addOnReadMore;
	
	@FindBy(id="modal-title")
	WebElement addOnModeltitle;
	
	@FindBy(id="plan-addon-modal-primaryButton")
	WebElement readMoreClose;
	
	@FindBy(id="premium-label")
	WebElement totPremTitleLbl;
	
	@FindBy(id="premium-main-amount")
	WebElement totPremAmt;
	
	@FindBy(id="navFooter-total-amount")
	WebElement floatingFooterPremAmt;
	
	@FindBy(xpath="(//div[contains(@id,\"card-\")]//button[contains(@id,'btn-select')])[1]")
	WebElement firstPlanSelectBtn;
	
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
	
	@FindBy(id = "ZprogressBar-step-2-icon")
	WebElement progressBarStep2;
	
	public APACQuotePlanPage(WebDriver driver) {
		this.driver = driver;
		log.info("Page title {}", driver.getTitle());
		wUtil = new WaitUtil(driver);
		elementActionsUtil = new ElementActionsUtil(driver);
		js = new JavaScriptUtil(driver);
		PageFactory.initElements(driver, this);
	}
	
	//*************************Plan Selection******************************/
	public String selectPlans(String planSelected)
    {
		String plan;
		 switch(planSelected){  
		    case "Executive": plan =Constants.Executive;  
		    break;  
		    case "Premium": plan =Constants.Premium;  
		    break;  
		    case "Basic": plan =Constants.Basic;  
		    break;  
		    case "Silver": plan =Constants.Silver;  
		    break;  
		    case "Gold": plan =Constants.Gold;  
		    break;
		    default:plan =Constants.Executive;  
		    }  
     return plan;
    }
	
	public boolean validatePlanCards(String planNamesList) {
		boolean isEqual = false;
		boolean isEqualSize = false;
		List<String> planNames = Arrays.asList(planNamesList.split(","));
		for (int i = 0; i < getPlanList.size(); i++) {
			isEqualSize = (getPlanList.size() == (planNames.size())) ? true : false;
			if (isEqualSize) {
				isEqual = (getPlanList.get(i).getText()).equals(planNames.get(i)) ? true : false;
				if(!isEqual) {
					log.info("Actual and expected plan cards  are not matching", getPlanList.get(i).getText());
				break;}
			} else {
				log.info("Actual and expected plan cards size are not matching", getPlanList.size(),
						planNames.size());
			}
		}
		return isEqual;
	}
	
	public String planPremium(String planSelected) throws InterruptedException {
		selectPlans(planSelected);
		WebElement  planPremium = null;
		String planChoosenPremium = null;
		try {
			planPremium=driver.findElement(By.id("discounted-"+selectPlans(planSelected)+""));
			planChoosenPremium=planPremium.getText();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return  planChoosenPremium;
	}
	
	public void selectPlan(String planSelected) throws InterruptedException {
		selectPlans(planSelected);
		WebElement  clickPlan= null;
		try {
			clickPlan=driver.findElement(By.id("btn-select-"+selectPlans(planSelected)+""));
			js.clickElementByJS(clickPlan);
			Assert.assertEquals(clickPlan.getText(),"Selected plan","Plan is not getting selected");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void validateBenefitsCountries(String planSelected, String benefitCountries) throws InterruptedException {
		selectPlans(planSelected);
		WebElement  getBenefitsCountry= null;
		try {
			getBenefitsCountry=driver.findElement(By.xpath("//table[@id='benefits-"+selectPlans(planSelected)+"']//tr[4]//div[@class='mb-3']"));
			getBenefitsCountry.getText();
		} catch (Exception e) {
			e.printStackTrace();
		}
		Assert.assertEquals(benefitCountries,getBenefitsCountry.getText());
	}
	
	//*************************AddOns Selection******************************/
	public boolean validateAddons(String addOnsList) {
		boolean isEqual = false;
		boolean isEqualSize = false;
		List<String> addOns = Arrays.asList(addOnsList.split(","));
		for (int i = 0; i < getAddOnList.size(); i++) {
			isEqualSize = (getAddOnList.size() == (addOns.size())) ? true : false;
			if (isEqualSize) {
				isEqual = (getAddOnList.get(i).getText()).contains(addOns.get(i)) ? true : false;
				if(!isEqual) {
					log.info("Actual and expected addOns  are not matching", getAddOnList.get(i).getText());
				break;}
			} else {
				log.info("Actual and expected addOns size are not matching", getAddOnList.size(),
						addOns.size());
			}
		}
		return isEqual;
	}
	
	public void clickAddOns(String selectAddOnsList) throws InterruptedException {   
		WebElement  clickAddOns;
		List<String> selectAddOns = Arrays.asList(selectAddOnsList.split(","));
		for (int i = 0; i < selectAddOns.size(); i++) {
			clickAddOns=driver.findElement(By.xpath("//span[text()='"+selectAddOns.get(i)+"']/ancestor::label"));
			wUtil.waitForElementToBeClickble(clickAddOns, 1000);
			js.clickElementByJS(clickAddOns);
		}
	}

	public String addOnShortDescription() {
		String actAddOnShortDesc = null;
		try {
			actAddOnShortDesc = shortDesc.getText();
		} catch (Exception e) {
			log.info("Actual Addon short description is {}: ",  shortDesc.getText());
		}
		return actAddOnShortDesc;
	}
	
	public void validateReadMore(String expReadmoreDesc) {
		String actReadmoreDesc = null;
		try {
			wUtil.waitForElementToBeClickble(addOnReadMore, 1000);
			js.clickElementByJS(addOnReadMore);
			wUtil.waitForElementToBeVisible(driver.switchTo().activeElement(), 2000);
			actReadmoreDesc= addOnModeltitle.getText();
			Assert.assertEquals(actReadmoreDesc,expReadmoreDesc,"Quote Plan add ons read more are not matching");
			wUtil.waitForElementToBeClickble(readMoreClose, 1000);
			js.clickElementByJS(readMoreClose);
			
		} catch (Exception e) {
			log.info("Actual Addon short description is {}: ",  shortDesc.getText());
		}
	}
	
	//*************************Coverages Selection******************************/
	public boolean validateCovergaes(String  planSelected, String coverages) {
		selectPlans(planSelected);
		boolean isEqual = false;
		boolean isEqualSize = false;
		WebElement  clickSeeAllCoverages=driver.findElement(By.id("btn-allcoverage-"+selectPlans(planSelected)+""));
		try {
			js.clickElementByJS(clickSeeAllCoverages);
			List<String> expCoverages = Arrays.asList(coverages.split(","));
		for (int i = 0; i < getCoveragesList.size(); i++) {
			isEqualSize = (getCoveragesList.size() == (expCoverages.size())) ? true : false;
			if (isEqualSize) {
				isEqual = (getCoveragesList.get(i).getText()).equals(expCoverages.get(i)) ? true : false;
				if(!isEqual) {
					log.info("Actual and expected coverages  are not matching", getCoveragesList.get(i).getText());
				break;}
			} else {
				log.info("Actual and expected coverages size are not matching", getCoveragesList.size(),
						expCoverages.size());
			}
		}} catch (Exception e) {
			e.printStackTrace();
		}
		js.clickElementByJS(closeXCoverageBtn);
		return isEqual;
	}
	
	//********************Premium validation on Screen 2 **************************/
	public String verifyChoosePlanTotalPremium() {
		String actPlanTotalPremium = null;
		try {
			actPlanTotalPremium = chosePlanPremium.getText();;
		} catch (Exception e) {
			log.info("Choosen total plan premium along with addons is:", chosePlanPremium.getText(), e);
		}
		return actPlanTotalPremium;
	}
	
	public String validateTheTotPremSection(String planPremium, String totalPremium) {
		String actPlanTotalPremium = null;
		try {
			actPlanTotalPremium = totPremTitleLbl.getText();
			Assert.assertEquals(actPlanTotalPremium,"My total premium","Quote Plan total Premium text are not matching");
			
			Assert.assertEquals(totPremAmt.getText(),planPremium,"Quote Plan total Premium text are not matching");
			Assert.assertEquals(floatingFooterPremAmt.getText(),totalPremium,"Quote Plan total floating premium text are not matching");
		} catch (Exception e) {
			log.info("Choosen total plan premium along with addons is:", chosePlanPremium.getText(), e);
		}
		return actPlanTotalPremium;
	}
	
	public void validateQuotePlanPage(String planCardsList, String planSelect,String benefitCountry, String ChoosePlanPremium, String addOnsList, String  selectAddOn, String coverages, String expPlanTotalPremium) throws InterruptedException {
		Assert.assertFalse(verifyNextBtnEnabled(),"Next button is not enabled");
		Assert.assertTrue(validatePlanCards(planCardsList),"Plan cards are not matching");
		validateBenefitsCountries(planSelect, benefitCountry);
		selectPlan(planSelect);
		Assert.assertEquals(ChoosePlanPremium,planPremium(planSelect),"Plan Premium not matching");
		Assert.assertTrue(validateAddons(addOnsList),"Addons are not matching");
		clickAddOns(selectAddOn);
		validateCovergaes(planSelect,coverages);
		Assert.assertEquals(expPlanTotalPremium,verifyChoosePlanTotalPremium(),"Total premium is not matching");
	}

	public APACQuotePolicyHolderPage clickNextButton() throws InterruptedException {
		wUtil.waitForElementToBeClickble(selectNextButton);
		js.clickElementByJS(selectNextButton);
		return new APACQuotePolicyHolderPage(driver);
	}

	////////**********************************************************************
	public void clickSaveButton() throws InterruptedException {
		wUtil.waitForElementToBeClickble(selectSaveButton);
		js.clickElementByJS(selectSaveButton);
	}

	public void validadeAddOnTitle() {
		String actAddOnTitle=addOnTitle.getText();
		Assert.assertEquals(actAddOnTitle,"I want to boost my coverage with");
	}

	public void clickBackButton() {
		wUtil.waitForElementToBeClickble(selectBackButton);
		js.clickElementByJS(selectBackButton);
	}

	public Boolean verifyNextBtnEnabled() {
		Boolean nextBtnEnabled = null;
		try {
			nextBtnEnabled = selectNextButton.isEnabled();
		} catch (Exception e) {
			log.info("Status of next buton is {}", selectNextButton.isEnabled(), e);
		}
		return nextBtnEnabled;
	}

	public Boolean verifySaveBtnEnabled() {
		Boolean saveBtnEnabled = null;
		try {
			saveBtnEnabled = selectSaveButton.isEnabled();
		} catch (Exception e) {
			log.info("Status of next buton is not enabled {}", selectSaveButton.isEnabled(), e);
		}
		return saveBtnEnabled;
	}
	
	public Boolean verifyInsurancePlanSummary() {
		Boolean isPlanSummaryDsplayed = null;
		try {
			isPlanSummaryDsplayed = insurancePlanSummary.isDisplayed();
		} catch (Exception e) {
			log.info("Insurance Plan Summary is not displayed {}", isPlanSummaryDsplayed, e);
		}
		return isPlanSummaryDsplayed;
	}
	
	public Boolean verifyQuotePlanProgressBar() {
		Boolean progressBar = null;
		try {
			progressBar = quotePlanProgressBar.isEnabled();
		} catch (Exception e) {
			log.info("Status of pay button is {}", quotePlanProgressBar.isEnabled(), e);
		}
		return progressBar;
	}
	
	//********Footer**********************************
	public  void clickselectPlanAndVerifyFoorterDisclaimer() throws InterruptedException {
		{
			verifyFooterElementsPlanPage();
			verifyDisclaimerElementPlanPage();
			verifyZurichLogoElementPlanPage();
			//js.clickElementByJS(firstPlanSelectBtn);
			//wUtil.waitForElementToBeClickble(selectNextButton);

			//elementActionsUtil.doActionsClick(selectNextButton);
		}
	}

	@SuppressWarnings("deprecation")
	public void verifyElementinPlanPage(WebElement element) {
		wUtil.waitForElementToBeVisible(element);
		boolean isElementPresent = element.isDisplayed();
		Assert.assertTrue(isElementPresent, "Element is unable to identified.");
		log.info("Element " + element + "is present in page");
	}

	public void verifyFooterElementsPlanPage() {
		verifyElementinPlanPage(facebookIcon);
		verifyElementinPlanPage(twitterIcon);
		verifyElementinPlanPage(instagramIcon);
		verifyElementinPlanPage(youtubeIcon);

		verifyElementinPlanPage(legal);
		verifyElementinPlanPage(privacyPolicy);
		verifyElementinPlanPage(termsOfUse);
		verifyElementinPlanPage(contactUs);
	}

	public void verifyDisclaimerElementPlanPage() {
		verifyElementinPlanPage(footerDisclaimer);

	}

	public void verifyZurichLogoElementPlanPage() {
		verifyElementinPlanPage(zurichLogo);
	}
	
	//**************** Progress bar**************
		public void verifyProgressQuoteBar() {
			String hex=null;
			try {
				String bar2=progressBarStep2.getCssValue("color");
				 hex = Color.fromString(bar2).asHex();
				 Assert.assertEquals(hex,"#a6adaf","Quote Icon progress bar is not matching");
			} catch (Exception e) {
				log.info("Quote Icon progress bar is not matching{}", hex, e);
			}
		}
}