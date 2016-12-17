package com.twitter.manager;

import redis.clients.jedis.Jedis;

public class ConsumerOffsetManager implements OffsetManager {

	// Redis host 
	private static String host = "localhost";
	
	private static Jedis jedis;
	
	public ConsumerOffsetManager() {
		
	}
	
	public static Jedis getConnection() { 
		if (jedis == null) { 
			jedis = new Jedis(host);
		} 
	    return jedis; 
	}
	 
	public String getOffset(String key) {
		Jedis redis = getConnection();
		return redis.get(key);
	}
	
	public String setOffset(String key, String value) {
		Jedis redis = getConnection();
		return redis.set(key, value);
	}
	

}
