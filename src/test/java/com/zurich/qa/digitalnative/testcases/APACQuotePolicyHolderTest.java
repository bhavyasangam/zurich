package com.zurich.qa.digitalnative.testcases;

import com.github.javafaker.Faker;
import com.zurich.qa.digitalnative.base.TestBase;
import com.zurich.qa.digitalnative.listeners.TestAllureListener;
import com.zurich.qa.digitalnative.pages.APACQuotePage;
import com.zurich.qa.digitalnative.pages.APACQuotePlanPage;
import com.zurich.qa.digitalnative.pages.APACQuotePolicyHolderPage;
import com.zurich.qa.digitalnative.utils.*;
import io.qameta.allure.Description;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestContext;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import java.text.SimpleDateFormat;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

@Listeners(TestAllureListener.class)
public class APACQuotePolicyHolderTest extends TestBase {

	APACQuotePlanPage aAPACQuotePlanPage;
	APACQuotePolicyHolderPage aAPACQuotePolicyHolderPage;
	APACQuotePage aAPACQuotePage;
	LinkedHashMap<String, String> data;
	DatePickerUtil dateUtil = new DatePickerUtil();
	String departureDate = dateUtil.currentDate();
	String returnDate = dateUtil.futureDate(1);
	String futureDepartureDate = dateUtil.futureDate(20);
	String pastDepartureDate = dateUtil.previousDate(3);
	static FakerUtil fu = new FakerUtil(driver);
	public static String firstName = fu.firstName();
	public static String lastName = fu.lastName();
	public static String dateofbirth = "09/08/1994";

	public static String idNumber = fu.idNumber();
	public static String emailAddress = fu.emailAddress();
	public static String mobileNumber = fu.phoneNumber();
	private static Logger log = LogManager.getLogger(APACQuotePolicyHolderTest.class);

	@Description("Verify in Family atleast 1 adult and at least 1 child should be travelling together")
	@Test(dataProvider = "getData")
	public void validateMandatoryFieldsDINA_94_1(String tripType, String travelWith, String country, String noOfTravellers, String planCardsList, String selectedPlan,String benefitCountry, String planPremium, String addOnsList, String selectAddOn, String coverages, String expPlanTotalPremium) throws InterruptedException {
		aAPACQuotePage = new APACQuotePage(driver);
		aAPACQuotePlanPage = new APACQuotePlanPage(driver);
		
		aAPACQuotePage.quoteDetailsForSingleTrip(tripType,travelWith,country,noOfTravellers,departureDate,returnDate);
		aAPACQuotePlanPage = aAPACQuotePage.clickQuotePlan();
		aAPACQuotePlanPage.selectPlan(selectedPlan);
		aAPACQuotePolicyHolderPage=aAPACQuotePlanPage.clickNextButton();
		aAPACQuotePolicyHolderPage.mandatoryfieldscheck();
	}

	@Description("Verify in Family atleast 1 adult and at least 1 child should be travelling together")
	@Test(dataProvider = "getData")
	public void Family1AdultMandatory_DINA_94_2(String tripType, String travelWith, String country, String travellers, String plans, String selectedPlan)
			throws Exception {
		//travellers = (int) Double.parseDouble(String.valueOf(travellers)) + "";
		aAPACQuotePage = new APACQuotePage(driver);
		aAPACQuotePlanPage = new APACQuotePlanPage(driver);
		aAPACQuotePage.quoteDetailsForSingleTripForFamilyAndGroupFutureDate(tripType,country,travelWith, travellers, departureDate,
				returnDate);
		aAPACQuotePlanPage = aAPACQuotePage.clickQuotePlan();
		aAPACQuotePlanPage.selectPlan(selectedPlan);
		aAPACQuotePolicyHolderPage=aAPACQuotePlanPage.clickNextButton();
		aAPACQuotePolicyHolderPage.iamTravellingAsYes(firstName, lastName, dateofbirth, idNumber, emailAddress,
				emailAddress, mobileNumber, "Male", "Passport", "India (+91)");
		for (int i = 1; i < 3; i++) {
			aAPACQuotePolicyHolderPage.autoFill(i, firstName, lastName, dateofbirth, "Male");}
		aAPACQuotePolicyHolderPage.clickNext();
		aAPACQuotePolicyHolderPage.errorMsgValidation(
				"If you are travelling with family then at least 1 adult and at least 1 child should be travelling together");
	}

