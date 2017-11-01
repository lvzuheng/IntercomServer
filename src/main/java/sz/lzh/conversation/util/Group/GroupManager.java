package sz.lzh.conversation.util.Group;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;


public class GroupManager extends Thread{
	
	private static Map<Integer,Group> gMap = new HashMap<Integer, Group>();
	private boolean runable = false;
	
	
    private static GroupManager groupManager;
    
    public static GroupManager getInstance(){
    	if(groupManager == null){
    		groupManager = new GroupManager();
    		groupManager.start();
    	}
    	return groupManager;
    }
	
	
	public  void putGroup(Integer gId , Group group){
		gMap.put(gId, group);
	}
	public  Group getGroup(Integer gId){
		return gMap.get(gId);
	}
	
	public  List<Group> getGroupList(){
		return (List<Group>) gMap.values();
	}
	
	public  void removeGroup(Integer gId){
		gMap.remove(gId);
	}
	
	public boolean isExist(Integer gId){
		return gMap.containsKey(gId);
	}
	
	@Override
	public  void run() {
		// TODO Auto-generated method stub
		super.run();
		while (runable) {
			if(gMap.size()>0)
				for(Entry<Integer,Group> entry :gMap.entrySet()){
//					if(entry.getValue().getMemberList().size()==0) gMap.remove(entry.getKey());
					System.err.println(entry.getValue().getMemberList().size());
				}
			try {
				Thread.sleep(10000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
