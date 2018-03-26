package sz.lzh.conversation.net.connect;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.List;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;

import com.lzh.net.connect.TcpServer;

import sz.lzh.conversation.Log.LogController;
import sz.lzh.conversation.config.Config;
import sz.lzh.conversation.config.Configure;
import sz.lzh.conversation.dao.cache.sync.SyncDate;
import sz.lzh.conversation.net.handler.ServiceHandler;
import sz.lzh.conversation.util.manager.ApplicationUtil;

public class MainServer implements ServletContextListener{
	
	private static Logger logger = Logger.getLogger(MainServer.class);
	private static MainServer mainServer;
	
	public static MainServer getInstance() {
		if(mainServer==null)
			mainServer = new MainServer();
		return mainServer;
	}
	
	public MainServer() {
		// TODO Auto-generated constructor stub
	}
	

	public static  void bootUp(){
		try {
			Configure.Init();
			SyncDate.execute(ApplicationUtil.getApplicationContext(), true);
			startServer(Configure.getConfig());
			ApplicationUtil.getBean(LogController.class).openLogController();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.info("服务器启动错误");
			e.printStackTrace();
		}
	}

	public static  void startServer(Config config){
		TcpServer tcpAcceptor = new TcpServer();
		List<Integer> list = config.getPorts();
		for (int i:list) {
			try {
				tcpAcceptor.create()
				.setPort(i)
				.setHandler(new ServiceHandler().setTIMEOUT(60))
				.open();
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
	}

	public void contextInitialized(ServletContextEvent sce) {
		// TODO Auto-generated method stub
		System.out.println("启动函数启动了");
		bootUp();
	}

	public void contextDestroyed(ServletContextEvent sce) {
		// TODO Auto-generated method stub
		
	}
	
}
