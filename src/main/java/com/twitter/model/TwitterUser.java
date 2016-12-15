package com.twitter.model;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.User;

public class TwitterUser {

	private String name;
	private Set<String> verifiedMentions;
	private List<Tweet> tweets;
	
	public TwitterUser(String name) {
		this.name = name;
		this.verifiedMentions = new HashSet<String>();
		this.tweets = new LinkedList<Tweet>();
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public Set<String> getVerifiedMentions() {
		return verifiedMentions;
	}
	
	public void setVerifiedMentions(String name) {
		this.verifiedMentions.add(name);
	}
	
	public void populateVerifiedMentions(String tweet, Twitter twitter) throws TwitterException {
		String pattern = "@[a-zA-Z0-9_]+";
		Pattern r = Pattern.compile(pattern);
		Matcher m = r.matcher(tweet);
	    if (m.find()) {
	    	User mentionedUser = twitter.showUser(m.group(0));
	    	if (mentionedUser.isVerified()) {
	    		setVerifiedMentions(mentionedUser.getScreenName());
	    	}
	    }
	}

	public List<Tweet> getTweets() {
		return tweets;
	}

	public void setTweets(List<Tweet> tweets) {
		this.tweets = tweets;
	}
	
}
