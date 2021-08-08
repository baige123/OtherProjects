package com.bilin.designMode.create.singleton;


/**
 * 只需要在创建类的时候进行同步，所以只要将创建和getInstance()分开，单独为创建加synchronized关键字，也是可以的
 * 缺点：不优雅
 * @author xiaobin.ma
 *
 */
public class LazyDoubleCheckSingleton {  
	  
    private static LazyDoubleCheckSingleton instance = null;  
  
    private LazyDoubleCheckSingleton() {  
    }  
  
    private static synchronized void syncInit() {  
        if (instance == null) {  
            instance = new LazyDoubleCheckSingleton();  
        }  
    }  
  
    public static LazyDoubleCheckSingleton getInstance() {  
        if (instance == null) {  
            syncInit();  
        }  
        return instance;  
    }  
}  