package com.zurich.qa.digitalnative.pages;

import java.lang.reflect.InvocationTargetException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.Color;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import com.zurich.qa.digitalnative.utils.Constants;
import com.zurich.qa.digitalnative.utils.DatePickerUtil;
import com.zurich.qa.digitalnative.utils.ElementActionsUtil;
import com.zurich.qa.digitalnative.utils.JavaScriptUtil;
import com.zurich.qa.digitalnative.utils.WaitUtil;

public class APACQuotePage {

	public WebDriver driver;
	WaitUtil wUtil;
	private static Logger log = LogManager.getLogger(APACQuotePage.class);
	ElementActionsUtil elementActionsUtil;
	JavaScriptUtil js;
	DatePickerUtil datePickerUtil;

	@FindBy(xpath = "//div/select[@name='travelType']/following-sibling::div//span[@class='placeholder']")
	WebElement ddTravelType;

	@FindBy(xpath = "(//div[@class='ss-single-selected'])[1]")
	WebElement ddTravelTypeExpand;

	// div/select[contains(@name,'destination')]/following-sibling::div//span[@class='ss-disabled']
	@FindBy(xpath = "//div/select[@name='destinationCountry']/following-sibling::div//span[@class='ss-disabled']")
	WebElement ddTravelCountry;

	@FindBy(xpath = "//div[@id='destinationCountry-div']//input[@type='search']")
	WebElement ddSearchCountry;

	@FindBy(id = "numberOfTravelers")
	WebElement travellersNumber;

	@FindBy(name = "effectiveDate")
	WebElement departureDate;

	@FindBy(xpath = "//span[@class='datepicker-cell day today focused']")
	WebElement todayDate;

	@FindBy(xpath = "//div/select[@name='travelGroupType']/following-sibling::div//span[@class='placeholder']")
	WebElement ddTravelWith;

	@FindBy(name = "expiryDate")
	WebElement returnDate;

	@FindBy(xpath = "//span[contains(@class,'focused')]")
	WebElement datePickerSelect;

	// "//input[@id='promoCode']"
	@FindBy(id = "promoCode")
	WebElement promoCode;

	@FindBy(id = "chooseMyPlan")
	WebElement choosePlan;

	@FindBy(id = "helper-message-promoCode")
	WebElement expiredPromocode;

	@FindBy(id = "helper-message-promoCode")
	WebElement promocodeError;

	@FindBy(id = "helper-message-destinationCountry")
	WebElement errorCountries;

	@FindBy(id = "helper-message-numberOfTravelers")
	WebElement errorTravellers;

	@FindBy(id = "desc-over70")
	WebElement seniorMsg;

	@FindBy(id = "departureDateModal-primaryButton")
	WebElement disclaimer;

	@FindBy(xpath = "(//span[@class='placeholder'])[2]")
	WebElement ddTravelToAnnual;

	@FindBy(id = "Zheader-logo-img-0")
	WebElement zurichLogo;

	@FindBy(id = "header")
	WebElement headerElement;

	@FindBy(id = "social-link-0")
	WebElement facebookIcon;

	@FindBy(id = "social-link-1")
	WebElement twitterIcon;

	@FindBy(id = "social-link-2")
	WebElement instagramIcon;

	@FindBy(id = "social-link-3")
	WebElement youtubeIcon;

	@FindBy(id = "footer-link-0-a")
	WebElement legal;

	@FindBy(id = "footer-link-1-a")
	WebElement privacyPolicy;

	@FindBy(id = "footer-link-2-a")
	WebElement termsOfUse;

	@FindBy(id = "footer-link-3-a")
	WebElement contactUs;

	@FindBy(id = "footer-label-disclaimer")
	WebElement footerDisclaimer;

	@FindBy(id = "Zheader-toggle")
	WebElement sandwichIcon;

	@FindBy(id = "//div/select[@name='destinationType']/following-sibling::div//span[@class='ss-disabled']")
	WebElement destinationType;

	@FindBy(xpath = "//span[@class='icon icon--pencil_24_outline']")
	WebElement promoCodePencilOutline;

	@FindBy(id = "departureDateModal-primaryButton")
	WebElement agreeButton;

