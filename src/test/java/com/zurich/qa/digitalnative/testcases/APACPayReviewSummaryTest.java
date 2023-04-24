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
import com.zurich.qa.digitalnative.pages.APACPayReviewSummaryPage;
import com.zurich.qa.digitalnative.pages.APACQuotePage;
import com.zurich.qa.digitalnative.pages.APACQuotePlanPage;
import com.zurich.qa.digitalnative.pages.APACQuotePolicyHolderPage;
import com.zurich.qa.digitalnative.utils.DatePickerUtil;
import com.zurich.qa.digitalnative.utils.ElementActionsUtil;
import com.zurich.qa.digitalnative.utils.ExcelUtil;
import com.zurich.qa.digitalnative.utils.FakerUtil;
import com.zurich.qa.digitalnative.utils.WaitUtil;

import io.qameta.allure.Description;

@Listeners(TestAllureListener.class)
public class APACPayReviewSummaryTest extends TestBase {

	APACQuotePlanPage aPACQuotePlanPage;
	APACQuotePolicyHolderPage aPACQuotePolicyHolderPage;
	APACPayReviewSummaryPage aPACPayReviewSummaryPage;
	APACQuotePage aPACQuotePage;
	APACPayPage aPACPayPage;
	DatePickerUtil  dateUtil =new DatePickerUtil();
	String departureDate = dateUtil.currentDate();
	String returnDate = dateUtil.futureDate(1);
	String futureDepartureDate =dateUtil.futureDate(20);
	String pastDepartureDate = dateUtil.previousDate(3);
	static FakerUtil fu = new FakerUtil(driver);
	public static String firstName = fu.firstName();
	public static String lastName = fu.lastName();
	public static String dateofbirth = "09/08/1994";
	public static String idNumber = fu.idNumber();
	public static String nameFirst = fu.firstName();
	public static String emailAddress = fu.emailAddress();
	public static String mobileNumber = fu.phoneNumber();

	private static Logger log = LogManager.getLogger(APACPayReviewSummaryTest.class);

	 @Description("DINA-95_1 Verify that Total Number of Travelers can not be updated to more than 100")
     @Test(dataProvider = "getData")
     public void validateUpdatingTheNoOfTravellers_DINA95_1(String tripType,String travelTo,String travelWithAnnual,String genderType,String idCardType, String codeType, String travelWith, String travellers) throws Exception {
             aPACQuotePage = new APACQuotePage(driver);
             aPACQuotePlanPage = new APACQuotePlanPage(driver);
             aPACPayPage = new APACPayPage(driver);
             aPACPayReviewSummaryPage = new APACPayReviewSummaryPage(driver);
             aPACQuotePlanPage = aPACQuotePage.enterQuoteAnnualTripDetails(tripType, travelTo, travelWithAnnual, departureDate);
             aPACQuotePolicyHolderPage = aPACQuotePlanPage.clickSelectPlanAnnualDomestic();
             aPACPayPage = aPACQuotePolicyHolderPage.iamTravellingAsYes(firstName, lastName, dateofbirth, idNumber, emailAddress, emailAddress,mobileNumber,genderType,idCardType,codeType );                                        
             aPACPayReviewSummaryPage.selectEditStep1AndEditDetails();
             aPACQuotePlanPage = aPACQuotePage.enterQuoteAnnualDetails(travelWith, travellers);
             Assert.assertEquals(aPACQuotePage.getTravellersError(), true);        
     }
	 
     @Description("DINA-95_2 Validate the behavior of \"Edit step 1\" button")
     @Test(dataProvider = "getData")
     public void validateEditStep1InReviewSummary_DINA95_2(String tripType, String travelTo, String travelWithAnnual,String genderType, String idCardType, String codeType) throws Exception {
             aPACQuotePage = new APACQuotePage(driver);
             aPACQuotePlanPage = new APACQuotePlanPage(driver);
             aPACPayPage = new APACPayPage(driver);
             aPACQuotePlanPage = aPACQuotePage.enterQuoteAnnualTripDetails(tripType, travelTo, travelWithAnnual, departureDate);
             aPACQuotePolicyHolderPage = aPACQuotePlanPage.clickSelectPlanAnnualDomestic();
             aPACPayPage = aPACQuotePolicyHolderPage.iamTravellingAsYes(firstName, lastName, dateofbirth, idNumber, emailAddress, emailAddress,mobileNumber,genderType,idCardType,codeType );                                        
             aPACPayReviewSummaryPage.selectEditStep1AndEditDetails();
             aPACQuotePlanPage = aPACQuotePage.futureDateAnnualTrip(futureDepartureDate);
             aPACQuotePlanPage.clickNextButton();
             aPACQuotePolicyHolderPage.clickNext();
             aPACPayReviewSummaryPage.selectEditStep1EditDetails();
             Assert.assertEquals(aPACPayReviewSummaryPage.dateValidate(),futureDepartureDate);
     }
     
