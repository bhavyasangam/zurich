package com.zurich.qa.digitalnative.testcases;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.zurich.qa.digitalnative.base.TestBase;
import com.zurich.qa.digitalnative.listeners.TestAllureListener;
import com.zurich.qa.digitalnative.pages.APACLoginPage;
import com.zurich.qa.digitalnative.pages.APACPortfolioPage;
import com.zurich.qa.digitalnative.utils.JavaScriptUtil;

import io.qameta.allure.Description;

@Listeners(TestAllureListener.class)
public class APACPortfolioTest extends TestBase {

	APACPortfolioPage aPACPortfolioPage;
	APACLoginPage aPACLoginPage;
	
	JavaScriptUtil js = new JavaScriptUtil(driver);
	private static Logger log = LogManager.getLogger(APACPortfolioTest.class);

	@Description("DINA-325 Verify the customer is able to see all the policies mapped to the account")
	@Test
	public void verifyAllPolicies_DINA_325_1() throws Exception {
		aPACPortfolioPage = new APACPortfolioPage(driver);
		aPACLoginPage	= new	APACLoginPage(driver);
		aPACLoginPage.signinWithEmail("zurichfeb15@gmail.com");
		aPACPortfolioPage = aPACLoginPage.clickVerifyCode();
		Assert.assertTrue("Policy screen less than 5 active policies should be displayed", aPACPortfolioPage.verifyAccountPolicies());
	}
	
	@Description("DINA-325 Verify the customer is able to see all the policies mapped to the account")
	@Test
	public void verifyPoliciesDetails_DINA_325_2() throws Exception {
		aPACPortfolioPage = new APACPortfolioPage(driver);
		aPACLoginPage	= new	APACLoginPage(driver);
		aPACLoginPage.signinWithEmail("zurichfeb15@gmail.com");
		aPACPortfolioPage = aPACLoginPage.clickVerifyCode();
		aPACPortfolioPage.verifyPolicyViewDetails();
	}
	
	@Description("DINA-325 Verify the customer is able to click on the view or edit policies")
	@Test
	public void verifyViewAllPolicies_DINA_325_3() throws Exception {
		aPACPortfolioPage = new APACPortfolioPage(driver);
		aPACLoginPage	= new	APACLoginPage(driver);
		aPACLoginPage.signinWithEmail("zurichfeb15@gmail.com");
		aPACPortfolioPage = aPACLoginPage.clickVerifyCode();
		aPACPortfolioPage.verifyViewAllPolicies();
	}
	
	@Description("DINA-325 Verify overall policies in the page")
	@Test
	public void verifyAllPolicies_DINA_325_4() throws Exception {
		aPACPortfolioPage = new APACPortfolioPage(driver);
		aPACLoginPage	= new	APACLoginPage(driver);
		aPACLoginPage.signinWithEmail("zurichfeb15@gmail.com");
		aPACPortfolioPage = aPACLoginPage.clickVerifyCode();
		Assert.assertTrue("Policy screen less than 5 active policies should be displayed", aPACPortfolioPage.verifyAccountPolicies());
	}
}