package sz.lzh.conversation.dao.cache.sync;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import sz.lzh.conversation.bean.CodeInfo;
import sz.lzh.conversation.dao.cache.CacheInfo;
import sz.lzh.conversation.dao.cache.CacheManager;
import sz.lzh.conversation.dao.cache.impl.CacheDataImpl;

@SuppressWarnings("rawtypes")
public class SyncDate {

		public SyncDate() {
			// TODO Auto-generated constructor stub
		}

		public static <T> void execute(String path){
			ApplicationContext context = new ClassPathXmlApplicationContext(path);
			for (String name : context.getBeanDefinitionNames()) {
				Object bean = context.getBean(name);
				if (bean instanceof CacheInfo) {
					((CacheInfo) bean).init();
				}
			}
		}
		public static void execute(String path,boolean clean){
			ApplicationContext context = new ClassPathXmlApplicationContext(path);
			if(clean)
				context.getBean(CacheDataImpl.class).flush();
			
			for (String name : context.getBeanDefinitionNames()) {
				Object bean = context.getBean(name);
				if (bean instanceof CacheInfo) {
					((CacheInfo) bean).init();
				}
			}
		}
		public static void execute(ApplicationContext context,boolean clean){
			if(clean)
				context.getBean(CacheDataImpl.class).flush();
			
			for (String name : context.getBeanDefinitionNames()) {
				Object bean = context.getBean(name);
				if (bean instanceof CacheInfo) {
					((CacheInfo) bean).init();
				}
			}
		}
}