     @Description("DINA-95_3 Validate the behavior of \"Edit step 2\" button")
     @Test(dataProvider = "getData")
     public void validateEditStep2InReviewSummary_DINA95_3(String tripType, String travelTo, String travelWithAnnual, String genderType, String idCardType, String codeType) throws Exception {
             aPACQuotePage = new APACQuotePage(driver);
             aPACQuotePlanPage = new APACQuotePlanPage(driver);
             aPACPayPage = new APACPayPage(driver);
             aPACQuotePlanPage = aPACQuotePage.enterQuoteAnnualTripDetails(tripType, travelTo, travelWithAnnual, departureDate);
             aPACQuotePolicyHolderPage = aPACQuotePlanPage.clickSelectPlanAnnualDomestic();
             aPACPayPage = aPACQuotePolicyHolderPage.iamTravellingAsYes(firstName, lastName, dateofbirth, idNumber, emailAddress, emailAddress, mobileNumber,codeType,genderType,idCardType );                                        
             aPACPayReviewSummaryPage.selectEditStep2AndEditDetails();
             aPACQuotePolicyHolderPage = aPACQuotePlanPage.clickSelectPlanAnnualDomesticGold();
             aPACQuotePolicyHolderPage.clickNext();
             aPACPayReviewSummaryPage.selectEditStep2EditDetails();        
             Assert.assertEquals(aPACPayReviewSummaryPage.verifyPlanAfterEdit(),"Gold");
     }
     
     @Description("DINA-95_4 Validate the behavior of \"Edit step 3\" button")
     @Test(dataProvider = "getData")
     public void validateEditStep3InReviewSummary_DINA95_4(String tripType, String travelTo, String travelWithAnnual,String genderType, String idCardType, String codeType, String genderNew) throws Exception {
             aPACQuotePage = new APACQuotePage(driver);
             aPACQuotePlanPage = new APACQuotePlanPage(driver);
             aPACPayPage = new APACPayPage(driver);
             aPACQuotePlanPage = aPACQuotePage.enterQuoteAnnualTripDetails(tripType, travelTo, travelWithAnnual, departureDate);
             aPACQuotePolicyHolderPage = aPACQuotePlanPage.clickSelectPlanAnnualDomestic();
             aPACPayPage = aPACQuotePolicyHolderPage.iamTravellingAsYes(firstName, lastName, dateofbirth, idNumber, emailAddress, emailAddress, mobileNumber,codeType,genderType,idCardType );                                        
             aPACPayReviewSummaryPage.selectEditStep3AndEditDetails();
             aPACPayPage = aPACQuotePolicyHolderPage.iamTravelling(genderNew);
             aPACPayReviewSummaryPage.selectEditStep3EditDetails();
             Assert.assertEquals(aPACPayReviewSummaryPage.verifyStep3EditDetails(),"Female");
     }
     
     @Description("Validate Customer is able to view his/her quote details in screen 4")
     @Test(dataProvider = "getData")
     public void validateScreen4_DINA95_8(String tripType, String travelTo, String travelWithAnnual,String codeType, String genderType, String idCardType) throws Exception {
             aPACQuotePage = new APACQuotePage(driver);
             aPACQuotePlanPage = new APACQuotePlanPage(driver);
             aPACPayPage = new APACPayPage(driver);
             aPACQuotePlanPage = aPACQuotePage.enterQuoteAnnualTripDetails(tripType, travelTo, travelWithAnnual, departureDate);
             aPACQuotePolicyHolderPage = aPACQuotePlanPage.clickSelectPlanAnnualDomestic();
             aPACPayPage = aPACQuotePolicyHolderPage.iamTravellingAsYes(firstName, lastName, dateofbirth, idNumber, emailAddress, emailAddress, mobileNumber,codeType,genderType,idCardType );                                        
             Assert.assertTrue(aPACPayReviewSummaryPage.verifyPremiumText());
             Assert.assertTrue(aPACPayReviewSummaryPage.verifyPayWithText());
             Assert.assertTrue(aPACPayReviewSummaryPage.verifyTermsText());
             Assert.assertTrue(aPACPayReviewSummaryPage.verifyAmountPremiumText());
             Assert.assertTrue(aPACPayReviewSummaryPage.verifyReviewBtnEnabled());
     }
     
