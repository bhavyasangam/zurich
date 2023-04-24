package com.zurich.qa.digitalnative.pages;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import com.zurich.qa.digitalnative.utils.ElementActionsUtil;
import com.zurich.qa.digitalnative.utils.JavaScriptUtil;
import com.zurich.qa.digitalnative.utils.WaitUtil;

public class APACQuotePolicyHolderPage {
	public WebDriver driver;
	WaitUtil wUtil;
	private static Logger log = LogManager.getLogger(APACQuotePolicyHolderPage.class);
	ElementActionsUtil elementActionsUtil;
	JavaScriptUtil js;

	@FindBy(id = "radio-areYouTraveling-0")
	WebElement checkBoxYes;

	@FindBy(id = "radio-areYouTraveling-1")
	WebElement checkBoxNo;

	@FindBy(id = "accordion-item-accordion-item-0-toggle")
	WebElement primaryTraveller;

	@FindBy(id = "accordion-item-accordion-item-0-toggle")
	WebElement policyholder;

	@FindBy(id = "accordion-item-accordion-item-1-toggle")
	WebElement primaryTravellerno;

	@FindBy(id = "person[0].firstName")
	WebElement firstName;

	@FindBy(id = "person[0].lastName")
	WebElement lastName;

	@FindBy(xpath = "//input[@id='datepicker-calendar']")
	WebElement dateOfBirth;

	@FindBy(name = "person[1].dateOfBirth")
	WebElement dateOfBirth2;

	@FindBy(xpath = "//div/select[@name='person[0].gender']/following-sibling::div//span[@class='placeholder']")
	WebElement gender;

	@FindBy(xpath = "//div[text()='Male']")
	WebElement male;

	@FindBy(xpath = "//div/select[@name='person[0].idCardtype']/following-sibling::div//span[@class='placeholder']")
	WebElement idCard;

	@FindBy(xpath = "//div[text()='Passport']")
	WebElement passport;

	@FindBy(xpath = "//input[@id='person[0].idCardNumber']")
	WebElement idNumber;

	@FindBy(name = "person[0].email")
	WebElement email;

	@FindBy(name = "person[0].confirmEmail")
	WebElement confirmEmail;

	@FindBy(xpath = "(//div[@class='ss-single-selected'])[3]")
	WebElement countryCode;

	@FindBy(xpath = "//div[text()='+91']")
	WebElement india;

	@FindBy(xpath = "//input[@id='person[0].mobile']")
	WebElement mobileNo;

	@FindBy(xpath = "//*[@id='accordion-item-0']/div/div[10]/div/div[2]/div/label")
	WebElement mobilenotext;

	@FindBy(xpath = "//button[@id='next']")
	WebElement next;

	@FindBy(id = "helper-message-person[0].firstName")
	WebElement firstNameerror;

	@FindBy(id = "helper-message-person[0].dateOfBirth")
	WebElement doberror;

	@FindBy(id = "helper-message-email")
	WebElement emailerror;

	@FindBy(id = "helper-message-ConfirmEmail")
	WebElement confirmemailerror;

	@FindBy(id = "helper-message-person[0].mobile")
	WebElement MobileNoerror;

	@FindBy(id = "helper-message-person[0].idCardNumber")
	WebElement idcardnumerror;

	@FindBy(xpath = "//*[@id='modal-content']")
	WebElement nodalErrorMsg;

	@FindBy(xpath = "//button[contains(@id,'accordion-item-accordion-item')]")
	List<WebElement> travellersPlus;
	
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

	public APACQuotePolicyHolderPage(WebDriver driver) {
		this.driver = driver;
		log.info("Page title {}", driver.getTitle());
		wUtil = new WaitUtil(driver);
		elementActionsUtil = new ElementActionsUtil(driver);
		js = new JavaScriptUtil(driver);
		PageFactory.initElements(driver, this);
	}
	
	public void  checbox_No() {
		js.clickElementByJS(checkBoxNo);
		Assert.assertTrue(policyholder.isDisplayed());
		Assert.assertTrue(primaryTravellerno.isDisplayed());
		try {
			// js.clickElementByJS(primaryTravellerno);
			js.clickElementByJS(policyholder);
		} catch (Exception e) {
			log.info("Some Error in code", e);
		}
	}
	
