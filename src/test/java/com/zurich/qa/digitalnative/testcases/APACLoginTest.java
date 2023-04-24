package com.zurich.qa.digitalnative.testcases;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.testng.annotations.Test;

import com.zurich.qa.digitalnative.base.TestBase;
import com.zurich.qa.digitalnative.pages.APACLoginPage;
import com.zurich.qa.digitalnative.pages.APACPortfolioPage;
import com.zurich.qa.digitalnative.utils.JavaScriptUtil;

import io.qameta.allure.Description;

public class APACLoginTest extends TestBase {

	APACPortfolioPage aPACPortfolioPage;
	APACLoginPage aAPACLoginPage;
	JavaScriptUtil js = new JavaScriptUtil(driver);
	private static Logger log = LogManager.getLogger(APACLoginTest.class);

	@Description("Verify that the customer should able to Logout.")
	@Test
	public void validateLogoutScreen_DINA16_1() throws Exception {
		aPACPortfolioPage = new APACPortfolioPage(driver);
		aPACPortfolioPage.logout();
		Assert.assertTrue(aAPACLoginPage.verifyLoginMessageDisplayed());
	}
	
	@Description("Verify that After logout customer is not able to login using Back Button.")
	@Test
	public void validateLogoutScreen_DINA16_2() throws Exception {
		aPACPortfolioPage = new APACPortfolioPage(driver);
		aPACPortfolioPage.logout();
		driver.navigate().back();
		Assert.assertTrue(aAPACLoginPage.verifyLoginMessageDisplayed());
	}
}