     @Description("DINA-96_1 Verify the  of various fields available on Quote Summary Screen for Single Trip.")
     @Test(dataProvider = "getData")
     public void validateSingleTripDetailsInQuoteSummary_DINA96_1(String tripType, String travelWith, String country, String codeType, String genderType, String idCardType) throws Exception {
             aPACQuotePage = new APACQuotePage(driver);
             aPACQuotePlanPage = new APACQuotePlanPage(driver);
             aPACPayPage = new APACPayPage(driver);
             aPACQuotePlanPage = aPACQuotePage.enterQuoteSingleTripDetails(tripType, travelWith, country,departureDate,returnDate);
             aPACQuotePolicyHolderPage = aPACQuotePlanPage.clickselectPlan();
             aPACPayPage = aPACQuotePolicyHolderPage.iamTravellingAsYes(firstName, lastName, dateofbirth, idNumber, emailAddress, emailAddress, mobileNumber,codeType,genderType,idCardType );                                        
             aPACPayReviewSummaryPage.quoteSummary();
             Assert.assertEquals(aPACPayReviewSummaryPage.verifyTravelWithDetails(),"Just me");
             Assert.assertEquals(aPACPayReviewSummaryPage.verifyTravellingPlanDetails(),"Executive");        
             Assert.assertEquals(aPACPayReviewSummaryPage.verifyStep3EditDetails(),"Female");
     }
     
     @Description("DINA-96_2 Verify the  of various fields available on Quote Summary Screen for Oneway Trip.")
     @Test(dataProvider = "getData")
     public void validateOnewayTripDetailsInQuoteSummary_DINA96_2(String tripType, String travelWith, String country, String codeType, String genderType, String idCardType) throws Exception {
             aPACQuotePage = new APACQuotePage(driver);
             aPACQuotePlanPage = new APACQuotePlanPage(driver);
             aPACPayPage = new APACPayPage(driver);
             aPACQuotePlanPage = aPACQuotePage.enterQuoteOnewayTripDetails(tripType, travelWith, country,departureDate);
             aPACQuotePolicyHolderPage = aPACQuotePlanPage.clickselectPlan();
             aPACPayPage = aPACQuotePolicyHolderPage.iamTravellingAsYes(firstName, lastName, dateofbirth, idNumber, emailAddress, emailAddress, mobileNumber,codeType,genderType,idCardType );                                        
             aPACPayReviewSummaryPage.quoteSummary();
             Assert.assertEquals(aPACPayReviewSummaryPage.verifyTravelWithDetails(),"Just me");
             Assert.assertEquals(aPACPayReviewSummaryPage.verifyTravellingPlanDetails(),"Executive");        
             Assert.assertEquals(aPACPayReviewSummaryPage.verifyStep3EditDetails(),"Female");
     }
     
     @Description("DINA-96_3 Verify the  of various fields available on Quote Summary Screen for Annual Trip.")
     @Test(dataProvider = "getData")
     public void validateAnnualTripDetailsInQuoteSummary_DINA96_3(String tripType, String travelTo, String travelWithAnnual, String codeType, String genderType, String idCardType) throws Exception {
             aPACQuotePage = new APACQuotePage(driver);
             aPACQuotePlanPage = new APACQuotePlanPage(driver);
             aPACPayPage = new APACPayPage(driver);
             aPACQuotePlanPage = aPACQuotePage.enterQuoteAnnualTripDetails(tripType, travelTo, travelWithAnnual, departureDate);
             aPACQuotePolicyHolderPage = aPACQuotePlanPage.clickSelectPlanAnnualDomesticGold();
             aPACPayPage = aPACQuotePolicyHolderPage.iamTravellingAsYes(firstName, lastName, dateofbirth, idNumber, emailAddress, emailAddress, mobileNumber,codeType,genderType,idCardType );                                        
             aPACPayReviewSummaryPage.quoteSummary();
             Assert.assertEquals(aPACPayReviewSummaryPage.verifyTravelWithDetails(),"Just me");
             Assert.assertEquals(aPACPayReviewSummaryPage.verifyPlanAfterEdit(),"Gold");        
             Assert.assertEquals(aPACPayReviewSummaryPage.verifyStep3EditDetails(),"Female");                
     }
     
