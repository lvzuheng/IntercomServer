package sz.lzh.conversation.util.Group;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import org.omg.CORBA.Request;

import sz.lzh.conversation.service.inform.IntercomInforms;



/**
 * 
 * VIDEO，VOICE，INTERCOM当前的组内状态，INTERCOM与VOICE不共存
 * VideoTransmitter：当前视频传输者
 * VoiceTransmitter：当前音频传输者
 * IntercomTransmitter：当前对讲主导者
 * 
 * */
public class Group {

	private Map<String,Interlocutor> gMap = new HashMap<String, Interlocutor>();

	private boolean INTERCOM  = false;
	private String IntercomTransmitter = null;
	private int groupId;
	private String groupName;

	public Group(int groupId,String groupName) {
		// TODO Auto-generated constructor stub
		setGroupId(groupId);
		setGroupName(groupName);
		GroupManager.getInstance().putGroup(groupId, this);
	}

	public void setMember(String uid,Interlocutor interlocutor){
		gMap.put(uid, interlocutor);
		interlocutor.setGroup(this);
	}
	public void setMember(HashMap<String, Interlocutor> map){
		for(Entry<String, Interlocutor> entry:map.entrySet())
			entry.getValue().setGroup(this);
		gMap.putAll(map);
	}
	
	public void removeMember(String uid){
		if(IntercomTransmitter == uid) IntercomTransmitter =null;
		gMap.remove(uid);
	}
	
	public Interlocutor getMember(String uid){
		return gMap.get(uid);
	}
	public Set<String> getMemberNameList(){
		return gMap.keySet();
	}
	
	public List<Interlocutor> getMemberList() {
		// TODO Auto-generated method stub
		List<Interlocutor> list = new ArrayList<Interlocutor>(gMap.values()) ;
       return list ;
	}
	
	public List<Interlocutor> getOtherMemberList(String username) {
		// TODO Auto-generated method stub
		List<Interlocutor> iList = getMemberList();
		iList.remove(getMember(username));
		return iList;
	}
	public int getMemberCount(){
		return gMap.size();
	}
	
	public boolean requestForIntercomTransmitter(String uid){
		if(IntercomTransmitter!=null){
			if(IntercomTransmitter.equals(uid))//对讲者重复申请，允许
				return true;
			if(!gMap.containsKey(uid) ||  gMap.get(IntercomTransmitter).getGrade()>=gMap.get(uid).getGrade())//权限不足，不允许
				return false;
			//非重复申请，权限足够，关闭原对讲者，打开申请者音频
			getMember(IntercomTransmitter).closeIntercom();
			getMember(uid).openIntercom();
		}
		IntercomTransmitter = uid;
		openIntercom();
		return true;
	}
	
	public boolean openIntercom(){
		INTERCOM = true;
		return true;
	}
	public boolean closeIntercom(){
		if(IntercomTransmitter!=null)
			getMember(IntercomTransmitter).closeIntercom();
		INTERCOM = false;
		IntercomTransmitter = null;
		return true;
	}
	public synchronized String getIntercomTransmitter() {
		return IntercomTransmitter;
	}
	
	/**
	 * IDLE = 0;
	 * VIDEO = 1;
	 * VIOCE = 2;
	 * VIDEO + VIOCE =3;
	 * INTERCOM = 4;
	 * VIDEO + INTERCOM =5;
	 * */
	public boolean getStatus() {
		return INTERCOM;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public int getGroupId() {
		return groupId;
	}

	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}
	
	@Override
	protected void finalize() throws Throwable {
		// TODO Auto-generated method stub
		super.finalize();
		GroupManager.getInstance().removeGroup(groupId);
	}
}
