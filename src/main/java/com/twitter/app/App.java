package com.twitter.app;

import com.twitter.workers.Consumer;

public class App 
{
	
//	private static final HttpClient defaultHttpClient = HttpClients.createDefault();
//	
//	private static String getURLResponse(String username, String since, String until, String querySearch, String scrollCursor) throws Exception {
//		String appendQuery = "";
//		if (username != null) {
//			appendQuery += "from:"+username;
//		}
//		if (since != null) {
//			appendQuery += " since:"+since;
//		}
//		if (until != null) {
//			appendQuery += " until:"+until;
//		}
//		if (querySearch != null) {
//			appendQuery += " "+querySearch;
//		}
//		
//		String url = String.format("https://twitter.com/i/search/timeline?f=realtime&q=%s&src=typd&max_position=%s", URLEncoder.encode(appendQuery, "UTF-8"), scrollCursor);
//		
//		HttpGet httpGet = new HttpGet(url);
//		HttpEntity resp = defaultHttpClient.execute(httpGet).getEntity();
//		
//		System.out.println(EntityUtils.toString(resp));
//		return EntityUtils.toString(resp);
//	}
	
    public static void main (String[] args)
    {
    	
        System.out.println( "Hello World!" );
        
        Consumer consumer = new Consumer();
        consumer.consume();
        
//        String TWITTER_CONSUMER_KEY = "FPjyfCAco4ecpQ6oV1BTtHiKd";
//        String TWITTER_SECRET_KEY = "6Jv1p8L2LjLL35TYFUge8Ruj9k6yCJGquRQbEBkG6obm3Mi3yu";
//        String TWITTER_ACCESS_TOKEN = "557985748-rQgcwLdZxEz07etRyBX0QZF121xoXAw32duYqTO4";
//        String TWITTER_ACCESS_TOKEN_SECRET = "CXQJuHKOAoSdqNzYjlAODgNbKIjmClSWTSOiYR6uyTQT4";
//        int maxTweets = 100;
//
//        ConfigurationBuilder cb = new ConfigurationBuilder();
//        cb.setDebugEnabled(true)
//            .setOAuthConsumerKey(TWITTER_CONSUMER_KEY)
//            .setOAuthConsumerSecret(TWITTER_SECRET_KEY)
//            .setOAuthAccessToken(TWITTER_ACCESS_TOKEN)
//            .setOAuthAccessTokenSecret(TWITTER_ACCESS_TOKEN_SECRET);
//        TwitterFactory tf = new TwitterFactory(cb.build());
//        Twitter twitter = tf.getInstance();
//        
//        String[] usersToTrack = {"taylorswift13"};//, "justinbieber", "taylorswift13", "BarackObama", "rihanna"}; 
//        
//    	try {
//    		
//    		for (String user : usersToTrack) {
//    			
//    			TwitterUser twitterUser = new TwitterUser(user);
//    			
//    			ArrayList<Status> tweets = new ArrayList<Status>();
//    			
//    			Paging p = new Paging();
//    			long lastID = Long.MAX_VALUE;
//    			
//    			while (tweets.size () < maxTweets) {
//    				tweets.addAll(twitter.getUserTimeline(user, p));
//    				System.out.println("Gathered " + tweets.size() + " tweets");
//    			      for (Status status: tweets) {
//    			        if(status.getId() < lastID) 
//    			        	lastID = status.getId();
//    			        
//    			        Tweet tweet = new Tweet(status.getId(), status.getText(), status.getCreatedAt(), status.getUser().getScreenName());
//    			        twitterUser.populateVerifiedMentions(status.getText(), twitter);
//    			        twitterUser.getTweets().add(tweet);
//    			        
//    			      }
//    			    
//    				p.setMaxId(lastID - 1);
//    			}
//    			
//				
//    		}
//
//		} catch (TwitterException e) {
//			e.printStackTrace();
//            System.out.println("Failed to search tweets: " + e.getMessage());
//            System.exit(-1);
//		}
    }
}

//Query query = new Query();
//query.query("from:BarackObama");
//QueryResult result;
//result = twitter.search(query);
//List<Status> tweets = result.getTweets();
//for (Status tweet : tweets) {
//	System.out.println("@" + tweet.getUser().getScreenName() + " - " + tweet.getUser().isVerified() + " - " + tweet.getText());
//}

        
//        try {
//            Query query = new Query("#OkJaanu");
//            QueryResult result;
//            do {
//                result = twitter.search(query);
//                List<Status> tweets = result.getTweets();
//                for (Status tweet : tweets) {
//                    System.out.println("@" + tweet.getUser().getScreenName() + " - " + tweet.getText());
//                }
                
//                User user = twitter.verifyCredentials();
//                List<Status> statuses = twitter.getMentionsTimeline();
//                System.out.println("Showing @" + user.getScreenName() + "'s mentions.");
//                for (Status status : statuses) {
//                    System.out.println("@" + status.getUser().getScreenName() + " - " + status.getText());
//                }
//            } while (true);//while ((query = result.nextQuery()) != null);
//            System.exit(0);
        
    