     @Description("DINA-96_6 Verify that Data should be in Protected Mode.")
     @Test(dataProvider = "getData")
     public void validateDataIsNonEditableInQuoteSummary_DINA96_6(String tripType, String travelTo, String travelWithAnnual, String codeType, String genderType, String idCardType) throws Exception {
             aPACQuotePage = new APACQuotePage(driver);
             aPACQuotePlanPage = new APACQuotePlanPage(driver);
             aPACPayPage = new APACPayPage(driver);
             aPACQuotePlanPage = aPACQuotePage.enterQuoteAnnualTripDetails(tripType, travelTo, travelWithAnnual, departureDate);
             aPACQuotePolicyHolderPage = aPACQuotePlanPage.clickSelectPlanAnnualDomesticGold();
             aPACPayPage = aPACQuotePolicyHolderPage.iamTravellingAsYes(firstName, lastName, dateofbirth, idNumber, emailAddress, emailAddress, mobileNumber,codeType,genderType,idCardType );                                        
             aPACPayReviewSummaryPage.quoteSummary();
             Assert.assertFalse(aPACPayReviewSummaryPage.verifyTravellingWithEditable());
     }
     
     @Description("DINA-96_7 Verify the behaviour of  Terms & Conditions checkbox.")
     @Test(dataProvider = "getData")
     public void validateCheckboxandPayButton_DINA96_7(String tripType, String travelWith, String country,String codeType, String genderType, String idCardType,String payMethod) throws Exception {
             aPACQuotePage = new APACQuotePage(driver);
             aPACQuotePlanPage = new APACQuotePlanPage(driver);
             aPACPayPage = new APACPayPage(driver);
             aPACQuotePlanPage = aPACQuotePage.enterQuoteOnewayTripDetails(tripType, travelWith, country, departureDate);
             aPACQuotePolicyHolderPage = aPACQuotePlanPage.clickselectPlan();                
             aPACPayPage = aPACQuotePolicyHolderPage.iamTravellingAsYes(firstName, lastName, dateofbirth, idNumber, emailAddress, emailAddress, mobileNumber,codeType,genderType,idCardType );
             aPACPayPage.paymentPageDetails(payMethod);
             Assert.assertTrue(aPACPayPage.verifyPayBtnEnabled());
     }
     
     @Description("DINA-96_8 Verify the quote summary open and close")
     @Test(dataProvider = "getData")
     public void validateOpenAndCloseInQuoteSummary_DINA96_8(String tripType, String travelWith, String country, String codeType, String genderType, String idCardType, String payMethod) throws Exception {
             aPACQuotePage = new APACQuotePage(driver);
             aPACQuotePlanPage = new APACQuotePlanPage(driver);
             aPACPayPage = new APACPayPage(driver);
             aPACQuotePlanPage = aPACQuotePage.enterQuoteSingleTripDetails(tripType, travelWith, country,departureDate,returnDate);
             aPACQuotePolicyHolderPage = aPACQuotePlanPage.clickselectPlan();
             aPACPayPage = aPACQuotePolicyHolderPage.iamTravellingAsYes(firstName, lastName, dateofbirth, idNumber, emailAddress, emailAddress, mobileNumber,codeType,genderType,idCardType );                                        
             aPACPayReviewSummaryPage.quoteSummary();
             aPACPayReviewSummaryPage.closeSummary();
             aPACPayReviewSummaryPage.paymentPageDetails(payMethod);
             Assert.assertTrue(aPACPayPage.verifyPayBtnEnabled());
     }
     
