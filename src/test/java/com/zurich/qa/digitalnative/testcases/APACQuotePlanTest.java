package com.zurich.qa.digitalnative.testcases;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.zurich.qa.digitalnative.base.TestBase;
import com.zurich.qa.digitalnative.listeners.TestAllureListener;
import com.zurich.qa.digitalnative.pages.APACQuotePage;
import com.zurich.qa.digitalnative.pages.APACQuotePage;
import com.zurich.qa.digitalnative.pages.APACQuotePlanPage;
import com.zurich.qa.digitalnative.utils.DatePickerUtil;
import com.zurich.qa.digitalnative.utils.ExcelUtil;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Story;

@Epic("DINA 1: As a customer i would like to obtain a quote so that i know the coverage and cost")
@Story("DINA 88: As a customer I want to view  the Available plans so that I can select a suitable plan for me..")
@Story("DINA 89: As a customer I want to view the Mandatory covergaes for my plan so that I can proceed with my selected plan for policy issuance.")

@Listeners(TestAllureListener.class)
public class APACQuotePlanTest extends TestBase {
	APACQuotePlanPage aAPACQuotePlanPage;
	APACQuotePage aAPACQuotePage;
	DatePickerUtil dateUtil = new DatePickerUtil();
	String departureDate = dateUtil.currentDate();
	String futureReturnDate =dateUtil.futureDate(5);
	private static Logger log = LogManager.getLogger(APACQuotePlanTest.class);
	
	@Description("Verify whether customer is able to select each plan and view benefit details of that particular plan ")
	@Test(dataProvider = "getData")
	public void verifyOneWayQuotePlans_DINA88_1(String tripType, String travelWith, String country, String noOfTravellers,
			String planCardsList, String selectedPlan, String benefitCountry, String planPremium, String addOnsList,
			String selectAddOn, String coverages, String expPlanTotalPremium) throws InterruptedException {
		aAPACQuotePage = new APACQuotePage(driver);
		aAPACQuotePlanPage = new APACQuotePlanPage(driver);
		aAPACQuotePlanPage = aAPACQuotePage.quoteDetailsForOneWayTrip(tripType, travelWith, country, noOfTravellers,
				departureDate, futureReturnDate);
		aAPACQuotePlanPage = aAPACQuotePage.clickQuotePlan();
		aAPACQuotePlanPage.validateQuotePlanPage(planCardsList, selectedPlan, benefitCountry, planPremium, addOnsList,
				selectAddOn, coverages, expPlanTotalPremium);
		Assert.assertTrue(aAPACQuotePlanPage.verifyNextBtnEnabled(),"Quote Plan Next button is not enabled");
	}

	@Description("Verify whether customer is able to select each plan and view benefit details of that particular plan ")
	@Test(dataProvider = "getData")
	public void validateRoundTrip_DINA88_1(String tripType, String travelWith, String country, String noOfTravellers,
			String planCardsList, String selectedPlan, String benefitCountry, String planPremium, String addOnsList,
			String selectAddOn, String coverages, String expPlanTotalPremium) throws InterruptedException {
		aAPACQuotePage = new APACQuotePage(driver);
		aAPACQuotePlanPage = new APACQuotePlanPage(driver);

		aAPACQuotePage.quoteDetailsForSingleTrip(tripType, travelWith, country, noOfTravellers, departureDate,
				futureReturnDate);
		aAPACQuotePlanPage = aAPACQuotePage.clickQuotePlan();
		aAPACQuotePlanPage.validateQuotePlanPage(planCardsList, selectedPlan, benefitCountry, planPremium, addOnsList,
				selectAddOn, coverages, expPlanTotalPremium);
		Assert.assertTrue(aAPACQuotePlanPage.verifyNextBtnEnabled(),"Quote Plan Next button is not enabled");
	}
	
