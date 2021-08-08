package com.bilin.designMode.create.singleton;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class Test {

	public static void main(String[] args) {
		//LazyStaticInnerClassSingleton.getInstance();
		
//		EnumSingleton instance = EnumSingleton.getInstance();
//		System.out.println(EnumSingleton.getInstance());
//		instance.setData(new Object());
//		System.out.println(instance.getData());
		
//		try {
//			Constructor c = EnumSingleton.class.getDeclaredConstructor(String.class,int.class);
//			c.setAccessible(true);
//			Object instance2 = c.newInstance("bilin",666);
//		} catch (Exception e) {
//			e.printStackTrace();
//		} 
		
		TestExexutorThread t1 = new TestExexutorThread();

		new Thread(t1).start();
		new Thread(t1).start();
	
	}

}