	public void firstname(String firstname) {
		js.clickElementByJS(checkBoxYes);
		js.clickElementByJS(primaryTraveller);
		firstName.sendKeys(firstname);
	}

	public void mandatoryfieldscheck() {
		Actions action = new Actions(driver);

		js.scrollIntoView(primaryTraveller);
		js.clickElementByJS(primaryTraveller);

		firstName.click();

		action.moveToElement(lastName).click(lastName).build().perform();
		wUtil.waitForElementToBeVisible(firstNameerror);
		Assert.assertTrue(firstNameerror.getText().equals("This field is required"));

		// date of birth
		wUtil.waitForElementToBeClickble(dateOfBirth);
		elementActionsUtil.doActionsClick(dateOfBirth);
		js.clickElementByJS(dateOfBirth);
		action.moveToElement(lastName).click(lastName).build().perform();

		wUtil.waitForElementToBeVisible(doberror);
		Assert.assertTrue(doberror.getText().equals("This field is required"));
		js.scrollIntoView(idCard);

		wUtil.waitForElementToBeClickble(email, 1000);
		elementActionsUtil.doActionsClick(email);
		action.moveToElement(confirmEmail).click(confirmEmail).build().perform();

		wUtil.waitForElementToBeVisible(emailerror);
		Assert.assertTrue(emailerror.getText().equals("This field is required"));
		js.clickElementByJS(confirmEmail);
		wUtil.waitForElementToBeClickble(email);
		js.clickElementByJS(lastName);

		wUtil.waitForElementToBeVisible(confirmemailerror);
		Assert.assertTrue(confirmemailerror.getText().equals("This field is required"));
		wUtil.waitForElementToBeClickble(mobileNo, 1000);
		elementActionsUtil.doActionsClick(mobileNo);
		js.clickElementByJS(lastName);
		wUtil.waitForElementToBeVisible(MobileNoerror);
		Assert.assertTrue(MobileNoerror.getText().equals("This field is required"));
		wUtil.waitForElementToBeClickble(idNumber, 1000);
		elementActionsUtil.doActionsClick(idNumber);
		action.moveToElement(lastName).click(lastName).build().perform();
		Assert.assertTrue(idcardnumerror.getText().equals("This field is required"));
	}

	public String mandatoryprimary_travller(String mandatoryfields[]) {
		String mandatoryFields = "";
		try {
			js.clickElementByJS(checkBoxYes);
			js.clickElementByJS(primaryTraveller);
			for (String field : mandatoryfields) {

				WebElement data = driver
						.findElement(By.xpath("//*[@id='accordion-item-0']//label[contains(text(),'" + field + "')]"));
				wUtil.waitForElementToBeVisible(data);
				String getdata = data.getText();
				if (getdata.contains("*")) {
					mandatoryFields += getdata + " ";
				}
			}
		} catch (Exception e) {
			log.info("madatory field validation failed",  e);
		}
		return mandatoryFields;
	}

	public String optionalprimary_traveller(String optionalfields[]) {
		String optionalFields = "";
		try {
			js.clickElementByJS(checkBoxYes);
			js.clickElementByJS(primaryTraveller);
			for (String field : optionalfields) {

				WebElement data = driver
						.findElement(By.xpath("//*[@id='accordion-item-0']//label[contains(text(),'" + field + "')]"));
				wUtil.waitForElementToBeVisible(data);
				String getdata = data.getText();
				if (getdata.contains("*") == false) {
					optionalFields += getdata + " ";
					System.out.println("Optional fields for primary travller are : " + getdata);
				}
			}
		} catch (Exception e) {
			log.info("Optional fields for primary travller  field validation failed",  e);
		}
		return optionalFields;
	}

	public String mandatory_travller2(String mandatoryfields[]) {
		String mandatoryFields = "";
		try {
			js.clickElementByJS(checkBoxYes);
			for (String field : mandatoryfields) {
				WebElement data = driver
						.findElement(By.xpath("//*[@id='accordion-item-1']//label[contains(text(),'" + field + "')]"));
				wUtil.waitForElementToBeVisible(data);
				String getdata = data.getText();
				if (getdata.contains("*")) {
					mandatoryFields += getdata + " ";
				}
			}
		} catch (Exception e) {
			log.info("Mandatory fields for  travller2  validation failed",  e);
		}
		return mandatoryFields;
	}

