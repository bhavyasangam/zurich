package com.zurich.qa.digitalnative.testcases;

import java.lang.reflect.InvocationTargetException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.zurich.qa.digitalnative.base.TestBase;
import com.zurich.qa.digitalnative.listeners.TestAllureListener;
import com.zurich.qa.digitalnative.pages.APACQuotePage;
import com.zurich.qa.digitalnative.pages.APACQuotePage;
import com.zurich.qa.digitalnative.utils.BaseInitializer;
import com.zurich.qa.digitalnative.utils.DatePickerUtil;
import com.zurich.qa.digitalnative.utils.ExcelUtil;

import io.qameta.allure.Description;

@Listeners(TestAllureListener.class)
public class APACQuoteTest extends TestBase {
	APACQuotePage aAPACQuote;
	DatePickerUtil dateUtil = new DatePickerUtil();
	String departureDate = dateUtil.currentDate();
	String returnDate = dateUtil.futureDate(1);
	String futureDepartureDate = dateUtil.futureDate(20);
	String futureReturnDate = dateUtil.futureDate(24);
	String pastDepartureDate = dateUtil.previousDate(3);
	private static Logger log = LogManager.getLogger(APACQuoteTest.class);

	String departureDate1 = dateUtil.currentDate();
	String returnDate1 = dateUtil.futureDate(4);

	String pastDate = dateUtil.previousDate(4);
//====================Moumita=========================================	
	@BeforeTest
	public void createQuotePageInstance() {
		driver = BaseInitializer.getInstance(BaseInitializer.moduleName(this)).getwebDriver();
	}

	@Description("Validating different travel types for Quote screen [Automation Coverage only]")
	@Test(dataProvider = "getData")
	public void quoteTravelTypes(String tripType, String country, String travelWith, String noOfTravellers,
			String departureDateDuration, String returnDateDuration)
			throws InterruptedException, InvocationTargetException {
		//aAPACQuote = new APACQuotePage(driver);
		aAPACQuote = new APACQuotePage(driver);
		aAPACQuote.selectTravelType(tripType);
		aAPACQuote.selectTravelCountry(tripType, country);
		aAPACQuote.provideTravellingPartnerDetails(travelWith, noOfTravellers);
		aAPACQuote.enterTravelDatesByDuration(tripType, departureDateDuration, returnDateDuration);
		Assert.assertTrue(aAPACQuote.verifyChoosePlanEnabled(),"Choose Plan is not enabled");
	}

	@Description("Validating different travel types for Quote screen [Automation Coverage only]")
	@Test(dataProvider = "getData")
	public void quoteTravelTypes1(String tripType, String country, String travelWith, String noOfTravellers,
			String departureDateDuration, String returnDateDuration)
			throws InterruptedException, InvocationTargetException {
		aAPACQuote = new APACQuotePage(driver);
		aAPACQuote.quoteDetailsForSingleTrip(tripType, country, travelWith, noOfTravellers, departureDate1,
				returnDate1);

		Assert.assertTrue(aAPACQuote.verifyChoosePlanEnabled(),"Choose Plan is not enabled");
	}

	@Description("Common cases : Validate the behaviour of Number of Travellers. My family with travellers as below 3/above100/")
	@Test(dataProvider = "getData")
	public void invalidTravellersCountInMyFamily_DINA84_14(String tripType, String country, String travelWith,
			String noOfTravellers, String departureDateDuration, String returnDateDuration) throws Exception {
		aAPACQuote = new APACQuotePage(driver);
		aAPACQuote.selectTravelType(tripType);
		aAPACQuote.selectTravelCountry(tripType, country);
		aAPACQuote.provideTravellingPartnerDetails(travelWith, noOfTravellers);
		aAPACQuote.enterTravelDatesByDuration(tripType, departureDateDuration, returnDateDuration);
		aAPACQuote.clickQuotePlan();
		Assert.assertEquals(aAPACQuote.getTravellersError(), true,"Travellers error is not displaying");
	}
	
