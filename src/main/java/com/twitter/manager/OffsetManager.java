package com.twitter.manager;

public interface OffsetManager {
	
	String getOffset(String key);
	
	String setOffset(String key, String value);
	
}