     @Description("DINA-6_6 Validate that Customer is able to delete  the Promocode using \"Edit step 1\" button.")
     @Test(dataProvider = "getData")
     public void validatePromocodeUsingEditDetailsInQuoteSummary_DINA6_6(String tripType, String travelWith, String country,String promocode, String codeType, String genderType, String idCardType, String payMethod) throws Exception {
             aPACQuotePage = new APACQuotePage(driver);
             aPACQuotePlanPage = new APACQuotePlanPage(driver);
             aPACPayPage = new APACPayPage(driver);
             aPACQuotePlanPage = aPACQuotePage.enterPromocode(tripType, travelWith, country,promocode,departureDate);
             aPACQuotePolicyHolderPage = aPACQuotePlanPage.clickselectPlan();
             aPACPayPage = aPACQuotePolicyHolderPage.iamTravellingAsYes(firstName, lastName, dateofbirth, idNumber, emailAddress, emailAddress, mobileNumber,codeType,genderType,idCardType );                                        
             aPACPayReviewSummaryPage.selectEditStep1AndEditDetails();
             aPACQuotePage.delPromocode();
             aPACQuotePage.clickQuotePlan();
             Assert.assertEquals(aPACQuotePlanPage.planPage(),"Choose and personalize my plan");
     }
     
     @Description("DINA-1278_1 Verify download the personal product summary")
     @Test(dataProvider = "getData")
     public void validateDownloadPersonalSummary_DINA1278_1(String tripType, String travelWith, String country,String codeType, String genderType, String idCardType) throws Exception {
             aPACQuotePage = new APACQuotePage(driver);
             aPACQuotePlanPage = new APACQuotePlanPage(driver);
             aPACPayPage = new APACPayPage(driver);
             aPACQuotePlanPage = aPACQuotePage.enterQuoteOnewayTripDetails(tripType, travelWith, country, departureDate);
             aPACQuotePolicyHolderPage = aPACQuotePlanPage.clickselectPlan();                
             aPACPayPage = aPACQuotePolicyHolderPage.iamTravellingAsYes(firstName, lastName, dateofbirth, idNumber, emailAddress, emailAddress, mobileNumber,codeType,genderType,idCardType );
             aPACPayReviewSummaryPage.quoteSummary();
             aPACPayReviewSummaryPage.downloadProductSummary();
     }
     
     @Description("DINA-1242_2 Verify the behaviour of Progress Bar Icons from Step 1")
     @Test(dataProvider = "getData")
     public void validateProgressBarIcons_DINA1242_2(String tripType, String travelWith, String country, String noOfTravellers, String planCardsList, String selectedPlan) throws InterruptedException {
             aPACQuotePage = new APACQuotePage(driver);        
             aPACQuotePlanPage = new APACQuotePlanPage(driver);
             aPACQuotePage.quoteDetailsForSingleTrip(tripType,travelWith,country,noOfTravellers,departureDate,returnDate);
             aPACQuotePage.verifyProgressQuoteBar() ;
     }
     
     @Description("DINA-1242_3 Verify the behaviour of Progress Bar Icons from Step 2")
     @Test(dataProvider = "getData")
     public void validateProgressBarIcons_DINA1242_3(String tripType, String travelWith, String country, String noOfTravellers, String planCardsList, String selectedPlan) throws InterruptedException {
             aPACQuotePage = new APACQuotePage(driver);   
             aPACQuotePlanPage = new APACQuotePlanPage(driver);
             aPACQuotePage.quoteDetailsForSingleTrip(tripType,travelWith,country,noOfTravellers,departureDate,returnDate);
             aPACQuotePlanPage = aPACQuotePage.clickQuotePlan();
             aPACQuotePlanPage.verifyProgressQuoteBar() ;
     }
     
     @Description("DINA-1242_4 Verify the Behaviour of Progress Bar Icon when data is updated on 1st screen.")
     @Test(dataProvider = "getData")
     public void validateProgressBarIcons_DINA1242_4(String tripType, String travelWith, String country,String codeType, String genderType, String idCardType, String travel) throws Exception {
             aPACQuotePage = new APACQuotePage(driver);        
             aPACQuotePlanPage = aPACQuotePage.enterOnewayQuoteTravelDetails(tripType, travelWith, country, departureDate);
             aPACQuotePage.clickQuotePlan();
             aPACPayReviewSummaryPage.progressBar1();
             aPACQuotePage.updateScreen1(travel);
             aPACPayReviewSummaryPage.progressBar2();
             Assert.assertFalse(aPACPayReviewSummaryPage.verifyProgressBar3());
             Assert.assertFalse(aPACPayReviewSummaryPage.verifyProgressBar4());
     }
     
