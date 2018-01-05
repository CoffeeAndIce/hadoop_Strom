package StormD1;

import java.util.concurrent.ConcurrentHashMap;

public class LocalCache {
		  
	    private LocalCache() {  
	    }   // 防止在外部实例化  
	  
	    // 使用volatile延迟初始化，防止编译器重排序  
	    private static volatile LocalCache instance;  
	  
	    public static LocalCache getInstance() {  
	  
	        // 对象实例化时与否判断（不使用同步代码块，instance不等于null时，直接返回对象，提高运行效率）  
	        if (instance == null) {  
	            // 同步代码块（对象未初始化时，使用同步代码块，保证多线程访问时对象在第一次创建后，不再重复被创建）  
	            synchronized (LocalCache.class) {  
	                // 未初始化，则初始instance变量  
	                if (instance == null) {  
	                    instance = new LocalCache();  
	                }  
	            }  
	        }  
	        return instance;  
	    }
	    private static final ConcurrentHashMap<String, Long> dataMap = new ConcurrentHashMap<String, Long>(); 
	    public ConcurrentHashMap<String, Long> getMap(){
	    	return dataMap;
	    }
}
