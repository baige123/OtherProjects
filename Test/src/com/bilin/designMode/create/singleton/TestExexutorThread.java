package com.bilin.designMode.create.singleton;

public class TestExexutorThread implements Runnable {

	@Override
	public void run() {
		LazyDoubleCheckSingleton aa = LazyDoubleCheckSingleton.getInstance();
		System.out.println(aa);
	}


}
