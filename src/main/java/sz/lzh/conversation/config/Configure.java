package sz.lzh.conversation.config;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.Map.Entry;


import java.util.Properties;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.support.WebApplicationContextUtils;

import sz.lzh.conversation.util.manager.ApplicationUtil;

public class Configure {

	private static Config config;
	private static Logger logger = Logger.getLogger(Configure.class);

	public static void Init(){
		InitProtect("config.properties");
	}

	public static void InitProtect(String name){
		String path  =System.getProperty("user.dir")+"\\conf\\"+name;
		FileInputStream fileInputStream = null;
		try {
			if(new File(path).exists()){
				fileInputStream = new FileInputStream(path);
				if(fileInputStream!=null){
					entry(fileInputStream);
//					ApplicationContext context = new ClassPathXmlApplicationContext("file:"+System.getProperty("user.dir")+"\\conf\\ApplicationContext.xml");
					ApplicationContext context =  ContextLoader.getCurrentWebApplicationContext() == null ?  new ClassPathXmlApplicationContext("file:"+System.getProperty("user.dir")+"\\conf\\ApplicationContext.xml"):ContextLoader.getCurrentWebApplicationContext();
					ApplicationUtil.setApplicationContext(context);
					logger.info("读取了外部配置文件："+path);
				}
			}else{
				entry();
//				ApplicationContext context = new ClassPathXmlApplicationContext("classpath*:ApplicationContext.xml");
				ApplicationContext context =  ContextLoader.getCurrentWebApplicationContext() == null ?  new ClassPathXmlApplicationContext("classpath*:ApplicationContext.xml"):ContextLoader.getCurrentWebApplicationContext();
				ApplicationUtil.setApplicationContext(context);
				logger.info("读取了本地配置文件："+path);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			try {
				if(fileInputStream!=null)
					fileInputStream.close();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	public static Config getConfig(){
		if(config == null)
			config = new Config();
		return config;
	}

	public static void entry(){

		try {
//			Configure.class.getClassLoader().getResource("config.properties").getPath()
			System.out.println(Configure.class.getClassLoader().getResource("config.properties").getPath());
			FileInputStream fileInputStream = new FileInputStream(Configure.class.getClassLoader().getResource("config.properties").getPath());
			entry(fileInputStream);
			fileInputStream.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	public static void entry(InputStream inputStream){
		Properties properties = new Properties();
		try {
			properties.load(inputStream);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for(Entry<Object, Object> entry :properties.entrySet()){
			Field field;
			try {
				if(String.valueOf(entry.getKey())!=null && !String.valueOf(entry.getKey()).equals("")){
					field = getConfig().getClass().getDeclaredField(String.valueOf(entry.getKey()));
					field.setAccessible(true);
					String type = field.getType().getName();
			        if ("java.lang.String".equals(type)) {
			            field.set(null,entry.getValue());
			        } else if ("byte".equals(type) || "java.lang.Byte".equals(type)) {
			            field.set( null,(Byte) entry.getValue());
			        } else if ("short".equals(type) || "java.lang.Short".equals(type)) {
			            field.set(null, Short.valueOf((String) entry.getValue()));
			        } else if ("int".equals(type) || "java.lang.Integer".equals(type)) {
			            field.set(null, Integer.valueOf((String) entry.getValue()));
			        } else if ("long".equals(type) || "java.lang.Long".equals(type)) {
			            field.set(null, Long.valueOf((String) entry.getValue()));
			        } else if ("boolean".equals(type) || "java.lang.Boolean".equals(type)) {
			            field.set(null, Boolean.valueOf((Boolean) entry.getValue()));
			        } else if ("float".equals(type) || "java.lang.Float".equals(type)) {
			            field.set(null, Float.valueOf((String) entry.getValue()));
			        } else if ("double".equals(type) || "java.lang.Double".equals(type)) {
			            field.set(null, Double.valueOf((String) entry.getValue()));
			        } else if ("char".equals(type) || "java.lang.Character".equals(type)) {
			            field.set(null, ((String) entry.getValue()).charAt(0));
			        }
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	} 
}