	@Description("Verify whether customer is able to select each plan and view benefit details of that particular plan ")
	@Test(dataProvider = "getData")
	public void validateAnnualTravel_DINA88_11(String tripType, String travelWith, String country, String noOfTravellers, String planCardsList, String selectedPlan,String benefitCountry, String planPremium, String addOnsList, String selectAddOn, String coverages, String expPlanTotalPremium) throws InterruptedException {
		aAPACQuotePlanPage = new APACQuotePlanPage(driver);		
		aAPACQuotePage = new APACQuotePage(driver);
		
		aAPACQuotePage.quoteDetailsForAnnual(tripType,travelWith,country,noOfTravellers,departureDate,futureReturnDate);
		
		aAPACQuotePlanPage =  aAPACQuotePage.clickQuotePlan();
		aAPACQuotePlanPage.validateQuotePlanPage(planCardsList,selectedPlan,benefitCountry,planPremium,addOnsList,selectAddOn,coverages,expPlanTotalPremium);

		Assert.assertTrue(aAPACQuotePlanPage.verifyNextBtnEnabled(),"Quote Plan Next button is not enabled");
	}
	
	@Description("DINA-88_2_3_4_6 and DINA-89_1 verify quote plan previous and next progress bar")
	@Test(dataProvider = "getData")
	public void verifyQuotePlanPrevNextDINA88_2(String tripType, String travelWith, String country, String noOfTravellers, String planCardsList, String selectedPlan) throws InterruptedException {
		aAPACQuotePage = new APACQuotePage(driver);
		aAPACQuotePlanPage = new APACQuotePlanPage(driver);
		String countries="Australia,Maldives";
		aAPACQuotePage.quoteDetailsForMultipleCountrySingleTrip(tripType,travelWith,countries,noOfTravellers,departureDate,futureReturnDate);
		Assert.assertTrue(aAPACQuotePlanPage.verifyQuotePlanProgressBar(),"Progress bar is not displayed");
		aAPACQuotePlanPage = aAPACQuotePage.clickQuotePlan();
		aAPACQuotePlanPage.selectPlan(selectedPlan);
		Assert.assertTrue(aAPACQuotePlanPage.verifyInsurancePlanSummary(),"Insurance summary is not displayed");
		aAPACQuotePlanPage.validadeAddOnTitle();
		aAPACQuotePlanPage.clickBackButton();
		Assert.assertTrue(aAPACQuotePlanPage.verifyQuotePlanProgressBar(),"Progress bar is not displayed");
		aAPACQuotePlanPage = aAPACQuotePage.clickQuotePlan();
		aAPACQuotePlanPage.selectPlan(selectedPlan);
		Assert.assertTrue(aAPACQuotePlanPage.verifyNextBtnEnabled(),"Next button is not displayed");
	}
	
	//Defect - DINA-1982
	@Description("DINA-88_3 verify quote plan with mulitple different countries")
	@Test(dataProvider = "getData")
	public void verifyQuoteMultipleCountriesDINA88_3(String tripType, String travelWith, String country, String noOfTravellers, String planCardsList, String selectedPlan) throws InterruptedException {
		aAPACQuotePage = new APACQuotePage(driver);
		aAPACQuotePlanPage = new APACQuotePlanPage(driver);
		
		aAPACQuotePage.quoteDetailsForMultipleCountrySingleTrip(tripType,travelWith,country,noOfTravellers,departureDate,futureReturnDate);
		
		Assert.assertTrue(aAPACQuotePlanPage.verifyQuotePlanProgressBar());
		aAPACQuotePlanPage = aAPACQuotePage.clickQuotePlan();
		Assert.assertTrue(aAPACQuotePlanPage.validatePlanCards(planCardsList),"Quote Plan Plan Cards are not matching");
	}
	
