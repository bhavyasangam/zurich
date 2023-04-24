package com.zurich.qa.digitalnative.testcases;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.BeforeClass;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.zurich.qa.digitalnative.base.TestBase;
import com.zurich.qa.digitalnative.listeners.TestAllureListener;
import com.zurich.qa.digitalnative.pages.APACPayPage;
import com.zurich.qa.digitalnative.pages.APACQuotePage;
import com.zurich.qa.digitalnative.pages.APACQuotePlanPage;
import com.zurich.qa.digitalnative.pages.APACQuotePlanPage_Old;
import com.zurich.qa.digitalnative.pages.APACQuotePolicyHolderPage;
import com.zurich.qa.digitalnative.utils.DatePickerUtil;
import com.zurich.qa.digitalnative.utils.ElementActionsUtil;
import com.zurich.qa.digitalnative.utils.ExcelUtil;
import com.zurich.qa.digitalnative.utils.FakerUtil;
import com.zurich.qa.digitalnative.utils.WaitUtil;

import io.qameta.allure.Description;

@Listeners(TestAllureListener.class)
public class APACPayTest extends TestBase {
	APACQuotePage  aPACQuotePage;
	APACQuotePlanPage aPACQuotePlanPage;
	APACQuotePolicyHolderPage aPACQuotePolicyHolderPage;
	APACPayPage aPACPayPage;
	LinkedHashMap<String, String> data;
	DatePickerUtil dateUtil = new DatePickerUtil();
	String departureDate = dateUtil.futureDate(1);
	String returnDate = dateUtil.futureDate(5);
	static FakerUtil fu = new FakerUtil(driver);
	public static String firstName = fu.firstName();
	public static String lastName = fu.lastName();
	public static String dateofbirth = "09/08/1994";
	public static String idNumber = fu.idNumber();
	public static String emailAddress = fu.emailAddress();
	public static String mobileNumber = fu.phoneNumber();

	private static Logger log = LogManager.getLogger(APACPayTest.class);
	/*
	 * @Test public void initializePage() { aPACQuotePage = new
	 * APACQuotePage(driver); aPACQuotePlanPage = new APACQuotePlanPage(driver);
	 * aPACQuotePolicyHolderPage= new APACQuotePolicyHolderPage(driver); aPACPayPage
	 * = new APACPayPage(driver); }
	 */

	@Description("DINA-1725 This test case is to vallidate the travellers details labels")
	@Test(dataProvider = "getData")
	public void validateReviewTravellersDetails(String tripType, String travelWith, String country, String noOfTravellers, String planCardsList, String selectedPlan) throws Exception {
		aPACQuotePage = new APACQuotePage(driver);
		aPACQuotePlanPage = new APACQuotePlanPage(driver);
		aPACPayPage = new APACPayPage(driver);
		aPACQuotePage.quoteDetailsForSingleTrip(tripType,travelWith,country,noOfTravellers,departureDate,returnDate);
		aPACQuotePlanPage = aPACQuotePage.clickQuotePlan();
		aPACQuotePlanPage.selectPlan(selectedPlan);
		aPACQuotePolicyHolderPage=aPACQuotePlanPage.clickNextButton();
		aPACQuotePolicyHolderPage.iamTravellingAsYes(firstName, lastName, dateofbirth, idNumber, emailAddress,
				emailAddress, mobileNumber, "Male", "Passport", "India (+91)");
		aPACPayPage=aPACQuotePolicyHolderPage.clickNextBtn();
		aPACPayPage.paymentPageDetails("ShopeePay/Qris");
		ArrayList<String> travellersDetails = new ArrayList<String>();
		String[] travellersLabel = { "First Name", "Last Name", "Date of Birth", "Gender", "ID type", "ID Number",
				"Email", "Mobile Number" };
		for (String str : travellersLabel) {
			travellersDetails.add(str);
		}
		Assert.assertTrue(aPACPayPage.verifyReviewTravellersDetails(travellersDetails),"Pay review labels are not matching.");
	}

	@Description("DINA-88 This test case is to validate Single trip flow till payment screen")
	@Test(dataProvider = "getData")
	public void paymentDetailsSingleTrip(String tripType, String travelWith, String country, String payMethod,
			String reviewExpPayPremiumAmt) throws Exception {
		aPACQuotePage = new APACQuotePage(driver);
		aAPACQuotePlanPage = new APACQuotePlanPage(driver);
		aAPACPayPage = new APACPayPage(driver);
		aAPACQuotePlanPage = aPACQuotePage.enterQuoteTravelDetails(tripType, travelWith, country, departureDate);
		aAPACQuotePolicyHolderPage = aAPACQuotePlanPage.clickselectPlan();
		aAPACPayPage = aAPACQuotePolicyHolderPage.iamTravellingAsYes(firstName, lastName, dateofbirth, idNumber,
				emailAddress, emailAddress, mobileNumber);
		aAPACPayPage.paymentPageDetails(payMethod);
		Assert.assertEquals(aAPACPayPage.getReviewPremiumAmt(), reviewExpPayPremiumAmt);
		Assert.assertTrue(aAPACPayPage.verifyPayBtnEnabled());
	}

