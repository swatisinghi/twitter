package com.twitter.manager;

public interface TwitterQueue<E> {
	
	
	void enQueue(E element);
	
	E deQueue();

	void close();

	
}
