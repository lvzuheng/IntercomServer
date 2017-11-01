package sz.lzh.conversation.config;

import java.util.ArrayList;
import java.util.List;

public class Config {
	public static String dataAddress ="1270.0.1"; 
	private static int dataPort  = 3306;
	public static String dataUsername ="root"; 
	public static String dataPassword ="admin"; 
	private static String cacheAddress  ="1270.0.1"; 
	private static int cachePort  = 6379;
	public static String cachePassword ="admin"; 
	private static int port = -1;
	private static int first = -1;
	private static int last = -1;
	private static String database;
	private static String cacheDatabase;

	public void setPort(int port) {
		this.port = port;
	}
	public int getFirst() {
		return first;
	}
	public void setFirst(int first) {
		this.first = first;
	}
	public int getLast() {
		return last;
	}
	public void setLast(int last) {
		this.last = last;
	}
	public List<Integer> getPorts() {
		List<Integer> list = new ArrayList<Integer>();
		if(port!=-1 || (first!=-1 && last !=-1 && last>=first)){
			list.add(port);
			for(int i = first ;i<= last;i++)
				list.add(i);
		}
		return list;
	}
	public String getDataAddress() {
		return dataAddress;
	}
	public void setDataAddress(String dataAddress) {
		this.dataAddress = dataAddress;
	}
	public int getDataPort() {
		return dataPort;
	}
	public void setDataPort(int dataPort) {
		this.dataPort = dataPort;
	}
	public String getCacheAddress() {
		return cacheAddress;
	}
	public void setCacheAddress(String cacheAddress) {
		this.cacheAddress = cacheAddress;
	}
	public int getCachePort() {
		return cachePort;
	}
	public void setCachePort(int cachePort) {
		this.cachePort = cachePort;
	}
	public static String getDatabase() {
		return database;
	}
	public static void setDatabase(String database) {
		Config.database = database;
	}
	public static String getCacheDatabase() {
		return cacheDatabase;
	}
	public static void setCacheDatabase(String cacheDatabase) {
		Config.cacheDatabase = cacheDatabase;
	}
}