	@Description("DINA-88 This test case is to validate One way trip flow till payment screen")
	@Test(dataProvider = "getData")
	public void paymentDetailsOneway(String tripType, String travelWith, String country, String payMethod,
			String reviewExpPayPremiumAmt) throws Exception {
		aPACQuotePage = new APACQuotePage(driver);
		aAPACQuotePlanPage = new APACQuotePlanPage(driver);
		aAPACPayPage = new APACPayPage(driver);
		aAPACQuotePlanPage = aPACQuotePage.enterQuoteTravelDetails1(tripType, travelWith, country, departureDate);
		aAPACQuotePolicyHolderPage = aAPACQuotePlanPage.clickselectPlan();
		aAPACPayPage = aAPACQuotePolicyHolderPage.iamTravellingAsYes(firstName, lastName, dateofbirth, idNumber,
				emailAddress, emailAddress, mobileNumber);
		aAPACPayPage.paymentPageDetails(payMethod);
		Assert.assertTrue(aAPACPayPage.verifyPayBtnEnabled());
		Assert.assertEquals(aAPACPayPage.getReviewPremiumAmt(), reviewExpPayPremiumAmt);
	}

	@Description("DINA-88 This test case is to validate Annual trip flow till payment screen")
	@Test(dataProvider = "getData")
	public void paymentDetailsAnnual(String tripType, String travelTo, String travelWithAnnual, String payMethod,
			String reviewExpPayPremiumAmt) throws Exception {
		aPACQuotePage = new APACQuotePage(driver);
		aAPACQuotePlanPage = new APACQuotePlanPage(driver);
		aAPACPayPage = new APACPayPage(driver);
		aAPACQuotePlanPage = aAPACQuotePage.enterQuoteTravelDetails2(tripType, travelTo, travelWithAnnual,
				departureDate);
		aAPACQuotePolicyHolderPage = aAPACQuotePlanPage.clickSelectPlanAnnual();
		aAPACPayPage = aAPACQuotePolicyHolderPage.iamTravellingAsYes(firstName, lastName, dateofbirth, idNumber,
				emailAddress, emailAddress, mobileNumber);
		aAPACPayPage.paymentPageDetails(payMethod);
		Assert.assertTrue(aAPACPayPage.verifyPayBtnEnabled());
		Assert.assertEquals(aAPACPayPage.getReviewPremiumAmt(), reviewExpPayPremiumAmt);
	}

//	==================================
	@Description("357_1 Verify that Footer is available on all the Pages")
	@Test(dataProvider = "getData")
	public void verifyFooterInEveryPage(String tripType, String travelWith, String country, String noOfTravellers, String planCardsList, String selectedPlan) throws Exception {
		aPACQuotePage = new APACQuotePage(driver);
		aPACQuotePlanPage = new APACQuotePlanPage(driver);
		aPACQuotePolicyHolderPage= new APACQuotePolicyHolderPage(driver);
        aPACPayPage = new APACPayPage(driver);
        System.out.println("In th etest");
		aPACQuotePage.enterQuoteTravelDetailsAndValidateFooter(tripType, travelWith, country,
				departureDate,returnDate);
		aPACQuotePlanPage = aPACQuotePage.clickQuotePlan();
		System.out.println("In th etesting");
		aPACQuotePlanPage.selectPlan(selectedPlan);
		aPACQuotePlanPage.clickselectPlanAndVerifyFoorterDisclaimer();
		aPACQuotePolicyHolderPage=aPACQuotePlanPage.clickNextButton();
		aPACPayPage = aPACQuotePolicyHolderPage.enterpassengerDetailsAndVerifyFooterDisclaimer(firstName, lastName,
				dateofbirth, idNumber, emailAddress, emailAddress, mobileNumber);
		//aPACPayPage.verifyFooterElementsPayPage();
		//aPACPayPage.verifyDisclaimerElementPayPage();
	}