	@Description("Common cases : Verify that notification message is present on Screen for Seniors")
	@Test(dataProvider = "getData")
	public void verifyNotificationMessageForSenior_DINACommon_1(String tripType, String country, String travelWith,
			String noOfTravellers, String departureDateDuration, String returnDateDuration) throws Exception {
		aAPACQuote = new APACQuotePage(driver);
		aAPACQuote.validateSeniorCitizenMsg();
	}

	@Description("DINA 84(2,3) : Verify that  Customer Travelling 'Me' and 'Me+1' is able start the Quote creation for Single Trip with Departure date as Today's Date.")
	@Test(dataProvider = "getData")
	public void validateSingleTripForMeAndDuoDINA84(String tripType, String country, String travelWith,
			String noOfTravellers) throws InterruptedException, InvocationTargetException {
		aAPACQuote = new APACQuotePage(driver);
		aAPACQuote.quoteDetailsForSingleTripForMeAndDuoToday(tripType, country, travelWith);
		Assert.assertTrue(aAPACQuote.verifyChoosePlanEnabled(),"Choose Plan is not enabled");
	}

	@Description("DINA 84(4,5) :Verify Customer Travelling as 'My Family' and 'A group' is able start the Quote creation for Single Trip with Today's Date.")
	@Test(dataProvider = "getData")
	public void validateSingleTripForFamilyAndGroupDINA84(String tripType, String country, String travelWith,
			String noOfTravellers, String departureDateDuration, String returnDateDuration) throws Exception {
		//aAPACQuote = new APACQuotePage(driver);
		aAPACQuote = new APACQuotePage(driver);
		aAPACQuote.quoteDetailsForSingleTripForFamilyAndGroupToday(tripType, country, travelWith, noOfTravellers);
		aAPACQuote.clickOnRandomObjectInQuotePage();
		Assert.assertTrue(aAPACQuote.verifyChoosePlanEnabled(),"Choose Plan is not enabled");
	}

	@Description("DINA 84(6,7) :Verify that  Customer Travelling as \"Me\" and 'Me+1' is able start the Quote creation for Single Trip with Travel Dates in Past.")
	@Test(dataProvider = "getData")
	public void validateSingleTripJustMeAndDuoPastDate_DINA84_6_7(String tripType, String country, String travelWith,
			String noOfTravellers) throws InterruptedException, InvocationTargetException {
		//aAPACQuote = new APACQuotePage(driver);
		aAPACQuote = new APACQuotePage(driver);
		aAPACQuote.quoteDetailsForSingleTripForMeAndDuoPastByDate(tripType, country, travelWith, pastDepartureDate,
				returnDate);
		Assert.assertTrue(aAPACQuote.verifyChoosePlanEnabled(),"Choose Plan is not enabled");
	}

	@Description("DINA 84(8,9) :Verify that  Customer Travelling as 'My Family' and 'A group' is able start the Quote creation for Single Trip with Travel Dates in Past.")
	@Test(dataProvider = "getData")
	public void validateSingleTripForFamilyAndGroupPastDateDINA84_8_9(String tripType, String country,
			String travelWith, String noOfTravellers) throws InterruptedException, InvocationTargetException {
		aAPACQuote = new APACQuotePage(driver);
		aAPACQuote.quoteDetailsForSingleTripForFamilyAndGroupPastDate(tripType, country, travelWith, noOfTravellers,
				pastDepartureDate, returnDate);
		Assert.assertTrue(aAPACQuote.verifyChoosePlanEnabled(),"Choose Plan is not enabled");
	}

	@Description("DINA 84(10,11) :Verify that  Customer Travelling as 'Just Me' and 'Me + 1' is able start the Quote creation for Single Trip with Travel Dates in Future.")
	@Test(dataProvider = "getData")
	public void validateSingleTripForFamilyAndGroupPastDateDINA84_10_11(String tripType, String country,
			String travelWith, String noOfTravellers) throws InterruptedException, InvocationTargetException {
		aAPACQuote = new APACQuotePage(driver);
		aAPACQuote.quoteDetailsForSingleTripForMeAndDuoFutureDate(tripType, country, travelWith, futureDepartureDate,
				futureReturnDate);
		Assert.assertTrue(aAPACQuote.verifyChoosePlanEnabled(),"Choose Plan is not enabled");
	}

