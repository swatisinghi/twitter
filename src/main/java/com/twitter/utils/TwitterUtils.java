package com.twitter.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TwitterUtils {
	
	public static final String[] usersToTrack = {"Katyperry","justinbieber","taylorswift13","BarackObama","rihanna","YouTube",
		"ladygaga", "TheEllenShow", "twitter","jtimberlake","KimKardashian","britneyspears","Cristiano","selenagomez","jimmyfallon",
		"ArianaGrande","cnnbrk","instagram","shakira","ddlovato","JLo","Drake","Oprah","KingJames","BillGates","KevinHart4real",
		"MileyCyrus","onedirection","Nytimes","Harry_Styles","SportsCenter","espn","Pink","wizkhalifa","NiallOfficial","Adele",
		"LilTunechi","BrunoMars","kanyewest","ActuallyNPH","neymarjr","KAKA","narendramodi","LiamPayne","NBA","Louis_Tomlinson",
		"Aliciakeys","SrBachchan","CNN","EmmaWatson","BBCBreaking","danieltosh","pitbull","Iamsrk","ConanOBrien","NICKIMINAJ",
		"NFL","zaynmalik","davidguetta","AvrilLavigne","Eminem" ,"BeingSalmanKhan","NASA","KylieJenner","blakeshelton",
		"aamir_khan","khloekardashian","realmadrid","coldplay","FCBarcelona","kourtneykardash","aplusk","vine","edsheeran",
		"KendallJenner","deepikapadukone","google","xtina","MohamadAlarefe","agnezmo","MariahCarey","shugairi","LeoDiCaprio",
		"TwitterEspanol","chrisbrown","JimCarrey","ricky_martin","KDTrey5","ivetesangalo","BBCWorld","iHrithik","priyankachopra",
		"TheEconomist","Reuters","TwitterSports","SnoopDogg","Beyonce","RyanSeacrest","AlejandroSanz","radityadika"};
	
	
	public static String[] getUsersToTrack() {
		return usersToTrack;
	}
	
	
	public static Date getStartDate() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String startDateString = "2016-11-01";
		try {
			return sdf.parse(startDateString);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static Date getEndDate() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String endDateString = "2016-12-01";
		try {
			return sdf.parse(endDateString);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}
}