	@Description("DINA-88_3 verify quote plan with mulitple different countries for more than 5 countries")
	@Test(dataProvider = "getData")
	public void verifyQuoteInvalidMultipleCountriesDINA88_3(String tripType, String travelWith, String country, String noOfTravellers, String planCardsList, String selectedPlan) throws InterruptedException {
		aAPACQuotePage = new APACQuotePage(driver);
		aAPACQuotePlanPage = new APACQuotePlanPage(driver);
		String countries="Australia,Czech Republic,France,Hong Kong,Maldives,Russian Federation";
		aAPACQuotePage.quoteDetailsForMultipleCountrySingleTrip(tripType,travelWith,countries,noOfTravellers,departureDate,futureReturnDate);
		
		Assert.assertTrue(aAPACQuotePlanPage.verifyQuotePlanProgressBar());
		aAPACQuotePlanPage = aAPACQuotePage.clickQuotePlan();
		Assert.assertEquals(aAPACQuotePage.verifyCountriesLimitErrrorMsg(),"Selected countries should not exceed 5.","Countries limit error message is not displayed");
	}
	
	//Defect-DINA-2822
	@Description("DINA-90_1 verify quote plan Add ons short description ")
	@Test(dataProvider = "getData")
	public void verifyAddOnsShortDescDINA90_1(String tripType, String travelWith, String country, String noOfTravellers, String planCardsList, String selectedPlan,String benefitCountry, String planPremium, String addOnsList, String selectAddOn, String coverages, String expPlanTotalPremium) throws InterruptedException {
		aAPACQuotePage = new APACQuotePage(driver);
		aAPACQuotePlanPage = new APACQuotePlanPage(driver);
		
		aAPACQuotePage.quoteDetailsForSingleTrip(tripType,travelWith,country,noOfTravellers,departureDate,futureReturnDate);
		
		aAPACQuotePlanPage = aAPACQuotePage.clickQuotePlan();
		Assert.assertTrue(aAPACQuotePlanPage.validatePlanCards(planCardsList),"Quote Plan Plan Cards are not matching");
		
		 aAPACQuotePlanPage.selectPlan(selectedPlan);
		 Assert.assertEquals(planPremium,aAPACQuotePlanPage.planPremium(selectedPlan),"Quote Plan Premium  are not matching");
		 Assert.assertTrue(aAPACQuotePlanPage.validateAddons(addOnsList),"Quote Plan add ons  are not matching");
		 aAPACQuotePlanPage.clickAddOns(selectAddOn);
		 String expShortDes="Protection against visa applications rejected up to 9 million";
		 Assert.assertEquals(aAPACQuotePlanPage.addOnShortDescription(),expShortDes,"Quote Plan add ons short description are not matching");
	}
	
	@Description("DINA-90_2 verify quote plan Add ons read more section")
	@Test(dataProvider = "getData")
	public void verifyAddOnsReadMoreDINA90_2(String tripType, String travelWith, String country, String noOfTravellers, String planCardsList, String selectedPlan,String benefitCountry, String planPremium, String addOnsList, String selectAddOn, String coverages, String expPlanTotalPremium) throws InterruptedException {
		aAPACQuotePage = new APACQuotePage(driver);
		aAPACQuotePlanPage = new APACQuotePlanPage(driver);
		aAPACQuotePage.quoteDetailsForSingleTrip(tripType,travelWith,country,noOfTravellers,departureDate,futureReturnDate);
		
		aAPACQuotePlanPage = aAPACQuotePage.clickQuotePlan();
		Assert.assertTrue(aAPACQuotePlanPage.validatePlanCards(planCardsList),"Quote Plan Plan Cards are not matching");
		
		aAPACQuotePlanPage.selectPlan(selectedPlan);
		Assert.assertEquals(planPremium,aAPACQuotePlanPage.planPremium(selectedPlan),"Quote Plan Premium  are not matching");
		Assert.assertTrue(aAPACQuotePlanPage.validateAddons(addOnsList),"Quote Plan add ons  are not matching");
		aAPACQuotePlanPage.clickAddOns(selectAddOn);
		String readMoreTitle="Protection against visa applications rejected up to 9 million";
		aAPACQuotePlanPage.validateReadMore(readMoreTitle);
	}
	
