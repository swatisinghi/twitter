package com.twitter.workers;


import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.twitter.manager.ConsumerOffsetManager;
import com.twitter.manager.FileBasedQueue;
import com.twitter.manager.OffsetManager;
import com.twitter.manager.TwitterQueue;
import com.twitter.model.Tweet;
import com.twitter.utils.TwitterUtils;

import twitter4j.Paging;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;

/**
 *  We are processing a maximum 1000 tweets, because there are not too many tweets in a month
 *  
 */
public class Consumer {
	
	private final static Logger LOGGER = Logger.getLogger(Consumer.class);
	private static final int MAX_TWEETS = 1000;
	private TwitterQueue<String> queue;
	private OffsetManager mgr;
	Twitter twitter = null;
	
	public Consumer() {
		queue = new FileBasedQueue();
		mgr = new ConsumerOffsetManager();
		twitter = configure();
	}
	
	private Twitter configure() {
        TwitterFactory tf = new TwitterFactory();
        return tf.getInstance();
	}
	
	/**
	 * Using redis to store the offset (max_id). Also, storing the max_id in memory
	 * In case the application goes down and we restart it the offset will be read from redis
	 * The offset management (to avoid duplicate reading of tweets) is done via redis
	 */
	private long getOffest(String user) {
		return mgr.getOffset(user) == null ? Long.MAX_VALUE : Long.valueOf(mgr.getOffset(user)); 
	}
	
	/**
	 * Using redis to store the count of tweets processed for a given user.
	 * In case the application goes down and we restart it the counts will be read from redis
	 */
	private int getCount(String user) {
		return mgr.getOffset(user) == null ? 0 : Integer.valueOf(mgr.getOffset(user)); 
	}
	
	private void gatherTweets(String user) throws TwitterException {
		Date startDate = TwitterUtils.getStartDate();
		Date endDate = TwitterUtils.getEndDate();
		
		List<Status> statuses;
		Paging p = new Paging();
		long lastID = getOffest(user);
		String userTweetCountStr = user.concat("_count");
		int count = getCount(userTweetCountStr);
		
		while (count < MAX_TWEETS) {
			statuses = twitter.getUserTimeline(user, p);
			
			LOGGER.info("User: " + user + "; Gathered " + statuses.size() + " tweets");
			System.out.println("Count ======== " + count);
	    	//Updating the count in redis
	    	count += statuses.size();
			mgr.setOffset(userTweetCountStr, String.valueOf(count));
			
			for (Status status: statuses) {
				/**
				 * Since twitter API gives data ordered by date in descending order,
				 * we skip processing as soon as we receive a tweet older than the start date 
				 */
		    	if (status.getCreatedAt().before(startDate))
		    		return;
		    	
		    	
				
		        if (status.getId() < lastID) 
		        	lastID = status.getId();
		        if (status.getCreatedAt().after(startDate) && status.getCreatedAt().before(endDate)) {
			        Tweet tweet = new Tweet(status.getId(), status.getText(), status.getCreatedAt(), status.getUser().getScreenName(), status.getRetweetCount());
			        LOGGER.info(tweet.toString());
			        queue.enQueue(tweet.toString());
		        }
		        
		        LOGGER.debug("User: " + user + "; offset: " + (lastID - 1));
		        //Updating the next offset in redis
			    mgr.setOffset(user, String.valueOf(lastID - 1));  
				p.setMaxId(lastID - 1);
		    }
			
		}
	}
	
	public void consume() {
		LOGGER.info("========= Reading tweets ==========");
		String[] usersToTrack = TwitterUtils.getUsersToTrack();
		
		try {
			for (String user : usersToTrack) {
				gatherTweets(user);
			}	
		} catch (TwitterException e) {
			e.printStackTrace();
            LOGGER.error("Failed to search tweets: " + e.getMessage());
            System.exit(-1);
		} finally {
			queue.close();
		}
	}
}
