package com.twitter.workers;


import java.util.List;

import com.twitter.manager.FileBasedQueue;
import com.twitter.manager.TwitterQueue;;
import com.twitter.model.Tweet;

import twitter4j.Paging;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;

public class Consumer {
	
	private static final int MAX_TWEETS = 5;
	private TwitterQueue<String> queue;
	
	
	public Consumer() {
		queue = new FileBasedQueue();
	}
	
	private Twitter configure() {
		
        TwitterFactory tf = new TwitterFactory();
        return tf.getInstance();
        
	}
	
	public void consume() {
		
		Twitter twitter = configure();
		
		String[] usersToTrack = {"taylorswift13"};
		try {
    		
			for (String user : usersToTrack) {
				
    			List<Status> statuses;
    			Paging p = new Paging();
    			long lastID = Long.MAX_VALUE;
//    			p.set	MaxId(lastID);
    			
    			int i = 0;
    			while (i < MAX_TWEETS) {
    				statuses = twitter.getUserTimeline(user, p);
    				System.out.println("Gathered " + statuses.size() + " tweets");
    			      for (Status status: statuses) {
    			        if(status.getId() < lastID) 
    			        	lastID = status.getId();
    			        
    			        Tweet tweet = new Tweet(status.getId(), status.getText(), status.getCreatedAt(), status.getUser().getScreenName(), status.getRetweetCount());
    			        System.out.println(tweet.toString());
    			        queue.enQueue(tweet.toString());
    			        
    			      }
    			    i++;
    				p.setMaxId(lastID - 1);
    			}
    			
    		}

		} catch (TwitterException e) {
			e.printStackTrace();
            System.out.println("Failed to search tweets: " + e.getMessage());
            System.exit(-1);
		} finally {
			queue.close();
		}
	}
}