	@Description("357_6 Verify that Zurich Icon is available on Top Right of Header for all the Pages ")
	@Test(dataProvider = "getData")
	public void verifyZurichLogoInEveryPage(String tripType, String travelWith, String country, String noOfTravellers, String planCardsList, String selectedPlan) throws InterruptedException {
		aPACQuotePage = new APACQuotePage(driver);
		aPACQuotePlanPage = new APACQuotePlanPage(driver);
		aPACQuotePolicyHolderPage= new APACQuotePolicyHolderPage(driver);
        aPACPayPage = new APACPayPage(driver);
		aPACQuotePage.verifyZurichLogo(); String countries= "Australia";
		aPACQuotePage.quoteDetailsForSingleTrip(tripType,travelWith,countries,noOfTravellers,departureDate,returnDate);
		aPACQuotePlanPage = aPACQuotePage.clickQuotePlan();
		aPACQuotePlanPage.selectPlan(selectedPlan);
		aPACQuotePlanPage.clickselectPlanAndVerifyFoorterDisclaimer();
		aPACQuotePolicyHolderPage=aPACQuotePlanPage.clickNextButton();
		aPACPayPage = aPACQuotePolicyHolderPage.enterpassengerDetailsAndVerifyFooterDisclaimer(firstName, lastName,
				dateofbirth, idNumber, emailAddress, emailAddress, mobileNumber);
		//aPACPayPage.verifyZurichLogoElementPayPage();
	}
	
	@Description("DINA6_4 verify that permium is updated with valid promocode percentage")
	@Test(dataProvider = "getData")
	public void validateDeletePromocode_DINA6_4(String tripType, String travelWith, String country, String noOfTravellers, String planCardsList, String selectedPlan) throws Exception {
		aPACQuotePage = new APACQuotePage(driver);
		aPACQuotePlanPage = new APACQuotePlanPage(driver);
		aPACQuotePolicyHolderPage= new APACQuotePolicyHolderPage(driver);
        aPACPayPage = new APACPayPage(driver);
		String  promocode="SLR900";
		aPACQuotePage.enterPromocode(tripType, travelWith, country,departureDate, promocode);	
		aPACQuotePlanPage.selectPlan(selectedPlan);
		aPACQuotePolicyHolderPage=aPACQuotePlanPage.clickNextButton();
		aPACPayPage = aPACQuotePolicyHolderPage.iamTravellingAsYes(firstName, lastName, dateofbirth, idNumber, emailAddress, emailAddress, 			mobileNumber,codeType,genderType,idCardType );  
		aPACQuotePage.clickQuotePlan();
		//Assert.assertEquals(aPACQuotePage.promocodeErrorMessage(), "promocode is applied");
	}
	
	@Description("DINA-6_5 Validate that Customer is able to delete  the Promocode using back button anytime during Quote creation cycle.")
	@Test(dataProvider = "getData")
	public void validateDeletePromocode_DINA6_5(String tripTypes, String travelWith, String country, String noOfTravellers, String planCardsList, String selectedPlan) throws Exception {
		aPACQuotePage = new APACQuotePage(driver);	
		aPACQuotePlanPage = new APACQuotePlanPage(driver);
		String  promocode="SLR900"; String tripType="One-way trip";
		aPACQuotePage.enterPromocode(tripType, country, travelWith, departureDate, promocode);	
		aPACQuotePlanPage.clickBackButton();
		aPACQuotePage.delPromocode();
		//Assert.assertEquals(aPACQuotePage.promocodeErrorMessage(), "Expired promo code. Please enter the correct promo code");
	}

	@Description("DINA-6_6 Validate that Customer is able to delete  the Promocode using \"Edit step 1\" button.")
	@Test(dataProvider = "getData")
	public void validatePromocodeUsingEditDetailsInQuoteSummary_DINA6_6(String tripType, String travelWith, String country, String noOfTravellers, String planCardsList, String selectedPlan) throws Exception {
		aPACQuotePage = new APACQuotePage(driver);
		aPACQuotePlanPage = new APACQuotePlanPage(driver);
		aPACPayPage = new APACPayPage(driver);
		String  promocode="SLR900";
		aPACQuotePlanPage = aPACQuotePage.enterPromocode(tripType, travelWith, country,promocode,departureDate);
		//aAPACQuotePolicyHolderPage = aAPACQuotePlanPage.clickselectPlan();
		//aAPACQuotePlanPage = aPACQuotePage.clickQuotePlan();
		aPACQuotePlanPage.selectPlan(selectedPlan);
		aPACQuotePolicyHolderPage=aPACQuotePlanPage.clickNextButton();
		aPACPayPage = aPACQuotePolicyHolderPage.iamTravellingAsYes(firstName, lastName, dateofbirth, idNumber, emailAddress, emailAddress, mobileNumber,codeType,genderType,idCardType );                                        
		aPACPayPage.selectEditStep1AndEditDetails();
		aPACQuotePage.delPromocode();
		aPACQuotePage.clickQuotePlan();
		Assert.assertEquals(aPACQuotePlanPage.planPage(),"Choose and personalize my plan");
	}
	

	@DataProvider(name = "getData")
	public Object[][] getData(ITestContext context) throws Exception {
		Object[][] testObjectArrray = ExcelUtil.getData(context);
		return testObjectArrray;
	}

}
