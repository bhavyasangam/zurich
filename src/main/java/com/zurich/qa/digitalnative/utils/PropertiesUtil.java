package com.zurich.qa.digitalnative.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;
import org.testng.Assert;

public class PropertiesUtil {
	private Properties properties;
	private final String propertyFilePath= "src\\main\\resources\\config.properties";

	String myBrowser;
	public String drivers ;
	public String theEnv ;
	public String  runType;
	String  isParallelExe;
	public String username ;
	public String password;
	String baseURL;
	public String fullURL;

	private Logger logger = LogManager.getLogger(PropertiesUtil.class);
	
	/**
	 * @param module
	 * This gets the properties from browserstack.properties which is browserstack config like all the browserstack related and any other properties which are not changing often
	 * This gets the properties from test.properties which is in the module level like the browser, env, baseurl
	 * 
	 */
	public void initProperties(String module) {			
		try {		
			Properties testprop=new Properties();          	  
			FileInputStream intestprop = new FileInputStream(propertyFilePath);	
			testprop.load(intestprop);
			intestprop.close();
			myBrowser = testprop.getProperty("browser");
			logger.info("Browser ::",myBrowser);
			
			runType  = testprop.getProperty("runtype");
			logger.info("execution type cloud or local  :: ",runType);
			
			isParallelExe = testprop.getProperty("isParallel");
			logger.info(" parallel execution :: ",isParallelExe);
			
			if(myBrowser.equals(null)) {
				myBrowser = "Chrome";
			}
			theEnv = testprop.getProperty("env");	
			logger.info("Environment ::",theEnv);
			
			baseURL = testprop.getProperty("baseUrl");
			logger.info("Base URL ::",baseURL);
			
			if((theEnv.equalsIgnoreCase("SITs"))||(theEnv.equalsIgnoreCase("STAGE")))  {
				fullURL = "https://"+theEnv+"."+baseURL;	 
			}else if((theEnv.equalsIgnoreCase("HU"))){
				fullURL = "https://"+theEnv+"-www."+baseURL;	 
			}
			else{
				fullURL = baseURL;
			}
		}
		catch(Exception a){
			logger.info("Exception :: {}", a.toString());
			logger.info("Exception Cause ::{}",a.getCause());
			Assert.fail("failed in the init properties " +a.getMessage());
		}
	}

	/**
	 * This method returns the module name
	 * @param a
	 * @return
	 */
	public String moduleName(Object a){
		System.out.println("The package name is : " + a.getClass().getPackage());
		String moduleName[] =  a.getClass().getPackage().toString().split("\\.");
		System.out.println("The module name is : " +moduleName[moduleName.length-2]);	 
		logger.info("Module Name :: {}", moduleName[moduleName.length-2]);
		return moduleName[moduleName.length-2];
	}
	
}
