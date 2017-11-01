package sz.lzh.conversation.util.Group;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import sz.lzh.conversation.service.inform.IntercomInforms;

import java.util.Map.Entry;


/**
 * 
 * 用户信息管理
 * 管理登录的用户信息
 *
 * */
public class InterlocutorManager extends Thread{

	private Map<String, Interlocutor> iMap = new HashMap<String, Interlocutor>();
	
	private static InterlocutorManager interlocutorManager;
	
	public static InterlocutorManager getInstance(){
		if(interlocutorManager == null){
			interlocutorManager = new InterlocutorManager();
//			interlocutorManager.start();
		}
		return interlocutorManager;
	}
	
	public Interlocutor getInterlocutor(String userName){
		return iMap.get(userName);
	}
	public List<Interlocutor> getInterlocutorList(String userName){
		
		return (List<Interlocutor>) iMap.values();
	}
	public Set<String> getNameList(String userName){
		
		return  iMap.keySet();
	}
	
	public void putInterlocutor(String userName,Interlocutor interlocutor){
		iMap.put(userName, interlocutor);
	}
	public void removeInterlocutor(String userName){
		if(iMap.get(userName).getGroup()!=null)
			iMap.get(userName).getGroup().removeMember(userName);
		if(iMap.get(userName).getP2PStatus()){
			IntercomInforms.P2PCloseReqestInform(iMap.get(userName).getP2Pinterlocutor(),userName);
			iMap.get(userName).stopP2PIntercom();
		}
		iMap.remove(userName);
	}
	public boolean isExist(String userName){
		return iMap.containsKey(userName);
	}
	
//	@Override
//	public  void run() {
//		// TODO Auto-generated method stub
//		super.run();
//		while (true) {
//				Iterator<Entry<String, Interlocutor>> eIterator =iMap.entrySet().iterator();
//				while(eIterator.hasNext()){
//					Entry<String, Interlocutor> entry = eIterator.next();
//					System.out.println("扫描成员组"+entry.getKey()+","+(entry.getValue().getChannel().isActive()));
//					if(!entry.getValue().getChannel().isActive()){
//						System.err.println("销毁对象"+entry.getKey());	
//						removeInterlocutor(entry.getKey());
//					}
//				}
//			try {
//				Thread.sleep(10000);
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}
//	}
}