	@Description("DINA-91_1 verify quote plan selecting and unselecting plan premium update")
	@Test(dataProvider = "getData")
	public void verifySelectUnselectPlanDINA91_1(String tripType, String travelWith, String country, String noOfTravellers, String planCardsList, String selectedPlan,String benefitCountry, String planPremium, String addOnsList, String selectAddOn, String coverages, String expPlanTotalPremium) throws InterruptedException {
		aAPACQuotePage = new APACQuotePage(driver);
		aAPACQuotePlanPage = new APACQuotePlanPage(driver);
		aAPACQuotePage.quoteDetailsForSingleTrip(tripType,travelWith,country,noOfTravellers,departureDate,futureReturnDate);
		
		aAPACQuotePlanPage = aAPACQuotePage.clickQuotePlan();
		Assert.assertTrue(aAPACQuotePlanPage.validatePlanCards(planCardsList),"Quote Plan Plan Cards are not matching");
		aAPACQuotePlanPage.selectPlan(selectedPlan);
		Assert.assertEquals(planPremium,aAPACQuotePlanPage.planPremium(selectedPlan),"Quote Plan Premium  are not matching");
		
		Assert.assertTrue(aAPACQuotePlanPage.validateAddons(addOnsList),"Quote Plan add ons  are not matching");
		aAPACQuotePlanPage.clickAddOns(selectAddOn);
		Assert.assertEquals(aAPACQuotePlanPage.verifyChoosePlanTotalPremium(),"IDR 462,568","Quote Plan total Premium  are not matching");
		aAPACQuotePlanPage.clickAddOns(selectAddOn);
		Assert.assertEquals(aAPACQuotePlanPage.verifyChoosePlanTotalPremium(),"IDR 412,951","Quote Plan total Premium  are not matching");
	}
	
	@Description("DINA-91_2 verify quote plan total premium section and floating footer premium ")
	@Test(dataProvider = "getData")
	public void verifyTotPremSectionDINA91_2(String tripType, String travelWith, String country, String noOfTravellers, String planCardsList, String selectedPlan,String benefitCountry, String planPremium, String addOnsList, String selectAddOn, String coverages, String expPlanTotalPremium) throws InterruptedException {
		aAPACQuotePage = new APACQuotePage(driver);
		aAPACQuotePlanPage = new APACQuotePlanPage(driver);
		
		aAPACQuotePage.quoteDetailsForSingleTrip(tripType,travelWith,country,noOfTravellers,departureDate,futureReturnDate);
		aAPACQuotePlanPage = aAPACQuotePage.clickQuotePlan();
		Assert.assertTrue(aAPACQuotePlanPage.validatePlanCards(planCardsList),"Quote Plan Plan Cards are not matching");
		
		aAPACQuotePlanPage.selectPlan(selectedPlan);
		Assert.assertEquals(planPremium,aAPACQuotePlanPage.planPremium(selectedPlan),"Quote Plan Premium  are not matching");
		Assert.assertTrue(aAPACQuotePlanPage.validateAddons(addOnsList),"Quote Plan add ons  are not matching");
		aAPACQuotePlanPage.clickAddOns(selectAddOn);
		aAPACQuotePlanPage.validateTheTotPremSection(planPremium,expPlanTotalPremium);
	}
	
	@Description("DINA-1579 Travel Return Date for Travel Start with Today's date.")
	@Test(dataProvider = "getData")
	public void verifyStartReturnDateDINA1579_1(String tripType, String travelWith, String country, String noOfTravellers, String planCardsList, String selectedPlan,String benefitCountry, String planPremium, String addOnsList, String selectAddOn, String coverages, String expPlanTotalPremium) throws InterruptedException {
		aAPACQuotePage = new APACQuotePage(driver);
		aAPACQuotePlanPage = new APACQuotePlanPage(driver);
		aAPACQuotePage.selectTravelType(tripType);
		aAPACQuotePage.selectTravelCountry(tripType, country);
		aAPACQuotePage.provideTravellingPartnerDetails(travelWith, noOfTravellers);
		String departureOnDate = dateUtil.currentDate();
		String returnOnDate = dateUtil.futureDate(365);
		aAPACQuotePage.enterTravelDates(tripType, departureOnDate, returnOnDate);
		String actreturnDate=aAPACQuotePage.validateDate("Return");
		Assert.assertEquals(actreturnDate,departureOnDate,"Travel Return Date for Travel Start with Today's date are not matching. ");
	}
	