	public String optional_travller2(String optionalfields[]) {
		String optionalFields = "";
		try {
			js.clickElementByJS(checkBoxYes);
			for (String field : optionalfields) {
				WebElement data = driver
						.findElement(By.xpath("//*[@id='accordion-item-1']//label[contains(text(),'" + field + "')]"));
				wUtil.waitForElementToBeVisible(data);
				String getdata = data.getText();
				if (getdata.contains("*") == false) {
					optionalFields += getdata + " ";
				}
			}
		} catch (Exception e) {
			log.info("Optional fields for  travller2  validation failed",  e);
		}
		return optionalFields;
	}

	public Boolean verifyIamTravellingSelectedAsYes() {
		Boolean iamTravelling = null;
		try {
			iamTravelling = checkBoxYes.isSelected();
		} catch (Exception e) {
			log.info("Status of next buton is {}", checkBoxYes.isSelected(), e);
		}
		return iamTravelling;
	}

	public Boolean verifyIamTravellingSelectedAsNo() {
		Boolean iamTravelling = null;
		try {
			iamTravelling = checkBoxNo.isSelected();

		} catch (Exception e) {
			log.info("Status of next buton is {}", checkBoxNo.isSelected(), e);
		}
		return iamTravelling;
	}

	public void clickPrimaryTraveller() {
		wUtil.waitForElementToBeVisible(primaryTraveller, 2000);
		//js.scrollIntoView(primaryTraveller);
		//wUtil.waitForElementToBeVisible(primaryTraveller, 2000);
		//wUtil.waitForElementToBeClickble(primaryTraveller, 2000);
		js.clickElementByJS(primaryTraveller);

	}

	public void enterFirstName(String first) {
		wUtil.waitForElementToBeClickble(firstName, 2000);
		elementActionsUtil.doActionsClick(firstName);
		elementActionsUtil.doSendKeys(firstName, first);
	}

	public void enterLastName(String last) {
		wUtil.waitForElementToBeClickble(lastName, 2000);
		elementActionsUtil.doActionsClick(lastName);
		elementActionsUtil.doSendKeys(lastName, last);
	}

	public void enterDateOfBirth(String dob) {
		wUtil.waitForElementToBeClickble(dateOfBirth);
		elementActionsUtil.doActionsClick(dateOfBirth);
		elementActionsUtil.doSendKeys(dateOfBirth, dob);
		dateOfBirth.sendKeys(Keys.ENTER);
	}

	public void enterDateOfBirth2(String dob) {
		wUtil.waitForElementToBeClickble(dateOfBirth2);
		elementActionsUtil.doActionsClick(dateOfBirth2);
		elementActionsUtil.doSendKeys(dateOfBirth2, dob);
		dateOfBirth2.sendKeys(Keys.ENTER);
	}

