package com.twitter.manager;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

import org.apache.log4j.Logger;

import com.twitter.manager.TwitterQueue;
import com.twitter.workers.Consumer;

public class FileBasedQueue implements TwitterQueue<String> {

	private final static Logger LOGGER = Logger.getLogger(FileBasedQueue.class);
	private static final String name = "/tmp/queue";
	private static final String NEWLINE = "\n";
	private FileWriter fw;
	private FileReader fr;
	BufferedWriter bw;
	BufferedReader br;
	
	public FileWriter getQueueForWriter() {
		if (fw == null) {
			try {
				fw = new FileWriter(name, true);
			} catch (IOException e) {
				e.printStackTrace();
				System.out.println("Unable to get file based queue: " + e.getMessage());
			}
		}
		return fw;
	}
	
	public void enQueue(String element) {
		
		FileWriter fw = getQueueForWriter();
		
		try {
			if (bw == null)
				bw = new BufferedWriter(fw);
			bw.write(element);
			bw.write(NEWLINE);
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Unable to get file based queue: " + e.getMessage());
		}	
	}

	public FileReader getQueueForReader() {
			if (fr == null) {
				try {
					fr = new FileReader(name);
				} catch (IOException e) {
					e.printStackTrace();
					System.out.println("Unable to get file based queue: " + e.getMessage());
				}
			}
			return fr;
		}
		

	public String deQueue() {
		
		String tweet;
		Scanner reader = null;
		
		try {
			reader = new Scanner(new File(name));
			while (reader.hasNextLine() && (tweet = reader.nextLine()) != null)  {
				LOGGER.info("Dequeing.." + tweet);
			}
		} catch (IOException e) {
			LOGGER.error("Unable to get file based queue: " + e.getMessage());
		} finally {
			reader.close();
		}
		return null;
	}
	
	public void close() {
		try {
			if (bw != null)
				bw.close();

			if (fw != null)
				fw.close();
			
			if (fr != null)
				fr.close();
			
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Unable to get file based queue: " + e.getMessage());
		}
	}

}
