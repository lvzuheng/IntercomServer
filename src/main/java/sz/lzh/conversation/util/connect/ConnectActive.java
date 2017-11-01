package sz.lzh.conversation.util.connect;


import java.util.List;

import io.netty.channel.Channel;
import sz.lzh.conversation.config.MemoryConfig;

public class ConnectActive {
	private Channel channel;
	private String uid;
	private int teamId;
	private int orgId;
	
	public ConnectActive(String uid,Channel channel,int orgId,int teamId) {
		// TODO Auto-generated constructor stub
		setChannel(channel);
		setOrgId(orgId);
		setTeamId(teamId);
		setUid(uid);
	}
	
	
	public Channel getChannel() {
		return channel;
	}
	public void setChannel(Channel channel) {
		this.channel = channel;
	}
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public int getOrgId() {
		return orgId;
	}
	
	
	public void setOrgId(int orgId) {
		this.orgId = orgId;
	}
	
//	public List<String> getOrgMember(){
//		List<String> list = DataManager.getInstance().getCacheInfoImpl().getCacheUser().getMember(orgId);
//		list.addAll(DataManager.getInstance().getCacheInfoImpl().getAdministrator().getMember(orgId));
//		return list;
//	}
//	
//	public List<String> getUserMember() {
//		return DataManager.getInstance().getCacheInfoImpl().getCacheUser().getMember(orgId);
//	}
//	
//	public List<String> getClientMember() {
//		return DataManager.getInstance().getCacheInfoImpl().getAdministrator().getMember(orgId);
//	}
//	
	
	public void removeConnect(){
//		RealTimeStreamController.removeRealTimeStramMap(uid);
//		DataManager.getInstance().getCacheDataImpl().del(MemoryConfig.ONLINEUSER, uid);
	}


	public int getTeamId() {
		return teamId;
	}


	public void setTeamId(int teamId) {
		this.teamId = teamId;
	}


	
}
