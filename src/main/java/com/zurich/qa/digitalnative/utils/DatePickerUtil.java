package com.zurich.qa.digitalnative.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class DatePickerUtil {

	private static Logger logger = LogManager.getLogger(DatePickerUtil.class);

	public String currentDate() {
		//DateTimeFormatter curDate = DateTimeFormatter.ofPattern("yyyy/MM/dd");
		DateTimeFormatter curDate = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		LocalDateTime now = null;
		try {                     
			now = LocalDateTime.now();
		} catch (Throwable error) {
			logger.info("Unable to generate current date");
		}
		return curDate.format(now);
	}

	public String futureDate(int noOfDays) {
		DateTimeFormatter curDate = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		LocalDateTime futDate = null;
		try {		
			LocalDateTime now = LocalDateTime.now();
			futDate = now.plusDays(noOfDays);
		} catch (Throwable error) {
			logger.info("Unable to generate future date");
		}
		return  curDate.format(futDate);
	}

	public String futureMonth(int noOfMonths) {
		DateTimeFormatter curDate = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		LocalDateTime prevMonth  = null;
		try {		
			LocalDateTime now = LocalDateTime.now();
			 prevMonth = now.plusMonths(noOfMonths);
		} catch (Throwable error) {
			logger.info("Unable to generate previous date");
		}
		return curDate.format(prevMonth);
	}

	public String previousDate(int noOfDays) {
		DateTimeFormatter curDate = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		LocalDateTime prevDate = null;
		try {		
			LocalDateTime now = LocalDateTime.now();
			prevDate = now.minusDays(noOfDays);
		} catch (Throwable error) {
			logger.info("Unable to generate previous date");
		}
		return curDate.format(prevDate);
	}

	public void previousMonth(int noOfMonths) {
		DateTimeFormatter curDate = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		try {		
			LocalDateTime now = LocalDateTime.now();
			LocalDateTime prevMonth = now.minusMonths(noOfMonths);
		} catch (Throwable error) {
			logger.info("Unable to generate previous date");
		}
		return;
	}
	
	public boolean checkIfPastDate(String givendate) {
		try {
			return LocalDate.parse(givendate, DateTimeFormatter.ofPattern("dd/MM/yyyy"))
					.isBefore(LocalDate.parse(currentDate(), DateTimeFormatter.ofPattern("yyyy/MM/dd")));
		} catch (Throwable error) {
			logger.info("Unable to check past date");
		}
		return false;
	}


}
