package sz.lzh.conversation.util.manager;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ApplicationUtil {
	
	private static ApplicationContext context;
	
	public static void setApplicationContext(ApplicationContext applicationContext) {
		context = applicationContext;
	}
	
	public static ApplicationContext getApplicationContext(){
		if(context == null)
			context = new ClassPathXmlApplicationContext("classpath*:ApplicationContext.xml");
		return context;
	}
	
	public static Object getBean(String bean){
		return context.getBean(bean);
	}
	public static <T> T getBean(Class<T> t) {
		return context.getBean(t);
	}
}