     @Description("DINA-1242_5 Verify the Behaviour of Progress Bar Icon when data is not updated on 1st screen.")
     @Test(dataProvider = "getData")
     public void validateProgressBarIcons_DINA1242_5(String tripType, String travelWith, String country,String codeType, String genderType, String idCardType, String travel) throws Exception {
             aPACQuotePage = new APACQuotePage(driver);        
             aPACQuotePlanPage = aPACQuotePage.enterOnewayQuoteTravelDetails(tripType, travelWith, country, departureDate);
             aPACQuotePage.clickQuotePlan();
             aPACPayReviewSummaryPage.progressBar1();
             aPACPayReviewSummaryPage.progressBar2();
             Assert.assertFalse(aPACPayReviewSummaryPage.verifyProgressBar2());
             Assert.assertFalse(aPACPayReviewSummaryPage.verifyProgressBar3());
             Assert.assertFalse(aPACPayReviewSummaryPage.verifyProgressBar4());
     }
     @Description("DINA-1242_6 Verify the behaviour of Progress Bar Icons from Step 3")
     @Test(dataProvider = "getData")
     public void validateProgressBarIcons_DINA1242_6(String tripType, String travelWith, String country,String codeType, String genderType, String idCardType) throws Exception {
             aPACQuotePage = new APACQuotePage(driver);        
             aPACQuotePlanPage = aPACQuotePage.enterOnewayQuoteTravelDetails(tripType, travelWith, country, departureDate);
             aPACQuotePage.clickQuotePlan();
             aPACQuotePlanPage.clickselectPlan();
             Assert.assertTrue(aPACPayReviewSummaryPage.verifyProgressBar1());
             Assert.assertTrue(aPACPayReviewSummaryPage.verifyProgressBar2());
             Assert.assertFalse(aPACPayReviewSummaryPage.verifyProgressBar3());
             Assert.assertFalse(aPACPayReviewSummaryPage.verifyProgressBar4());
     }
     
     @Description("DINA-1242_7 Verify the Behaviour of Progress Bar Icon on screen 3 when data is updated on on Previous Screens.")
     @Test(dataProvider = "getData")
     public void validateProgressBarIcons_DINA1242_7(String tripType, String travelWith, String country,String codeType, String genderType, String idCardType, String travel) throws Exception {
             aPACQuotePage = new APACQuotePage(driver);        
             aPACQuotePlanPage = aPACQuotePage.enterOnewayQuoteTravelDetails(tripType, travelWith, country, departureDate);
             aPACQuotePage.clickQuotePlan();
             aPACQuotePlanPage.clickselectPlan();
             aPACPayReviewSummaryPage.progressBar1();
             aPACQuotePage.updateScreen1(travel);
             aPACPayReviewSummaryPage.progressBar3();
             Assert.assertTrue(aPACPayReviewSummaryPage.verifyProgressBar1());
             Assert.assertTrue(aPACPayReviewSummaryPage.verifyProgressBar2());
             Assert.assertTrue(aPACPayReviewSummaryPage.verifyProgressBar3());
             Assert.assertFalse(aPACPayReviewSummaryPage.verifyProgressBar4());
     }
     
     @Description("DINA-1242_8 Verify the Behaviour of Progress Bar Icon on screen 3 when data is not updated onon Previous Screens.")
     @Test(dataProvider = "getData")
     public void validateProgressBarIcons_DINA1242_8(String tripType, String travelWith, String country,String codeType, String genderType, String idCardType, String travel) throws Exception {
             aPACQuotePage = new APACQuotePage(driver);        
             aPACQuotePlanPage = aPACQuotePage.enterOnewayQuoteTravelDetails(tripType, travelWith, country, departureDate);
             aPACQuotePage.clickQuotePlan();
             aPACQuotePlanPage.clickselectPlan();
             aPACPayReviewSummaryPage.progressBar1();
             aPACPayReviewSummaryPage.progressBar3();
             Assert.assertTrue(aPACPayReviewSummaryPage.verifyProgressBar1());
             Assert.assertTrue(aPACPayReviewSummaryPage.verifyProgressBar2());
             Assert.assertTrue(aPACPayReviewSummaryPage.verifyProgressBar3());
             Assert.assertFalse(aPACPayReviewSummaryPage.verifyProgressBar4());
     }
     