	public void selectGender(String genderType) throws InterruptedException {
		wUtil.waitForElementToBeClickble(gender, 2000);
		js.clickElementByJS(gender);
		try {
			elementActionsUtil.perfElementClick(genderType);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void selectIdCard(String idCardType) throws InterruptedException {
		wUtil.waitForElementToBeClickble(idCard, 1000);
		js.clickElementByJS(idCard);
		try {
			elementActionsUtil.perfElementClick(idCardType);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void enterIdNumber(String id) {
		wUtil.waitForElementToBeClickble(idNumber, 1000);
		elementActionsUtil.doActionsClick(idNumber);
		elementActionsUtil.doSendKeys(idNumber, id);
	}

	public void enterEmail(String emailAdd) {
		wUtil.waitForElementToBeClickble(email, 1000);
		elementActionsUtil.doActionsClick(email);
		elementActionsUtil.doSendKeys(email, emailAdd);
	}

	public void enterConfirmEmail(String confEmail) {
		wUtil.waitForElementToBeClickble(confirmEmail, 1000);
		elementActionsUtil.doActionsClick(confirmEmail);
		elementActionsUtil.doSendKeys(confirmEmail, confEmail);
	}

	public void selectCountryCode(String codeType) throws InterruptedException {
		wUtil.waitForElementToBeClickble(countryCode, 1000);
		js.clickElementByJS(countryCode);
		try {
			elementActionsUtil.perfElementClick(codeType);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void enterMobileNo(String mob) {
		wUtil.waitForElementToBeClickble(mobileNo, 1000);
		elementActionsUtil.doActionsClick(mobileNo);
		elementActionsUtil.doSendKeys(mobileNo, mob);
	}

	public void clickNext() {
		wUtil.waitForElementToBeClickble(next, 1000);
		js.clickElementByJS(next);
	}
	

	public APACPayPage clickNextBtn() {
		wUtil.waitForElementToBeClickble(next, 1000);
		js.clickElementByJS(next);
		return new APACPayPage(driver);
	}

	public void errorMsgValidation(String Errormsg) {
		Assert.assertEquals(Errormsg, nodalErrorMsg.getText());
	}

	public void iamTravellingAsYes(String first, String last, String dob, String id, String emailAdd,
			String confEmail, String mob, String genderType, String idCardType, String codeType) throws Exception {
		try {
			clickPrimaryTraveller();
			enterFirstName(first);
			enterLastName(last);
			enterDateOfBirth(dob);
			selectGender(genderType);
			selectIdCard(idCardType);
			enterIdNumber(id);
			enterEmail(emailAdd);
			enterConfirmEmail(confEmail);
			selectCountryCode(codeType);
			enterMobileNo(mob);
			clickPrimaryTraveller();
		} catch (Exception e) {
			log.info("Quote entry page have some issue", e);
		}
		//return new APACPayPage(driver);
	}

	public void autoFill(int i, String FN, String LN, String dob, String genderType) throws InterruptedException {

		wUtil.waitForElementToBeClickble(driver.findElement(By.id("accordion-item-accordion-item-" + i + "-toggle")),
				1000);
		js.clickElementByJS(driver.findElement(By.id("accordion-item-accordion-item-" + i + "-toggle")));
		wUtil.waitForElementToBeClickble(driver.findElement(By.id("person[" + i + "].firstName")), 2000);
		elementActionsUtil.doActionsClick(driver.findElement(By.id("person[" + i + "].firstName")));
		elementActionsUtil.doSendKeys(driver.findElement(By.id("person[" + i + "].firstName")), FN);
		wUtil.waitForElementToBeClickble(driver.findElement(By.id("person[" + i + "].lastName")), 2000);
		elementActionsUtil.doActionsClick(driver.findElement(By.id("person[" + i + "].lastName")));
		elementActionsUtil.doSendKeys(driver.findElement(By.id("person[" + i + "].lastName")), LN);
		wUtil.waitForElementToBeClickble(driver.findElement(By.name("person[" + i + "].dateOfBirth")));
		elementActionsUtil.doActionsClick(driver.findElement(By.name("person[" + i + "].dateOfBirth")));

		elementActionsUtil.doSendKeys(driver.findElement(By.name("person[" + i + "].dateOfBirth")), dob);
		driver.findElement(By.name("person[" + i + "].dateOfBirth")).sendKeys(Keys.ENTER);
		wUtil.waitForElementToBeClickble(driver.findElement(By.xpath(
				"//div/select[@name='person[" + i + "].gender']/following-sibling::div//span[@class='placeholder']")),
				1000);
		js.clickElementByJS(driver.findElement(By.xpath(
				"//div/select[@name='person[" + i + "].gender']/following-sibling::div//span[@class='placeholder']")));
		if (genderType.equals("Female")) {
			driver.findElement(By.xpath("//*[@id=\"person[" + i + "].gender-div\"]/div[1]/div[2]/div[2]/div[3]"))
					.click();
		} else if (genderType.equals("Male")) {
			driver.findElement(By.xpath("//*[@id=\"person[" + i + "].gender-div\"]/div[1]/div[2]/div[2]/div[2]"))
					.click();
		}
		wUtil.waitForElementToBeClickble(driver.findElement(By.id("accordion-item-accordion-item-" + i + "-toggle")),
				1000);
		js.clickElementByJS(driver.findElement(By.id("accordion-item-accordion-item-" + i + "-toggle")));
	}

	public int noTravellers() {
		return travellersPlus.size();
	}

	public String InvalidMobile(String invmobileNo) {
		Actions action = new Actions(driver);
		js.scrollIntoView(countryCode);
		mobileNo.sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.DELETE));
		enterMobileNo(invmobileNo);
		action.moveToElement(lastName).click(lastName).build().perform();
		wUtil.waitForElementToBeVisible(MobileNoerror);
		return MobileNoerror.getText();
	}

	public String InvalidEmail(String emailId) {
		js.scrollIntoView(countryCode);
		email.sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.DELETE));
		enterEmail(emailId);
		Actions action = new Actions(driver);
		action.moveToElement(lastName).click(lastName).build().perform();
		wUtil.waitForElementToBeVisible(emailerror);
		return emailerror.getText();
	}

	public String confirmemailerror() {
		js.scrollIntoView(countryCode);
		enterEmail("ag@gmail.com");
		enterConfirmEmail("a@gmail.com");
		Actions action = new Actions(driver);
		action.moveToElement(lastName).click(lastName).build().perform();
		wUtil.waitForElementToBeVisible(confirmemailerror);
		return confirmemailerror.getText();
	}

	public String invalidID(String idCardType, String idNo) throws InterruptedException {
		Actions action = new Actions(driver);
		selectIdCard(idCardType);
		idNumber.sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.DELETE));
		enterIdNumber(idNo);
		action.moveToElement(lastName).click(lastName).build().perform();
		wUtil.waitForElementToBeVisible(idcardnumerror);
		return idcardnumerror.getText();
	}