	@Description("DINA 84(12,13) :Verify that  Customer Travelling as 'My Family' and 'A group' is able start the Quote creation for Single Trip with Travel Dates in Past.")
	@Test(dataProvider = "getData")
	public void validateSingleTripForFamilyAndGroupPastDateDINA84_12_13(String tripType, String country,
			String travelWith, String noOfTravellers) throws InterruptedException, InvocationTargetException {
		aAPACQuote = new APACQuotePage(driver);
		aAPACQuote.quoteDetailsForSingleTripForFamilyAndGroupFutureDate(tripType, country, travelWith, noOfTravellers,
				pastDepartureDate, returnDate);
		Assert.assertTrue(aAPACQuote.verifyChoosePlanEnabled(),"Choose Plan is not enabled");
	}
	
	@Description("DINA 85(2,3) :Verify that  Customer Travelling as 'Just Me' and 'Me + 1'' is able start the Quote creation for One-way Trip with Travel DatesToday.")
	@Test(dataProvider = "getData")
	public void validateOnewayTripMeAndDuoToDAYDINA85_2_3(String tripType, String country,
			String travelWith, String noOfTravellers) throws InterruptedException, InvocationTargetException {
		aAPACQuote = new APACQuotePage(driver);
		aAPACQuote.quoteDetailsForAnnualTripForMeAndDuoToday(tripType, country, travelWith);
		Assert.assertTrue(aAPACQuote.verifyChoosePlanEnabled(),"Choose Plan is not enabled");
	}
	
	@Description("DINA 85(4,5) :Verify that  Customer Travelling as 'My family' and 'A group' is able start the Quote creation for One-way Trip with Travel DatesToday.")
	@Test(dataProvider = "getData")
	public void validateOneWayTripForFamilyAndGroupDINA85_4_5(String tripType, String country, String travelWith,
			String noOfTravellers) throws Exception {
		aAPACQuote = new APACQuotePage(driver);
		aAPACQuote.quoteDetailsForOneWayTripForFamilyAndGroupToday(tripType, country, travelWith, noOfTravellers);
		Assert.assertTrue(aAPACQuote.verifyChoosePlanEnabled(),"Choose Plan is not enabled");
	}
	
	@Description("DINA 85(6,7) :Verify that  Customer Travelling as \"Me\" and 'Me+1' is able start the Quote creation for One-way Trip with Travel Dates in Past.")
	@Test(dataProvider = "getData")
	public void validateOneWayTripJustMeAndDuoPastDate_DINA85_6_7(String tripType, String country, String travelWith,
			String noOfTravellers) throws InterruptedException, InvocationTargetException {
		aAPACQuote = new APACQuotePage(driver);
		aAPACQuote.quoteDetailsForOneWayTripForMeAndDuoPastByDate(tripType, country, travelWith, pastDepartureDate,
				returnDate);
		Assert.assertTrue(aAPACQuote.verifyChoosePlanEnabled(),"Choose Plan is not enabled");
	}

	@Description("DINA 85(8,9) :Verify that  Customer Travelling as 'My Family' and 'A group' is able start the Quote creation for One-way Trip with Travel Dates in Past.")
	@Test(dataProvider = "getData")
	public void validateOneWayTripForFamilyAndGroupPastDateDINA85_8_9(String tripType, String country,
			String travelWith, String noOfTravellers) throws InterruptedException, InvocationTargetException {
		aAPACQuote = new APACQuotePage(driver);
		aAPACQuote.quoteDetailsForOneWayTripForFamilyAndGroupPastDate(tripType, country, travelWith, noOfTravellers,
				pastDepartureDate, returnDate);
		Assert.assertTrue(aAPACQuote.verifyChoosePlanEnabled(),"Choose Plan is not enabled");
	}