     @Description("DINA-1242_9 Verify the behaviour of Progress Bar Icons from Step 4")
     @Test(dataProvider = "getData")
     public void validateProgressBarIcons_DINA1242_9(String tripType, String travelWith, String country,String codeType, String genderType, String idCardType) throws Exception {
             aPACQuotePage = new APACQuotePage(driver);        
             aPACQuotePlanPage = aPACQuotePage.enterOnewayQuoteTravelDetails(tripType, travelWith, country, departureDate);
             aPACQuotePage.clickQuotePlan();
             aPACQuotePlanPage.clickselectPlan();
             aPACPayPage = aPACQuotePolicyHolderPage.iamTravellingAsYes(firstName, lastName, dateofbirth, idNumber, emailAddress, emailAddress, mobileNumber,codeType,genderType,idCardType );                                        
             Assert.assertTrue(aPACPayReviewSummaryPage.verifyProgressBar1());
             Assert.assertTrue(aPACPayReviewSummaryPage.verifyProgressBar2());
             Assert.assertTrue(aPACPayReviewSummaryPage.verifyProgressBar3());
             Assert.assertFalse(aPACPayReviewSummaryPage.verifyProgressBar4());
     }
     
     @Description("DINA-1242_10 Verify the Behaviour of Progress Bar Icon on screen 4 when data is updated on Previous Screens.")
     @Test(dataProvider = "getData")
     public void validateProgressBarIcons_DINA1242_10(String tripType, String travelWith, String country,String codeType, String genderType, String idCardType, String travel) throws Exception {
             aPACQuotePage = new APACQuotePage(driver);        
             aPACQuotePlanPage = aPACQuotePage.enterOnewayQuoteTravelDetails(tripType, travelWith, country, departureDate);
             aPACQuotePage.clickQuotePlan();
             aPACQuotePlanPage.clickselectPlan();
             aPACPayPage = aPACQuotePolicyHolderPage.iamTravellingAsYes(firstName, lastName, dateofbirth, idNumber, emailAddress, emailAddress, mobileNumber,codeType,genderType,idCardType );                                        
             aPACPayReviewSummaryPage.progressBar1();
             aPACQuotePage.updateScreen1(travel);
             aPACPayReviewSummaryPage.progressBar4();
             Assert.assertTrue(aPACPayReviewSummaryPage.verifyProgressBar1());
             Assert.assertTrue(aPACPayReviewSummaryPage.verifyProgressBar2());
             Assert.assertTrue(aPACPayReviewSummaryPage.verifyProgressBar3());
             Assert.assertFalse(aPACPayReviewSummaryPage.verifyProgressBar4());
     }
     
     @Description("DINA-1242_11 Verify the Behaviour of Progress Bar Icon on screen 4 when data is not updated on on Previous Screens.")
     @Test(dataProvider = "getData")
     public void validateProgressBarIcons_DINA1242_11(String tripType, String travelWith, String country,String codeType, String genderType, String idCardType, String travel) throws Exception {
             aPACQuotePage = new APACQuotePage(driver);        
             aPACQuotePlanPage = aPACQuotePage.enterOnewayQuoteTravelDetails(tripType, travelWith, country, departureDate);
             aPACQuotePage.clickQuotePlan();
             aPACQuotePlanPage.clickselectPlan();
             aPACPayPage = aPACQuotePolicyHolderPage.iamTrvellingAsYes(firstName, lastName, dateofbirth, idNumber, emailAddress, emailAddress, mobileNumber,codeType,genderType,idCardType );                                        
             aPACPayReviewSummaryPage.progressBar1();
             aPACPayReviewSummaryPage.progressBar4();
             Assert.assertTrue(aPACPayReviewSummaryPage.verifyProgressBar1());
             Assert.assertTrue(aPACPayReviewSummaryPage.verifyProgressBar2());
             Assert.assertTrue(aPACPayReviewSummaryPage.verifyProgressBar3());
             Assert.assertFalse(aPACPayReviewSummaryPage.verifyProgressBar4());
     }
     
     @DataProvider(name = "getData")
     public Object[][] getData(ITestContext context) throws Exception {
             Object[][] testObjectArrray = ExcelUtil.getData(context);
             return testObjectArrray;
     }

}