	public APACPayPage iamTravellingAsNo(String first, String last, String dob, String id, String emailAdd,
			String confEmail, String mob, String genderType, String idCardType, String codeType) throws Exception {
		try {
			checbox_No();
			enterFirstName(first);
			enterLastName(last);
			enterDateOfBirth(dob);
			selectGender(genderType);
			selectIdCard(idCardType);
			enterIdNumber(id);
			enterEmail(emailAdd);
			enterConfirmEmail(confEmail);
			selectCountryCode(codeType);
			enterMobileNo(mob);
			clickPrimaryTraveller();
		} catch (Exception e) {
			log.info("Quote entry page have some issue", e);
		}
		return new APACPayPage(driver);
	}
	
	//********* Footer****************************
	@SuppressWarnings("deprecation")
    public void verifyElementinPolicyHolderPage(WebElement element) {
        wUtil.waitForElementToBeVisible(element);
        boolean isElementPresent = element.isDisplayed();
        Assert.assertTrue(isElementPresent);
        log.info("Element "+element+"is present in page");
    }
	
    public void verifyFooterElementsPolicyHolderPage() {
        verifyElementinPolicyHolderPage(facebookIcon);
        verifyElementinPolicyHolderPage(twitterIcon);
        verifyElementinPolicyHolderPage(instagramIcon);
        verifyElementinPolicyHolderPage(youtubeIcon);
        verifyElementinPolicyHolderPage(legal);
        verifyElementinPolicyHolderPage(privacyPolicy);
        verifyElementinPolicyHolderPage(termsOfUse);
        verifyElementinPolicyHolderPage(contactUs);
    }
    
    public void verifyDisclaimerElementPolicyHolderPage() {
        verifyElementinPolicyHolderPage(footerDisclaimer);
    }
    
    public void verifyZurichLogoPolicyHolderPage() {
        verifyElementinPolicyHolderPage(zurichLogo);
    }
    
	public APACPayPage enterpassengerDetailsAndVerifyFooterDisclaimer(String first, String last, String dob,  String id, String emailAdd, String confEmail,  String mob) {
        try {
            verifyFooterElementsPolicyHolderPage();
            verifyDisclaimerElementPolicyHolderPage();
            verifyZurichLogoPolicyHolderPage();
            clickPrimaryTraveller();
            enterFirstName(first);
            enterLastName(last);
            enterDateOfBirth(dob);
            selectGender("Male");
            selectIdCard("Passport");
            enterIdNumber(id);
            enterEmail(emailAdd);
            enterConfirmEmail(confEmail);
            selectCountryCode("+91");
            enterMobileNo(mob);
            clickNext();
        } catch (Exception e) {
            log.info("Quote entry page have some issue", e);
        }
        return new APACPayPage(driver);
    }
}
