package com.twitter.app;

import java.util.Scanner;

import org.apache.log4j.Logger;

import com.twitter.workers.Consumer;
import com.twitter.workers.Indexer;

public class App 
{
	private final static Logger LOGGER = Logger.getLogger(App.class);
	
    public static void main (String[] args)
    {
    	
        LOGGER.info(" ========== Starting ==========");
        
        while (true) {
	        Scanner in = new Scanner(System.in);
	        System.out.println("Menu");
	        System.out.println("1. Consume Tweets");
	        System.out.println("2. Index Tweets");
	        System.out.println("Q. Quit");
	        
	        String ch = in.next();
        
        	if (ch.equals("1")) {
        		Consumer consumer = new Consumer();
                consumer.consume();
        	} else if (ch.equals("2")) {
        		Indexer indexer = new Indexer();
        		indexer.index();
        	} else if (ch.equalsIgnoreCase("Q")) {
        		System.exit(0);
        	} else {
        		System.out.println("Enter correct choice");
        	}
        }
               
    }
}
