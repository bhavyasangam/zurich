package com.zurich.qa.digitalnative.utils;

import java.util.Locale;

import org.openqa.selenium.WebDriver;

import com.github.javafaker.Faker;

public class FakerUtil {
	private WebDriver driver;

	public FakerUtil(WebDriver driver) {
		this.driver = driver;
	}
	
	Faker faker = new Faker(new Locale("en-IND"));

	public String firstName() {
		String firstName = faker.name().firstName();
		return firstName;
	} 

	public String lastName() {
		String lastName = faker.name().lastName();
		return lastName;
	} 

	public String phoneNumber() {
		String phoneNum = faker.number().digits(10);
		return phoneNum;
	}

	public String idNumber() {
		String idNum = faker.number().digits(12);
		return idNum;
	}

	public String emailAddress() {
		String emailAdd = faker.internet().emailAddress();
		return emailAdd;
	}

	public String confirmEmailAddress() {
		String emailAdd = faker.internet().emailAddress();
		return emailAdd;
	}
}
