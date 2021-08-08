package com.bilin.designMode.create.singleton;

/**
 * 
 * 优点：避免资源浪费
 * 缺点：使用syncronized 又影响性能
 * @author xiaobin.ma
 *
 */
public class LazySingleton {
	
	private static LazySingleton instance  = null;

	private LazySingleton(){
		System.out.println("LazySingleton is create");
	}
	
	public static synchronized LazySingleton getInstance(){
		if(instance == null){
			instance = new LazySingleton();
		}
		return instance;
	}
	
}
