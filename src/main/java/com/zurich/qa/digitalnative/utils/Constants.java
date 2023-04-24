package com.zurich.qa.digitalnative.utils;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Constants {

	//Quote Screen1 Constants
	public static final String SINGLE_TRIP = "Round Trip";
	public static final String ONEWAY_TRIP = "One-way trip";
	public static final String ANNUAL_TRIP = "Annual travel";
	
	public static final String JUST_ME = "Just me";
	public static final String DUO = "Me + 1";
	public static final String MY_FAMILY = "My family";
	public static final String GROUP = "A group";
	
	public static final String SENIOR_CITIZEN_TITLE = "Are you 70 years or over?";
	public static final String SENIOR_CITIZEN_DESC = "If you or someone travelling with you is over 70 years old, contact us at Zurich Care ";
	
	//Quote Screen2 Constants
    public static final String Executive =  "EXECUT" ;
    public static final String Basic =  "BASIC" ;
    public static final String Premium =  "PREM";
    public static final String Silver =  "SILVER";
    public static final String Gold =  "GOLD";
	
	//Footer Urls Constants
	public static final String HOME_URL = "https://www.zurich.co.id/en";

	public static final String FACEBOOK_URL = "https://www.facebook.com/ZurichID";
	public static final String INSTAGRAM_URL = "https://www.instagram.com/zurichid/";
	public static final String TWITTER_URL = "https://twitter.com/ZurichIndonesia";
	public static final String YOUTUBE_URL = "https://www.youtube.com/channel/UCaKhmCNNA6pPuY56o3ej4tA";
	public static Map<String, String> FOOTER_LINK = new HashMap<>();
	static {
		FOOTER_LINK.put("Legal","https://www.zurich.co.id/en/services/legal");
		FOOTER_LINK.put("Privacy Policy","https://www.zurich.co.id/en/services/privacy");
		FOOTER_LINK.put("Terms Of Use", "https://www.zurich.co.id/en/services/terms-of-use");
		FOOTER_LINK.put("Contact Us", "https://www.zurich.co.id/en/layanan-nasabah/hubungi-kami");
	}
	
	// convert mutable Map to immutable map
	public static final Map<String,String> FOOTER_LINK_IMMUTABLE = Collections.unmodifiableMap(FOOTER_LINK);
	public static final String QUOTEPAGE_URL = "https://web.digitalnative-sit.zurich.co.id/travel/quote";
	
}
