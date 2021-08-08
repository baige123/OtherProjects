package com.bilin.designMode.create.singleton;


/**
 * 
 * 优点：避免资源浪费
 * 缺点：延迟加载但存在线程安全问题，多线程下实例有可能不唯一
 * @author bilinma
 *
 */
public class LazySimpleSingleton {

	private static LazySimpleSingleton instance  = null;
	
	private LazySimpleSingleton(){
		System.out.println("LazySimpleSingleton is create");
	}
	
	public final static LazySimpleSingleton getInstance(){
		if(instance == null){
			instance = new LazySimpleSingleton();
		}
		return instance;
	}
	
}