	@Description("Verify If you are traveling with family then 2 adults and should not be of same gender")
	@Test(dataProvider = "getData")
	public void FamilyMandatoryDifferentGender_DINA_94_3(String tripType, String travelWith, String country, String travellers, String plans, String selectedPlan)
			throws Exception {
		Faker faker = new Faker(new Locale("en-IND"));
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		travellers = (int) Double.parseDouble(String.valueOf(travellers)) + "";
		aAPACQuotePage = new APACQuotePage(driver);
		aAPACQuotePlanPage = new APACQuotePlanPage(driver);
		aAPACQuotePage.quoteDetailsForSingleTripForFamilyAndGroupFutureDate(tripType,country,travelWith, travellers, departureDate,
				returnDate);
		aAPACQuotePlanPage = aAPACQuotePage.clickQuotePlan();
		aAPACQuotePlanPage.selectPlan(selectedPlan);
		aAPACQuotePolicyHolderPage=aAPACQuotePlanPage.clickNextButton();
		aAPACQuotePolicyHolderPage.iamTravellingAsYes(firstName, lastName,
				sdf.format(faker.date().past(2 * 365, TimeUnit.DAYS)), idNumber, emailAddress, emailAddress,
				mobileNumber, "Male", "Passport", "India (+91)");
		for (int i = 1; i < 3; i++)
			aAPACQuotePolicyHolderPage.autoFill(i, firstName, lastName, dateofbirth, "Male");
		aAPACQuotePolicyHolderPage.clickNext();
		aAPACQuotePolicyHolderPage.errorMsgValidation(
				"If you are travelling with 3 family member then at least 1 male and at least 1 female should be travelling together");

	}

