package com.twitter.manager;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import com.twitter.manager.TwitterQueue;

public class FileBasedQueue implements TwitterQueue<String> {

	private static final String name = "/tmp/queue";
	private FileWriter file;
	BufferedWriter bw;
	
	public FileWriter getQueue() {
		if (file == null) {
			try {
				file = new FileWriter(name, true);
			} catch (IOException e) {
				e.printStackTrace();
				System.out.println("Unable to get file based queue: " + e.getMessage());
			}
		}
		return file;
	}
	
	public void enQueue(String element) {
		
		FileWriter fw = getQueue();
		
		try {
			if (bw == null)
				bw = new BufferedWriter(fw);
			bw.write(element);
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Unable to get file based queue: " + e.getMessage());
		}	
	}

	public String deQueue() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public void close() {
		try {
			if (bw != null)
				bw.close();

			if (file != null)
				file.close();
			
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Unable to get file based queue: " + e.getMessage());
		}
	}

}