	@Description("DINA 85(10,11) :Verify that  Customer Travelling as 'Just Me' and 'Me + 1' is able start the Quote creation for One-way Trip with Travel Dates in Future.")
	@Test(dataProvider = "getData")
	public void validateOneWayTripForFamilyAndGroupFutureDateDINA85_10_11(String tripType, String country,
			String travelWith, String noOfTravellers) throws InterruptedException, InvocationTargetException {
		aAPACQuote = new APACQuotePage(driver);
		aAPACQuote.quoteDetailsForOneWayTripForMeAndDuoFutureDate(tripType, country, travelWith, futureDepartureDate,
				futureReturnDate);
		Assert.assertTrue(aAPACQuote.verifyChoosePlanEnabled(),"Choose Plan is not enabled");
	}

	@Description("DINA 85(12,13) :Verify that  Customer Travelling as 'My Family' and 'A group' is able start the Quote creation for One-way Trip with Travel Dates in Past.")
	@Test(dataProvider = "getData")
	public void validateOneWayTripForFamilyAndGroupPastDateDINA85_12_13(String tripType, String country,
			String travelWith, String noOfTravellers) throws InterruptedException, InvocationTargetException {
		aAPACQuote = new APACQuotePage(driver);
		aAPACQuote.quoteDetailsForOneWayTripForFamilyAndGroupFutureDate(tripType, country, travelWith, noOfTravellers,
				pastDepartureDate, returnDate);
		Assert.assertTrue(aAPACQuote.verifyChoosePlanEnabled(),"Choose Plan is not enabled");
	}
	
	
	@Description("DINA 86(2,3) :Verify that  Customer Travelling as 'Just Me' and 'Me + 1'' is able start the Quote creation for Annual Trip with Travel DatesToday.")
	@Test(dataProvider = "getData")
	public void validateAnnualTravelMeAndDuoToDAYDINA86_2_3(String tripType, String country,
			String travelWith, String noOfTravellers) throws InterruptedException, InvocationTargetException {
		aAPACQuote = new APACQuotePage(driver);
		aAPACQuote.quoteDetailsForAnnualTripForMeAndDuoToday(tripType, country, travelWith);
		Assert.assertTrue(aAPACQuote.verifyChoosePlanEnabled(),"Choose Plan is not enabled");
	}
	
	@Description("DINA 86(4,5) :Verify that  Customer Travelling as 'My family' and 'A group' is able start the Quote creation for Annual Travel with Travel DatesToday.")
	@Test(dataProvider = "getData")
	public void validateAnnualTravelForFamilyAndGroupDINA86_4_5(String tripType, String country, String travelWith,
			String noOfTravellers) throws Exception {
		aAPACQuote = new APACQuotePage(driver);
		aAPACQuote.quoteDetailsForAnnualTripForFamilyAndGroupToday(tripType, country, travelWith, noOfTravellers);
		Assert.assertTrue(aAPACQuote.verifyChoosePlanEnabled(),"Choose Plan is not enabled");
	}
	
	@Description("DINA 86(6,7) :Verify that  Customer Travelling as \"Me\" and 'Me+1' is able start the Quote creation for Annual Travel with Travel Dates in Past.")
	@Test(dataProvider = "getData")
	public void validateAnnualTravelJustMeAndDuoPastDate_DINA86_6_7(String tripType, String country, String travelWith,
			String noOfTravellers) throws InterruptedException, InvocationTargetException {
		aAPACQuote = new APACQuotePage(driver);
		aAPACQuote.quoteDetailsForAnnualTripForMeAndDuoPastByDate(tripType, country, travelWith, pastDepartureDate,
				returnDate);
		Assert.assertTrue(aAPACQuote.verifyChoosePlanEnabled(),"Choose Plan is not enabled");
	}

	@Description("DINA 85(8,9) :Verify that  Customer Travelling as 'My Family' and 'A group' is able start the Quote creation for Annual Travel with Travel Dates in Past.")
	@Test(dataProvider = "getData")
	public void validateAnnualTravelForFamilyAndGroupPastDateDINA86_8_9(String tripType, String country,
			String travelWith, String noOfTravellers) throws InterruptedException, InvocationTargetException {
		aAPACQuote = new APACQuotePage(driver);
		aAPACQuote.quoteDetailsForAnnualTripForFamilyAndGroupPastDate(tripType, country, travelWith, noOfTravellers,
				pastDepartureDate, returnDate);
		Assert.assertTrue(aAPACQuote.verifyChoosePlanEnabled(),"Choose Plan is not enabled");
	}

