package sz.lzh.conversation.util.manager;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CacheThreadManager extends Thread {
	
	private ExecutorService cachedThreadPool = Executors.newCachedThreadPool();
	
	private static CacheThreadManager cacheThreadManager;

	public static CacheThreadManager getInstance(){
		if( cacheThreadManager == null){
			cacheThreadManager = new CacheThreadManager();
			cacheThreadManager.start();
		}
		return cacheThreadManager;
	}
	
	public ExecutorService getCachedThreadPool() {
		return cachedThreadPool;
	}
}
