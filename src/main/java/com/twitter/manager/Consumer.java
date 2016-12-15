package com.twitter.manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.elasticsearch.common.xcontent.XContentBuilder;

import com.twitter.model.Tweet;
import com.twitter.model.TwitterUser;

import twitter4j.Paging;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;

public class Consumer {
	
	private static final int MAX_TWEETS = 20;
	public Consumer() {}
	
	private Twitter configure() {
		
        TwitterFactory tf = new TwitterFactory();
        return tf.getInstance();
        
	}
	
//	private void indexItem(List<Map<String, Object>> items, String index, String type) {
//
//        Indexer indexer = new Indexer();
//        indexer.indexDocument(items, index, type);
//
//    }
	
	private void indexItem(HashMap<String, XContentBuilder> items, String index, String type) {

        Indexer indexer = new Indexer();
        indexer.indexDocument(items, index, type);

    }
	
	public void consume() {
		
		Twitter twitter = configure();
		String[] usersToTrack = {"taylorswift13"};
		try {
    		
//			List<Map<String, Object>> esTweets = new ArrayList<Map<String,Object>>();
			HashMap<String, XContentBuilder> esTweets = new HashMap<String, XContentBuilder>();
    		for (String user : usersToTrack) {
    			
    			TwitterUser twitterUser = new TwitterUser(user);
    			List<Status> statuses;
    			Paging p = new Paging();
    			long lastID = Long.MAX_VALUE;
    			
    			
    			while (twitterUser.getTweets().size () < MAX_TWEETS) {
    				statuses = twitter.getUserTimeline(user, p);
    				System.out.println("Gathered " + statuses.size() + " tweets");
    			      for (Status status: statuses) {
    			        if(status.getId() < lastID) 
    			        	lastID = status.getId();
    			        
    			        Tweet tweet = new Tweet(status.getId(), status.getText(), status.getCreatedAt(), status.getUser().getScreenName());
    			        System.out.println(tweet.json().toString());
    			        twitterUser.populateVerifiedMentions(status.getText(), twitter);
    			        twitterUser.getTweets().add(tweet);
//    			        esTweets.add(tweet.json());
    			        esTweets.put(Long.toString(status.getId()), tweet.tweetJson());
    			        
    			        
    			      }
    			    
    				p.setMaxId(lastID - 1);
    			}
    			
    			indexItem(esTweets, "tweets", "tweets");
    		}

		} catch (TwitterException e) {
			e.printStackTrace();
            System.out.println("Failed to search tweets: " + e.getMessage());
            System.exit(-1);
		}
	}
}
