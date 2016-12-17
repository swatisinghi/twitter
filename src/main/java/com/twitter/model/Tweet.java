package com.twitter.model;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Tweet {
	
	private long id;
	private String text;
	private Date date;
	private String userName;
	private int retweetCount;

	public Tweet(long id, String text, Date date, String userName, int count) {
		this.id = id;
		this.text = text;
		this.date = date;
		this.userName = userName;
		this.retweetCount = count;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public String toString() {
		Map<String, Object> json = new HashMap<String, Object>();
		json.put("name", this.userName);
		json.put("date", this.date);
		json.put("text", this.text);
		json.put("id", this.id);
		json.put("retweet_count", this.retweetCount);
        return json.toString();
    }
}
