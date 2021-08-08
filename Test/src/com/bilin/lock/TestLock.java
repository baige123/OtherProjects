package com.bilin.lock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class TestLock {

	
	private static int count=0;
	
	static Lock lock = new ReentrantLock();
	
	public static void inc(){
		try {
			lock.lock();
			Thread.sleep(1);
			count++;
		} catch (InterruptedException e) {
			e.printStackTrace();
		}finally{
			lock.unlock();
		}
	}
	
	public static void main(String args[]) throws InterruptedException{
		for(int i=0;i<1000;i++){
			new Thread(()->inc()).start();
		}
		Thread.sleep(4000);
		System.out.println(count);
	}
}