	@FindBy(id = "dina-header-image")
	WebElement dinaHeaderImage;

	@FindBy(id = "label-insuranceType")
	WebElement travelTypeLabel;

	@FindBy(id = "label-travellingWith")
	WebElement travelWithLabel;

	@FindBy(xpath = "//input[@name='effectiveDate']//following-sibling::label[contains(text(),'departing on *')]")
	WebElement departureOnLabel;

	@FindBy(xpath = "//input[@name='expiryDate']//following-sibling::label[contains(text(),'return on *')]")
	WebElement returnOnLabel;

	@FindBy(xpath = "//input[@id='promoCode']//following-sibling::label")
	WebElement promoCodeLabel;

	@FindBy(id = "title-over70")
	WebElement seniorCitizenTitle;

	@FindBy(id = "desc-over70")
	WebElement seniorCitizenDesc;

	@FindBy(id = "helper-message-destinationCountry")
	WebElement countriesLimitErrorMsg;
	
	@FindBy(id = "ZprogressBar-step-1-icon")
	WebElement progressBarStep1;

	public Map<String, WebElement> FOOTER_WEBELEMENT = new HashMap<>();
	{
		FOOTER_WEBELEMENT.put("Legal", legal);
		FOOTER_WEBELEMENT.put("Privacy Policy", privacyPolicy);
		FOOTER_WEBELEMENT.put("Terms Of Use", termsOfUse);
		FOOTER_WEBELEMENT.put("Contact Us", contactUs);
	}

	public APACQuotePage(WebDriver driver) {
		this.driver = driver;
		log.info("Page title {}", driver.getTitle());
		wUtil = new WaitUtil(driver);
		elementActionsUtil = new ElementActionsUtil(driver);
		js = new JavaScriptUtil(driver);
		datePickerUtil = new DatePickerUtil();
		PageFactory.initElements(driver, this);
	}

	// *************************Quote Page Details**************************

