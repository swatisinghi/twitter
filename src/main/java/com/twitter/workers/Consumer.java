package com.twitter.workers;


import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;

import com.sun.org.apache.bcel.internal.generic.GETSTATIC;
import com.twitter.manager.ConsumerOffsetManager;
import com.twitter.manager.FileBasedQueue;
import com.twitter.manager.OffsetManager;
import com.twitter.manager.TwitterQueue;
import com.twitter.model.Tweet;
import com.twitter.utils.TwitterUtils;

import twitter4j.Paging;
import twitter4j.RateLimitStatus;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;

/**
 * @author swati
 * Worker to consume tweets.
 */
public class Consumer {
	
	private final static Logger LOGGER = Logger.getLogger(Consumer.class);
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
	
	private void gatherTweets(String user) throws TwitterException {
		Date startDate = TwitterUtils.getStartDate();
		Date endDate = TwitterUtils.getEndDate();
		
		List<Status> statuses;
		Paging p = new Paging();
		long lastTweetId = getOffest(user);
		
		while (true) {
			statuses = twitter.getUserTimeline(user, p);
			
			LOGGER.info("User: " + user + "; Gathered " + statuses.size() + " tweets");

			//No more tweets left to process from this user
			if (statuses.size() == 0) {
				return;
			}

			for (Status status: statuses) {
				/**
				 * Since twitter API gives data ordered by date in descending order,
				 * we skip processing this user as soon as we receive a tweet older than the start date 
				 */
		    	if (status.getCreatedAt().before(startDate)) {
		    		LOGGER.info("User: " + user + "No more tweets in the desired period left to process");
		    		return;
		    	}
		    	
		        //If the tweet returned is more recent that what is already processed, we ignore it
		    	//[mostly a re-run where the user is already processes]
		        if (status.getId() > lastTweetId) 
		        	return;
		        
		        //Saving the next offset to be processed in redis
	        	long nextTweetId = status.getId() - 1;
		        LOGGER.debug("User: " + user + "; offset: " + nextTweetId);
			    mgr.setOffset(user, String.valueOf(nextTweetId));  
				p.setMaxId(nextTweetId);
	        
		        //Queueing tweets
		        if (status.getCreatedAt().after(startDate) && status.getCreatedAt().before(endDate)) {
			        Tweet tweet = new Tweet(status.getId(), status.getText(), status.getCreatedAt(), status.getUser().getScreenName(), status.getRetweetCount());
			        LOGGER.info(tweet.toString());
			        queue.enQueue(tweet.toString());
		        }
	        }
	    }
	}
	
	/**
	 * Sleeping for secondsUntilReset in case twitter rate limits the app
	 */
	public boolean rateLimited() {
		int sleep = 10000;
		try {
			Map<String, RateLimitStatus> rateLimitStatusMap = twitter.getRateLimitStatus();
			RateLimitStatus rateLimitStatus = rateLimitStatusMap.get("/statuses/user_timeline");
			if (rateLimitStatus.getRemaining() == 0) {
				sleep = rateLimitStatus.getSecondsUntilReset() * 1000;
			} 
			LOGGER.info("Rate limited, sleeping for " + sleep + " milliseconds");
			Thread.sleep(sleep);
			return true;
		} catch (TwitterException e) {
			LOGGER.error("Exception occurred when sleeping due to rate limit: " + e.getMessage());
			return false;
		} catch (InterruptedException e) {
			LOGGER.error("Exception occurred when sleeping due to rate limit: " + e.getMessage());
			return false;
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
			LOGGER.error("Failed to search tweets: " + e.getMessage());
			if (!rateLimited()) {
				System.exit(-1);
			} 
			consume();
		} finally {
			queue.close();
		}
	}
}
