package com.twitter.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TwitterUtils {
	
	public static final String[] usersToTrack = {"BarackObama", "twitter"};
	
	
	public TwitterUtils() {
		
	}
	
	public static String[] getUsersToTrack() {
		return usersToTrack;
	}
	
	
	public static Date getStartDate() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String startDateString = "2016-09-01";
		try {
			return sdf.parse(startDateString);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	public static Date getEndDate() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String endDateString = "2016-10-01";
		try {
			return sdf.parse(endDateString);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	
	


}
