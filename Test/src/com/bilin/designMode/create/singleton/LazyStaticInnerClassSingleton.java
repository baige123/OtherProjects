package com.bilin.designMode.create.singleton;

/**
 * 延迟加载
 * 内部类保证线程安全
 * (其实说它完美，也不一定，如果在构造函数中抛出异常，实例将永远得不到创建，也会出错。所以说，十分完美的东西是没有的，我们只能根据实际情况，选择最适合自己应用场景的实现方法)
 * 优点：避免了资源浪费，线程安全，保证了性能而且优雅
 * 缺点：能够被反射破坏
 * @author xiaobin.ma
 *
 */
public class LazyStaticInnerClassSingleton {

	private LazyStaticInnerClassSingleton(){
		System.out.println("LazyStaticInnerClassSingleton is create");
	}
	
	
	private static class SingletonHolder{
		private static LazyStaticInnerClassSingleton instance = new LazyStaticInnerClassSingleton();
	}
	
	public static LazyStaticInnerClassSingleton getInstance(){
		return SingletonHolder.instance;
	}
}
