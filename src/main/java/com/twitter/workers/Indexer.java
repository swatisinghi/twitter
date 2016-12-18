package com.twitter.workers;

import org.apache.log4j.Logger;

import com.twitter.manager.FileBasedQueue;
import com.twitter.manager.TwitterQueue;

public class Indexer {
	
	private final static Logger LOGGER = Logger.getLogger(Consumer.class);
	private TwitterQueue<String> queue;
	
	public Indexer() { 
		queue = new FileBasedQueue();	
	}	
        
	/**
	 * Not doing offset management at the indexer side, assuming the queue will manage its own offset
	 * This method will just log the tweets to index (some tweets have new lines in them, not reading every json as a whole)
	 * [Since we are using a file based queue for this implementation, 
	 * the tweets will be read from the beginning of the file each time indexer runs]
	 * Ideally the offset should be managed by queue and the correct 
	 * tweets should be read by indexer worker
	 */
	public void index() {
		LOGGER.info("========= Indexing begins ==========");
		queue.deQueue();
		LOGGER.info("========= Indexing ends ==========");
		queue.close();
		return;
	}
	
    public static void main(String args[]) { }
}