	public void selectTravelType(String tripType) throws InterruptedException {
		wUtil.waitForElementToBeClickble(ddTravelType, 1000);
		js.clickElementByJS(ddTravelType);
		try {
			elementActionsUtil.perfElementClick(tripType);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void selectTravelCountry(String tripType, String countryOrTravelType) throws InterruptedException {
		if ((tripType.trim()).equals((Constants.SINGLE_TRIP).trim())
				|| (tripType.trim()).equals((Constants.ONEWAY_TRIP).trim())) {
			if ((ddSearchCountry.isDisplayed()) == true) {
				ddSearchCountry.clear();
				ddSearchCountry.sendKeys(countryOrTravelType);
				try {
					elementActionsUtil.perfElementClick(countryOrTravelType);
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else {
				js.clickElementByJS(ddTravelCountry);
				ddSearchCountry.sendKeys(countryOrTravelType);

				try {
					elementActionsUtil.perfElementClick(countryOrTravelType);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} else if ((tripType.trim()).equals((Constants.ANNUAL_TRIP).trim())) {
			/*
			 * wUtil.waitForElementToBeClickble(ddTravelToAnnual, 1000);
			 * js.clickElementByJS(ddTravelToAnnual);
			 */
			wUtil.waitForElementToBeClickble(ddTravelToAnnual, 1000);
			js.clickElementByJS(ddTravelToAnnual);
			try {
				elementActionsUtil.perfElementClick(countryOrTravelType);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void selectTravelWith(String travelWith) throws InterruptedException {
		wUtil.waitForElementToBeClickble(ddTravelWith, 2000);
		js.clickElementByJS(ddTravelWith);
		try {
			elementActionsUtil.perfElementClick(travelWith);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void enterTravellersNumber(String travellers) throws InterruptedException {
		wUtil.waitForElementToBeClickble(travellersNumber);
		elementActionsUtil.doActionsSendKeys(travellersNumber, travellers);
	}

	public void provideTravellingPartnerDetails(String travelWith, String noOfTravellers) throws InterruptedException {
		selectTravelWith(travelWith);
		if ((travelWith.trim()).equals((Constants.MY_FAMILY).trim())
				|| (travelWith.trim()).equals((Constants.GROUP).trim())) {
			enterTravellersNumber(noOfTravellers);
		}
	}

	public void enterTravelDates(String tripType, String departureDate, String returnDate) throws InterruptedException {
		if ((tripType.trim()).equals((Constants.ONEWAY_TRIP).trim())
				|| (tripType.trim()).equals((Constants.ANNUAL_TRIP).trim())) {
			selectDepartingOn(departureDate);
		} else if ((tripType.trim()).equals((Constants.SINGLE_TRIP).trim())) {
			selectDepartingOn(departureDate);
			selectReturningDate(returnDate);
		}
	}

	public void selectDepartingOn(String selectDate) {
		SimpleDateFormat dtf = new SimpleDateFormat("dd/MM/yyyy");
		Date date1 = null;
		try {
			date1 = dtf.parse(selectDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		departureDate.sendKeys(dtf.format(date1));
		departureDate.sendKeys(Keys.ENTER);
		wUtil.waitForElementToBeClickble(departureDate, 1000);
		js.clickElementByJS(departureDate);
		js.clickElementByJS(datePickerSelect);
	}

	public void disclaimerWindow() throws InterruptedException {
		wUtil.waitForElementToBeVisible(driver.switchTo().activeElement(), 2000);
		wUtil.waitForElementToBeClickble(disclaimer, 1000);
		js.clickElementByJS(disclaimer);
	}

	public void selectReturningDate(String selectDate) {
		SimpleDateFormat dtf = new SimpleDateFormat("dd/MM/yyyy");
		Date date1 = null;
		try {
			date1 = dtf.parse(selectDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		returnDate.sendKeys(dtf.format(date1));
		returnDate.sendKeys(Keys.ENTER);
		wUtil.waitForElementToBeClickble(returnDate, 1000);
		js.clickElementByJS(returnDate);
		js.clickElementByJS(datePickerSelect);
	}

	public void enterTravelDatesByDuration(String tripType, String departureDateDuration, String returnDateDuration)
			throws InterruptedException, InvocationTargetException {
		String departureDate = getDateByDuration(departureDateDuration);
		String returnDate = getDateByDuration(returnDateDuration);
		if ((tripType.trim()).equals((Constants.ONEWAY_TRIP).trim())
				|| (tripType.trim()).equals((Constants.ANNUAL_TRIP).trim())) {
			selectDepartingOn(departureDate);
		} else if ((tripType.trim()).equals((Constants.SINGLE_TRIP).trim())) {
			selectDepartingOn(departureDate);
			selectReturningDate(returnDate);
		}
	}

	public String getDateByDuration(String duration) {
		Integer dDuration = Integer.parseInt(duration);
		int intDuration = dDuration;
		String date = null;
		try {
			if (intDuration == 0) {
				date = datePickerUtil.currentDate();
			} else if (intDuration < 0) {
				date = datePickerUtil.previousDate(intDuration);
			} else if (intDuration > 0) {
				date = datePickerUtil.futureDate(intDuration);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return date;
	}

	public APACQuotePlanPage clickQuotePlan() {
		wUtil.waitForElementToBeClickble(choosePlan);
		js.clickElementByJS(choosePlan);
		return new APACQuotePlanPage(driver);
	}

	public void quoteDetailsForSingleTrip(String tripType, String travelWith, String country,
			String noOfTravellers, String departureDate, String returnDate) throws InterruptedException {
		try {
			selectTravelType(tripType);
			selectTravelCountry(tripType, country);
			provideTravellingPartnerDetails(travelWith, noOfTravellers);
			enterTravelDates(tripType, departureDate, returnDate);
		} catch (Exception e) {
			log.info("Quote entry page have some issue", e);
		}
		//return new APACQuotePlanPage(driver);
	}

	public APACQuotePlanPage quoteDetailsForMultipleCountrySingleTrip(String tripType, String travelWith,
			String countries, String noOfTravellers, String departureDate, String returnDate)
			throws InterruptedException {
		try {
			selectTravelType(tripType);
			System.out.println("countries" + countries);
			List<String> countriesList = Arrays.asList(countries.split(","));
			for (int i = 0; i < countriesList.size(); i++) {
				selectTravelCountry(tripType, countriesList.get(i));
			}
			provideTravellingPartnerDetails(travelWith, noOfTravellers);
			enterTravelDates(tripType, departureDate, returnDate);
		} catch (Exception e) {
			log.info("Quote entry page have some issue", e);
		}
		return new APACQuotePlanPage(driver);
	}

	public void quoteDetailsForSingleTripByTravelDuration(String tripType, String country, String travelWith,
			String noOfTravellers, String departureDateDuration, String returnDateDuration)
			throws InterruptedException, InvocationTargetException {
		selectTravelType(tripType);
		selectTravelCountry(tripType, country);
		selectTravelCountry(tripType, country);
		provideTravellingPartnerDetails(travelWith, noOfTravellers);
		enterTravelDatesByDuration(tripType, departureDateDuration, returnDateDuration);
	}

	public APACQuotePlanPage quoteDetailsForOneWayTrip(String tripType, String country, String travelWith,
			String noOfTravellers, String departureDate, String returnDate) throws InterruptedException {
		try {
			selectTravelType(tripType);
			selectTravelCountry(tripType, country);
			selectTravelCountry(tripType, country);
			provideTravellingPartnerDetails(travelWith, noOfTravellers);
			enterTravelDates(tripType, departureDate, returnDate);
		} catch (Exception e) {
			log.info("Quote entry page have some issue", e);
		}
		return new APACQuotePlanPage(driver);
	}

	public void quoteDetailsForAnnual(String tripType, String travelWith, String country, String noOfTravellers,
			String departureDate, String returnDate) throws InterruptedException {
		selectTravelType(tripType);
		selectTravelCountry(tripType, country);
		provideTravellingPartnerDetails(travelWith, noOfTravellers);
		enterTravelDates(tripType, departureDate, returnDate);
	}

	public void quoteDetailsForSingleTripForMeAndDuoToday(String tripType, String country, String travelWith)
			throws InterruptedException, InvocationTargetException {
		selectTravelType(tripType);
		selectTravelCountry(tripType, country);
		provideTravellingPartnerDetails(travelWith, "0");
		enterTravelDatesByDuration(tripType, "0", "2");
	}

	public void quoteDetailsForSingleTripForFamilyAndGroupToday(String tripType, String country, String travelWith,
			String noOfTravellers) throws InterruptedException, InvocationTargetException {
		selectTravelType(tripType);
		selectTravelCountry(tripType, country);
		provideTravellingPartnerDetails(travelWith, noOfTravellers);
		enterTravelDatesByDuration(tripType, "0", "2");
	}

	public void clickOnRandomObjectInQuotePage() {
		js.clickElementByJS(promoCodePencilOutline);
	}

//==================
	public void quoteDetailsForSingleTripForMeAndDuoPastByDate(String tripType, String country, String travelWith,
			String departureDate, String arrivalDate) throws InterruptedException, InvocationTargetException {
		selectTravelType(tripType);
		selectTravelCountry(tripType, country);
		provideTravellingPartnerDetails(travelWith, "0");
		enterTravelDates(tripType, departureDate, arrivalDate);
		disclaimerWindow();
	}

	public void quoteDetailsForSingleTripForFamilyAndGroupPastDate(String tripType, String country, String travelWith,
			String noOfTravellers, String departureDate, String arrivalDate)
			throws InterruptedException, InvocationTargetException {
		selectTravelType(tripType);
		selectTravelCountry(tripType, country);
		provideTravellingPartnerDetails(travelWith, noOfTravellers);
		enterTravelDates(tripType, departureDate, arrivalDate);
		disclaimerWindow();
	}

	public void quoteDetailsForSingleTripForMeAndDuoFutureDate(String tripType, String country, String travelWith,
			String departureDate, String arrivalDate) throws InterruptedException, InvocationTargetException {
		selectTravelType(tripType);
		selectTravelCountry(tripType, country);
		provideTravellingPartnerDetails(travelWith, "0");
		enterTravelDates(tripType, departureDate, arrivalDate);
	}

	public void quoteDetailsForSingleTripForFamilyAndGroupFutureDate(String tripType, String country, String travelWith,
			String noOfTravellers, String departureDate, String arrivalDate)
			throws InterruptedException, InvocationTargetException {
		selectTravelType(tripType);
		selectTravelCountry(tripType, country);
		provideTravellingPartnerDetails(travelWith, noOfTravellers);
		enterTravelDates(tripType, departureDate, arrivalDate);
	}

//	============ONE Way Trip==============================

	public void quoteDetailsForOneWayTripForMeAndDuoToday(String tripType, String country, String travelWith)
			throws InterruptedException, InvocationTargetException {
		selectTravelType(tripType);
		selectTravelCountry(tripType, country);
		provideTravellingPartnerDetails(travelWith, "0");
		enterTravelDatesByDuration(tripType, "0", "2");
	}

	public void quoteDetailsForOneWayTripForFamilyAndGroupToday(String tripType, String country, String travelWith,
			String noOfTravellers) throws InterruptedException, InvocationTargetException {
		selectTravelType(tripType);
		selectTravelCountry(tripType, country);
		provideTravellingPartnerDetails(travelWith, noOfTravellers);
		enterTravelDatesByDuration(tripType, "0", "2");
	}

	public void quoteDetailsForOneWayTripForMeAndDuoPastByDate(String tripType, String country, String travelWith,
			String departureDate, String arrivalDate) throws InterruptedException, InvocationTargetException {
		selectTravelType(tripType);
		selectTravelCountry(tripType, country);
		provideTravellingPartnerDetails(travelWith, "0");
		enterTravelDates(tripType, departureDate, arrivalDate);
		disclaimerWindow();
	}

	public void quoteDetailsForOneWayTripForFamilyAndGroupPastDate(String tripType, String country, String travelWith,
			String noOfTravellers, String departureDate, String arrivalDate)
			throws InterruptedException, InvocationTargetException {
		selectTravelType(tripType);
		selectTravelCountry(tripType, country);
		provideTravellingPartnerDetails(travelWith, noOfTravellers);
		enterTravelDates(tripType, departureDate, arrivalDate);
		disclaimerWindow();
	}

	public void quoteDetailsForOneWayTripForMeAndDuoFutureDate(String tripType, String country, String travelWith,
			String departureDate, String arrivalDate) throws InterruptedException, InvocationTargetException {
		selectTravelType(tripType);
		selectTravelCountry(tripType, country);
		provideTravellingPartnerDetails(travelWith, "0");
		enterTravelDates(tripType, departureDate, arrivalDate);
	}

	public void quoteDetailsForOneWayTripForFamilyAndGroupFutureDate(String tripType, String country, String travelWith,
			String noOfTravellers, String departureDate, String arrivalDate)
			throws InterruptedException, InvocationTargetException {
		selectTravelType(tripType);
		selectTravelCountry(tripType, country);
		provideTravellingPartnerDetails(travelWith, noOfTravellers);
		enterTravelDates(tripType, departureDate, arrivalDate);
	}

	public void quoteTravel(String tripType, String country, String travelWith) {
		try {
			selectTravelType(tripType);
			selectTravelCountry(tripType, country);
			selectTravelWith(travelWith);
		} catch (Exception e) {
			log.info("Quote entry page have some issue", e);
		}
	}
	

//	=========================Annual========================
	public void quoteDetailsForAnnualTripForMeAndDuoToday(String tripType, String country, String travelWith)
			throws InterruptedException, InvocationTargetException {
		selectTravelType(tripType);
		selectTravelCountry(tripType, country);
		provideTravellingPartnerDetails(travelWith, "0");
		enterTravelDatesByDuration(tripType, "0", "2");
	}

	public void quoteDetailsForAnnualTripForFamilyAndGroupToday(String tripType, String country, String travelWith,
			String noOfTravellers) throws InterruptedException, InvocationTargetException {
		selectTravelType(tripType);
		selectTravelCountry(tripType, country);
		provideTravellingPartnerDetails(travelWith, noOfTravellers);
		enterTravelDatesByDuration(tripType, "0", "2");
	}

	public void quoteDetailsForAnnualTripForMeAndDuoPastByDate(String tripType, String country, String travelWith,
			String departureDate, String arrivalDate) throws InterruptedException, InvocationTargetException {
		selectTravelType(tripType);
		selectTravelCountry(tripType, country);
		provideTravellingPartnerDetails(travelWith, "0");
		enterTravelDates(tripType, departureDate, arrivalDate);
		disclaimerWindow();
	}

	public void quoteDetailsForAnnualTripForFamilyAndGroupPastDate(String tripType, String country, String travelWith,
			String noOfTravellers, String departureDate, String arrivalDate)
			throws InterruptedException, InvocationTargetException {
		selectTravelType(tripType);
		selectTravelCountry(tripType, country);
		provideTravellingPartnerDetails(travelWith, noOfTravellers);
		enterTravelDates(tripType, departureDate, arrivalDate);
		disclaimerWindow();
	}

	public void quoteDetailsForAnnualTripForMeAndDuoFutureDate(String tripType, String country, String travelWith,
			String departureDate, String arrivalDate) throws InterruptedException, InvocationTargetException {
		selectTravelType(tripType);
		selectTravelCountry(tripType, country);
		provideTravellingPartnerDetails(travelWith, "0");
		enterTravelDates(tripType, departureDate, arrivalDate);
	}

	public void quoteDetailsForAnnualTripForFamilyAndGroupFutureDate(String tripType, String country, String travelWith,
			String noOfTravellers, String departureDate, String arrivalDate)
			throws InterruptedException, InvocationTargetException {
		selectTravelType(tripType);
		selectTravelCountry(tripType, country);
		provideTravellingPartnerDetails(travelWith, noOfTravellers);
		enterTravelDates(tripType, departureDate, arrivalDate);
	}

//	==========================================================
	public void validateBehaviourOfAllFieldsInQuotePage(String tripType) {

		verifyElementinPage(dinaHeaderImage);
		verifyElementinPage(ddTravelType);
		verifyElementinPage(travelTypeLabel);
		verifyElementinPage(travelWithLabel);
		verifyElementinPage(promoCodeLabel);
		verifyElementinPage(departureOnLabel);

		// verify mandatory fields
		verifyFieldisMandatory(ddTravelType);
		verifyFieldisMandatory(travelTypeLabel);
		verifyFieldisMandatory(travelWithLabel);
		verifyFieldisNotMandatory(promoCodeLabel);
		verifyFieldisMandatory(departureOnLabel);

		// select other fields to make return date visible
		try {
			selectTravelType(tripType);
			if ((tripType.trim()).equals((Constants.SINGLE_TRIP).trim())) {
				verifyElementinPage(returnOnLabel);
				verifyFieldisMandatory(returnOnLabel);
			}
		} catch (Exception e) {
			log.info("Quote entry page have some issue", e);
		}
	}

	public boolean verifyFieldisMandatory(WebElement webElement) {
		String labelText = webElement.getText();
		boolean flag = false;
		if (labelText.contains("*")) {
			flag = true;
		}
		return flag;
	}

	public boolean verifyFieldisNotMandatory(WebElement webElement) {
		String labelText = webElement.getText();
		boolean flag = false;
		if (!labelText.contains("*")) {
			flag = true;
		}
		return flag;
	}

//	======Senior Citizen Msg=================================

	public void validateSeniorCitizenMsg() {
		verifyElementinPage(seniorCitizenTitle);
		verifyElementinPage(seniorCitizenDesc);

		String seniorCtznTitle = seniorCitizenTitle.getText();
		Assert.assertEquals(seniorCtznTitle, Constants.SENIOR_CITIZEN_TITLE, "Senior citizen title is not matching");

		String seniorCtznDesc = seniorCitizenTitle.getText();
		Assert.assertEquals(seniorCtznDesc, Constants.SENIOR_CITIZEN_DESC,
				"Senior citizen description is not matching");
	}

//	===========================================================

	public Boolean verifyChoosePlanEnabled() {
		Boolean idChoosePlanEnabled = null;
		try {
			idChoosePlanEnabled = choosePlan.isEnabled();
		} catch (Exception e) {
			log.info("Status of choosePlan buton is {}", choosePlan.isEnabled(), e);
		}
		return idChoosePlanEnabled;
	}

	public String verifyCountriesLimitErrrorMsg() {
		String actCountriesLimitErrorMsg = null;
		try {
			countriesLimitErrorMsg.isDisplayed();
			actCountriesLimitErrorMsg = countriesLimitErrorMsg.getText();
		} catch (Exception e) {
			log.info("Counties should not exceed more than 5 error message is not displayed {}",
					countriesLimitErrorMsg.getText(), e);
		}
		return actCountriesLimitErrorMsg;
	}

	// ***********Promo Code*******************

	public void enterPromoCode(String promoCodeData) throws InterruptedException {
		wUtil.waitForElementToBeClickble(promoCode);
		elementActionsUtil.doActionsSendKeys(promoCode, promoCodeData);
	}

	public boolean getPromocodeError() {
		boolean promocodeErrorMsg = null != null;
		try {
			promocodeErrorMsg = expiredPromocode.isDisplayed();
		} catch (Exception e) {
			log.info("Error message is {}", expiredPromocode.isDisplayed(), e);
		}
		return promocodeErrorMsg;
	}

	public String invalidPromocodeError() {
		String invalidpromocodeErrorMsg = null;
		try {
			promocodeError.isDisplayed();
			invalidpromocodeErrorMsg = promocodeError.getText();
		} catch (Exception e) {
			log.info("Error message is {}", promocodeError.isDisplayed(), e);
		}
		return invalidpromocodeErrorMsg;
	}

	public void delPromocode() {
		promoCode.clear();
	}

	public APACQuotePlanPage enterPromocode(String tripType, String travelWith, String country, String selectDate,
			String promocode) {
		try {
			quoteTravel(tripType, country, travelWith);
			selectDepartingOn(selectDate);
			enterPromoCode(promocode);
			clickQuotePlan();
		} catch (Exception e) {
			log.info("Quote entry page have some issue", e);
		}
		return new APACQuotePlanPage(driver);
	}

	// **********Footer and Logo validation***************
	public void enterQuoteTravelDetailsAndValidateFooter(String tripType, String travelWith,
			String country, String departDate, String returnDate) {
		try {
			quoteTravel(tripType, country, travelWith);
			selectDepartingOn(departDate);
			selectReturningDate(returnDate);
			verifyFooterElements();
			verifyDisclaimerElement();
		} catch (Exception e) {
			log.info("Quote entry page have some issue", e);
		}
	}

	public void clickZurichLogo() {
		try {
			wUtil.waitForElementToBeClickble(zurichLogo);
			elementActionsUtil.doActionsClick(zurichLogo);
		} catch (Exception e) {
			log.info("Home page is not landed", e);
		}
	}

	public void verifyZurichLogo() {
		verifyElementinPage(zurichLogo);
	}
	
	public void validateomePageURL(String expectedURL) {
		wUtil.waitForElementToBeVisible(headerElement, 5000);
		String url = null;
		try {
			url = elementActionsUtil.getUrlOfTheScreen();
			Assert.assertEquals(url, expectedURL, "Home page url is not matching");
		} catch (Exception e) {
			log.info("Error message is {}", url, e);
		}
	}

	public void validateFaceBookLink(String urlText) {
		wUtil.waitForElementToBeVisible(zurichLogo, 5000);
		js.scrollPageDown();
		String hrefText = null;
		try {
			hrefText = elementActionsUtil.getHref(facebookIcon);
			Assert.assertEquals(hrefText, urlText, "Footer Facebook link is not matching");
		} catch (Exception e) {
			log.info("Error message is {}", hrefText, e);
		}
	}

	public void validateTwitterLink(String urlText) {
		wUtil.waitForElementToBeVisible(zurichLogo, 5000);
		js.scrollPageDown();
		String hrefText = null;
		try {
			hrefText = elementActionsUtil.getHref(twitterIcon);
			Assert.assertEquals(hrefText, urlText, "Footer Twitter link is not matching");
		} catch (Exception e) {
			log.info("Error message is {}", hrefText, e);
		}
	}

	public void validateInstagramLink(String urlText) {
		wUtil.waitForElementToBeVisible(zurichLogo, 5000);
		js.scrollPageDown();
		String hrefText = null;
		try {
			hrefText = elementActionsUtil.getHref(instagramIcon);
			Assert.assertEquals(hrefText, urlText, "Footer Instagram link is not matching");
		} catch (Exception e) {
			log.info("Error message is {}", hrefText, e);
		}
	}

	public void validateYoutubeLink(String urlText) {
		wUtil.waitForElementToBeVisible(zurichLogo, 5000);
		js.scrollPageDown();
		String hrefText = null;
		try {
			hrefText = elementActionsUtil.getHref(youtubeIcon);
			Assert.assertEquals(hrefText, urlText, "Footer Youtube link is not matching");
		} catch (Exception e) {
			log.info("Error message is {}", hrefText, e);
		}
	}

	public void validateFooterLink(String footerName, WebElement element) {
		wUtil.waitForElementToBeVisible(zurichLogo, 5000);
		js.scrollPageDown();
		String hrefText = null;
		String urltext = Constants.FOOTER_LINK.get(footerName);
		try {
			hrefText = elementActionsUtil.getHref(element);
			Assert.assertEquals(hrefText, urltext, "Footer links are not matching");
		} catch (Exception e) {
			log.info("Error message is {}", hrefText, e);
		}
	}

	public void openFooterLink(String footerName, WebElement element) throws Exception {
		wUtil.waitForElementToBeVisible(element, 5000);
		elementActionsUtil.doActionsClick(element);
		ArrayList<String> tabs = new ArrayList<String>(driver.getWindowHandles());
		driver.switchTo().window(tabs.get(1));
		String actUrl = elementActionsUtil.getUrlOfTheScreen();
		String expUrl = Constants.FOOTER_LINK.get(footerName);
		Assert.assertTrue(actUrl.equalsIgnoreCase(expUrl), "Footer expected url is not matching");
		driver.close();
	}

	public void validateandOpenFooterLink(String linkText) throws Exception {
		validateFooterLink(linkText, FOOTER_WEBELEMENT.get(linkText));
		openFooterLink(linkText, FOOTER_WEBELEMENT.get(linkText));
	}

	public void verifyElementinPage(WebElement element) {
		wUtil.waitForElementToBeVisible(element);
		boolean isElementPresent = element.isDisplayed();
		Assert.assertTrue(isElementPresent, "Element is not present in the page");
		log.info("Element " + element + "is present in page");
	}

	public void verifyFooterElements() {
		try {
			verifyElementinPage(facebookIcon);
			verifyElementinPage(twitterIcon);
			verifyElementinPage(instagramIcon);
			verifyElementinPage(youtubeIcon);

			verifyElementinPage(legal);
			verifyElementinPage(privacyPolicy);
			verifyElementinPage(termsOfUse);
			verifyElementinPage(contactUs);
		} catch (Exception e) {
			log.info("exception is {}", e);
		}
	}

	public void verifyDisclaimerElement() {
		verifyElementinPage(footerDisclaimer);
	}

	public WebElement getFooterMapValue(String mapKey) {
		return FOOTER_WEBELEMENT.get(mapKey);
	}

	public void openNavigationBar() throws InterruptedException {
		wUtil.waitForElementToBeVisible(sandwichIcon);
		js.clickElementByJS(sandwichIcon);
	}

	public String validateDate(String travelDate) {
		returnDate.sendKeys(Keys.ENTER);
		if (travelDate.equalsIgnoreCase("Departing")) {
			wUtil.waitForElementToBeClickble(departureDate, 1000);
			return departureDate.getAttribute("value");
		} else {
			wUtil.waitForElementToBeClickble(returnDate, 1000);
			return returnDate.getAttribute("value");
		}
	}
	
	//*****************Progress Bar************
	public void verifyProgressQuoteBar() {
		String hex=null;
		try {
			String bar2=progressBarStep1.getCssValue("color");
			 hex = Color.fromString(bar2).asHex();
			 Assert.assertEquals(hex,"#23366f","Quote Icon progress bar is not matching");
		} catch (Exception e) {
			log.info("Quote Icon progress bar is not matching{}", hex, e);
		}
	}

	
}