	@Description("Verify for 100 customers allowed for Family")
	@Test(dataProvider = "getData")
	public void FamilyMax100_DINA_94_4(String tripType, String travelWith, String country, String travellers, String plans, String selectedPlan) throws Exception {
		Faker faker = new Faker(new Locale("en-IND"));
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		String gender = "", var = "";
		travellers = (int) Double.parseDouble(String.valueOf(travellers)) + "";
		aAPACQuotePage = new APACQuotePage(driver);
		aAPACQuotePlanPage = new APACQuotePlanPage(driver);
		aAPACQuotePage.quoteDetailsForSingleTripForFamilyAndGroupFutureDate(tripType,country,travelWith, travellers, departureDate,
				returnDate);
		aAPACQuotePlanPage = aAPACQuotePage.clickQuotePlan();
		aAPACQuotePlanPage.selectPlan(selectedPlan);
		aAPACQuotePolicyHolderPage=aAPACQuotePlanPage.clickNextButton();

		aAPACQuotePolicyHolderPage.iamTravellingAsYes(firstName, lastName,
				sdf.format(faker.date().past(2 * 365, TimeUnit.DAYS)), idNumber, emailAddress, emailAddress,
				mobileNumber, "Female", "Passport", "India (+91)");
		for (int i = 1; i < Integer.parseInt(travellers); i++) {
			if (i == 1)
				gender = "Male";
			else if (i == 2)
				gender = "Female";
			else {
				var = faker.dog().gender().toString();
				gender = var.substring(0, 1).toUpperCase() + var.substring(1);
			}
			aAPACQuotePolicyHolderPage.autoFill(i, firstName, lastName, dateofbirth, gender);
		}
		Assert.assertTrue(aAPACQuotePolicyHolderPage.noTravellers() == Integer.parseInt(travellers));
		aAPACQuotePolicyHolderPage.clickNext();
		WebDriverWait wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.titleIs("Checkout - Zurich"));
		Assert.assertTrue(driver.getTitle().equals("Checkout - Zurich"));
	}

	@Test(dataProvider = "getData")
	public void FamilyMax100NotTravelling_DINA_94_5(String tripType, String travelWith, String country, String travellers, String plans, String selectedPlan)
			throws Exception {
		Faker faker = new Faker(new Locale("en-IND"));
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		String gender = "", var = "";
		travellers = (int) Double.parseDouble(String.valueOf(travellers)) + "";
		aAPACQuotePage = new APACQuotePage(driver);
		aAPACQuotePlanPage = new APACQuotePlanPage(driver);
		aAPACQuotePage.quoteDetailsForSingleTripForFamilyAndGroupFutureDate(tripType,country,travelWith, travellers, departureDate,
				returnDate);
		aAPACQuotePlanPage = aAPACQuotePage.clickQuotePlan();
		aAPACQuotePlanPage.selectPlan(selectedPlan);
		aAPACQuotePolicyHolderPage=aAPACQuotePlanPage.clickNextButton();

		aAPACQuotePolicyHolderPage.iamTravellingAsNo(firstName, lastName, dateofbirth, idNumber, emailAddress,
				emailAddress, mobileNumber, "Male", "Passport", "India (+91)");
		String dob = "";

		for (int i = 1; i <= Integer.parseInt(travellers); i++) {
			if (i == 1) {
				gender = "Male";
				dob = sdf.format(faker.date().birthday(18, 70));
			} else if (i == 2) {
				gender = "Female";
				dob = sdf.format(faker.date().birthday(18, 70));
			} else {
				var = faker.dog().gender().toString();
				gender = var.substring(0, 1).toUpperCase() + var.substring(1);
				dob = sdf.format(faker.date().past(2 * 365, TimeUnit.DAYS));
			}
			aAPACQuotePolicyHolderPage.autoFill(i, firstName, lastName, dob, gender);
		}
		Assert.assertTrue(aAPACQuotePolicyHolderPage.noTravellers() - 1 == Integer.parseInt(travellers));
		aAPACQuotePolicyHolderPage.clickNext();

		WebDriverWait wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.titleIs("Checkout - Zurich"));
		Assert.assertTrue(driver.getTitle().equals("Checkout - Zurich"));
	}

	@Test(dataProvider = "getData")
	public void validateConfirmEmaildoesNotmatchEmailDINA_94_6(String tripType, String travelWith, String country, String noOfTravellers, String planCardsList, String selectedPlan,String benefitCountry, String planPremium, String addOnsList, String selectAddOn, String coverages, String expPlanTotalPremium) throws InterruptedException {
		aAPACQuotePage = new APACQuotePage(driver);
		aAPACQuotePlanPage = new APACQuotePlanPage(driver);
		aAPACQuotePage.quoteDetailsForSingleTrip(tripType,travelWith,country,noOfTravellers,departureDate,returnDate);
		aAPACQuotePlanPage = aAPACQuotePage.clickQuotePlan();
		aAPACQuotePlanPage.selectPlan(selectedPlan);
		aAPACQuotePolicyHolderPage=aAPACQuotePlanPage.clickNextButton();
		aAPACQuotePolicyHolderPage.clickPrimaryTraveller();
		String error = aAPACQuotePolicyHolderPage.confirmemailerror();
		Assert.assertTrue("The email addresses don't match".equals(error));
	}

	@Test(dataProvider = "getData")
	public void validateInvalidEmail_DINA_94_7(String tripType, String travelWith, String country, String noOfTravellers, String planCardsList, String selectedPlan,String benefitCountry, String planPremium, String addOnsList, String selectAddOn, String coverages, String expPlanTotalPremium) throws InterruptedException {
		String[] email = { "agmail.com", "Sam@gmailedu",
				"Loremipsumdolorsitamet*consectetueradipiscingelitsmDonecquamfeliciesnec@gmail.com" };
		aAPACQuotePage = new APACQuotePage(driver);
		aAPACQuotePlanPage = new APACQuotePlanPage(driver);
		aAPACQuotePage.quoteDetailsForSingleTrip(tripType,travelWith,country,noOfTravellers,departureDate,returnDate);
		aAPACQuotePlanPage = aAPACQuotePage.clickQuotePlan();
		aAPACQuotePlanPage.selectPlan(selectedPlan);
		aAPACQuotePolicyHolderPage=aAPACQuotePlanPage.clickNextButton();
		aAPACQuotePolicyHolderPage.clickPrimaryTraveller();
		for (String e : email) {
			String error = aAPACQuotePolicyHolderPage.InvalidEmail(e);
			Assert.assertTrue("Invalid email".equals(error));
		}
	}

	@Test(dataProvider = "getData")
	public void validateInvalidID_DINA_94_8(String tripType, String travelWith, String country, String noOfTravellers, String planCardsList, String selectedPlan,String benefitCountry, String planPremium, String addOnsList, String selectAddOn, String coverages, String expPlanTotalPremium) throws InterruptedException {
		String[] KTP_ID = { "A23456yrufi90759", "21345678901234", "123456789012345678905", "4556duyfs+*&&*35" };
		String[] Passport_ID = { "ABHIOPD LJVPO", "123 4", "AS13@#$UW&w1", "123qwe456rt0k", "12R" };
		aAPACQuotePage = new APACQuotePage(driver);
		aAPACQuotePlanPage = new APACQuotePlanPage(driver);
		aAPACQuotePage.quoteDetailsForSingleTrip(tripType,travelWith,country,noOfTravellers,departureDate,returnDate);
		aAPACQuotePlanPage = aAPACQuotePage.clickQuotePlan();
		aAPACQuotePlanPage.selectPlan(selectedPlan);
		aAPACQuotePolicyHolderPage=aAPACQuotePlanPage.clickNextButton();
		aAPACQuotePolicyHolderPage.clickPrimaryTraveller();
		for (String e : KTP_ID) {
			String error = aAPACQuotePolicyHolderPage.invalidID("KTP", e);
			Assert.assertTrue("Invalid KTP".equals(error));
		}
		for (String e : Passport_ID) {
			String error = aAPACQuotePolicyHolderPage.invalidID("Passport", e);
			Assert.assertTrue("Invalid passport".equals(error));
		}
	}

	@Test(dataProvider = "getData")
	public void validateInvalidMobileNoTest_DINA_94_9(String tripType, String travelWith, String country, String noOfTravellers, String planCardsList, String selectedPlan,String benefitCountry, String planPremium, String addOnsList, String selectAddOn, String coverages, String expPlanTotalPremium) throws InterruptedException {
		String[] mobile = { "ABHIOPDLJVPO", "1234", "AS13@#$UW&41", "1234562348901" };
		aAPACQuotePage = new APACQuotePage(driver);
		aAPACQuotePlanPage = new APACQuotePlanPage(driver);
		aAPACQuotePage.quoteDetailsForSingleTrip(tripType,travelWith,country,noOfTravellers,departureDate,returnDate);
		aAPACQuotePlanPage = aAPACQuotePage.clickQuotePlan();
		aAPACQuotePlanPage.selectPlan(selectedPlan);
		aAPACQuotePolicyHolderPage=aAPACQuotePlanPage.clickNextButton();
		aAPACQuotePolicyHolderPage.clickPrimaryTraveller();

		for (String e : mobile) {
			String error = aAPACQuotePolicyHolderPage.InvalidMobile(e);
			Assert.assertTrue("Invalid phone number".equals(error));
		}
	}

	@DataProvider(name = "getData")
	public Object[][] getData(ITestContext context) throws Exception {
		Object[][] testObjectArrray = ExcelUtil.getData(context);
		return testObjectArrray;
	}

}