	@Description("DINA 85(10,11) :Verify that  Customer Travelling as 'Just Me' and 'Me + 1' is able start the Quote creation for Annual Travel with Travel Dates in Future.")
	@Test(dataProvider = "getData")
	public void validateAnnualTravelForFamilyAndGroupFutureDateDINA86_10_11(String tripType, String country,
			String travelWith, String noOfTravellers) throws InterruptedException, InvocationTargetException {
		aAPACQuote = new APACQuotePage(driver);
		aAPACQuote.quoteDetailsForAnnualTripForMeAndDuoFutureDate(tripType, country, travelWith, futureDepartureDate,
				futureReturnDate);
		Assert.assertTrue(aAPACQuote.verifyChoosePlanEnabled(),"Choose Plan is not enabled");
	}

	@Description("DINA 85(12,13) :Verify that  Customer Travelling as 'My Family' and 'A group' is able start the Quote creation for Annual Travel with Travel Dates in Past.")
	@Test(dataProvider = "getData")
	public void validateAnnualTravelForFamilyAndGroupPastDateDINA86_12_13(String tripType, String country,
			String travelWith, String noOfTravellers) throws InterruptedException, InvocationTargetException {
		aAPACQuote = new APACQuotePage(driver);
		aAPACQuote.quoteDetailsForAnnualTripForFamilyAndGroupFutureDate(tripType, country, travelWith, noOfTravellers,
				pastDepartureDate, returnDate);
		Assert.assertTrue(aAPACQuote.verifyChoosePlanEnabled(),"Choose Plan is not enabled");
	}
	
	@Description("DINA 84(1)_85(1)_86(1) :Validate the behaviour of various fields available")
	@Test(dataProvider = "getData")
	public void validateBehaviourOfVariousFieldDINA84_1DINA85_1DINA86_1(String tripType)
			throws InterruptedException, InvocationTargetException {
		aAPACQuote = new APACQuotePage(driver);
		aAPACQuote.validateBehaviourOfAllFieldsInQuotePage(tripType);
	}

	//Promo Code Test cases
	@Description("Ensure that Customer is not able to Proceed to Step 2 with Invalid Promocode.")
	@Test(dataProvider = "getData")
	public void validateInvalidPromocode_DINA6_1(String tripType, String country, String travelWith, String promocode)throws InterruptedException {
		aAPACQuote = new APACQuotePage(driver);	
		aAPACQuote.enterPromocode(tripType, country, travelWith, departureDate, promocode);
		Assert.assertEquals(aAPACQuote.invalidPromocodeError(), "Invalid promo code. Please enter the correct promo code");
	}
	
	@Description("Ensure that Customer is not able to Proceed to Step 2 with Expired Promocode.")
	@Test(dataProvider = "getData")
	public void validateExpiredPromocode_DINA6_2(String tripType, String country, String travelWith, String promocode)throws InterruptedException {
		aAPACQuote = new APACQuotePage(driver);	
		aAPACQuote.enterPromocode(tripType, country, travelWith, departureDate, promocode);
		Assert.assertEquals(aAPACQuote.invalidPromocodeError(), "Expired promo code. Please enter the correct promo code");
	}

	@Description("Ensure that Customer gets the appropiate discount when correct promo code is entered.")
	@Test(dataProvider = "getData")
	public void validatePromocode_DINA6_3(String tripType, String country, String travelWith, String promocode)throws InterruptedException {
		aAPACQuote = new APACQuotePage(driver);	
		aAPACQuote.enterPromocode(tripType, country, travelWith, departureDate, promocode);		
		Assert.assertEquals(aAPACQuote.invalidPromocodeError(), "***********");
	}

	@DataProvider(name = "getData")
	public Object[][] getData(ITestContext context) throws Exception {
		Object[][] testObjectArrray = ExcelUtil.getData(context);
		return testObjectArrray;
	}

}