	@Description("DINA-1579_2 Travel Return Date for Travel Start with Past date.")
	@Test(dataProvider = "getData")
	public void verifyStartReturnDateDINA1579_2(String tripType, String travelWith, String country, String noOfTravellers, String planCardsList, String selectedPlan,String benefitCountry, String planPremium, String addOnsList, String selectAddOn, String coverages, String expPlanTotalPremium) throws InterruptedException {
		aAPACQuotePage = new APACQuotePage(driver);
		aAPACQuotePlanPage = new APACQuotePlanPage(driver);
		aAPACQuotePage.selectTravelType(tripType);
		aAPACQuotePage.selectTravelCountry(tripType, country);
		aAPACQuotePage.provideTravellingPartnerDetails(travelWith, noOfTravellers);
		String departureOnDate = dateUtil.previousDate(2);
		String returnOnDate = dateUtil.futureDate(364);
		aAPACQuotePage.enterTravelDates(tripType, departureOnDate, returnOnDate);
		aAPACQuotePage.disclaimerWindow();
		String actreturnDate=aAPACQuotePage.validateDate("Departing");
		Assert.assertEquals(actreturnDate,departureOnDate,"Travel Return Date for Travel Start with Past date are not matching. ");
	}
	
	@Description("DINA-1579_3 Travel Return Date for Travel Start with Future date.")
	@Test(dataProvider = "getData")
	public void verifyStartReturnDateDINA1579_3(String tripType, String travelWith, String country, String noOfTravellers, String planCardsList, String selectedPlan,String benefitCountry, String planPremium, String addOnsList, String selectAddOn, String coverages, String expPlanTotalPremium) throws InterruptedException {
		aAPACQuotePage = new APACQuotePage(driver);
		aAPACQuotePlanPage = new APACQuotePlanPage(driver);
		aAPACQuotePage.selectTravelType(tripType);
		aAPACQuotePage.selectTravelCountry(tripType, country);
		aAPACQuotePage.provideTravellingPartnerDetails(travelWith, noOfTravellers);
		String departureOnDate = dateUtil.futureMonth(9);
		String returnOnDate = dateUtil.futureDate(364);
		aAPACQuotePage.enterTravelDates(tripType, departureOnDate, returnOnDate);
		String actreturnDate=aAPACQuotePage.validateDate("Departing");
		Assert.assertEquals(actreturnDate,departureDate,"Travel Return Date for Travel Start with Future date are not matching. ");
		}
	
	@Description("DINA-1579_4 Proceed with Quote creation within 365 days of Travel return date.")
	@Test(dataProvider = "getData")
	public void verifyStartReturnDateDINA1579_4(String tripType, String travelWith, String country, String noOfTravellers, String planCardsList, String selectedPlan,String benefitCountry, String planPremium, String addOnsList, String selectAddOn, String coverages, String expPlanTotalPremium) throws InterruptedException {
		aAPACQuotePage = new APACQuotePage(driver);
		aAPACQuotePlanPage = new APACQuotePlanPage(driver);
		aAPACQuotePage.selectTravelType(tripType);
		aAPACQuotePage.selectTravelCountry(tripType, country);
		aAPACQuotePage.provideTravellingPartnerDetails(travelWith, noOfTravellers);
		String departureOnDate = dateUtil.currentDate();
		String returnOnDate = dateUtil.futureDate(364);
		aAPACQuotePage.enterTravelDates(tripType, departureOnDate, returnOnDate);
		String actreturnDate=aAPACQuotePage.validateDate("Return");
		Assert.assertEquals(actreturnDate,returnOnDate,"Travel Return Date for Travel Start with Future date are not matching");
		Assert.assertTrue(aAPACQuotePage.verifyChoosePlanEnabled(),"Quote Choose Plan  button is not enabled");
		}
	
	@DataProvider(name = "getData")
	public Object[][] getData(ITestContext context) throws Exception {
		Object[][] testObjectArrray = ExcelUtil.getData(context);
		return testObjectArrray;
	}

}
