package sz.lzh.conversation.util.Group;


import java.util.Calendar;

import io.netty.channel.Channel;

public class Interlocutor {

	private String username;
	private String nickname;
	private int grade = -1;
	private Channel channel;
	private String charactor;
	private boolean P2PStatus = false;//点对点对讲状态
	private Interlocutor P2Pinterlocutor;
	private Group group;
//	private int groupId =-1;
//	private String groupName;
	private boolean Intercomstatus = false;//组内对讲状态

	public Interlocutor() {
		// TODO Auto-generated constructor stub
	}
//	public Interlocutor(String username,String nickname,int grade,Channel channel,int groupId) {
//		// TODO Auto-generated constructor stub
//		setUsername(username);
//		setNickname(nickname);
//		setGrade(grade);
//		setChannel(channel);
//		setGroupId(groupId);
//		InterlocutorManager.getInstance().putInterlocutor(username, this);
//	}
	public Interlocutor(String username,String nickname,Channel channel) {
		// TODO Auto-generated constructor stub
		setUsername(username);
		setNickname(nickname);
		setChannel(channel);
		InterlocutorManager.getInstance().putInterlocutor(username, this);
	}
	
	public int getGrade() {
		return grade;
	}
	public Interlocutor setGrade(int grade) {
		this.grade = grade;
		return this;
	}
	public Channel getChannel() {
		return channel;
	}
	public Interlocutor setChannel(Channel channel) {
		this.channel = channel;
		return this;
	}
	public String getCharactor() {
		return charactor;
	}
	public Interlocutor setCharactor(String charactor) {
		this.charactor = charactor;
		return this;
	}

	public String getUsername() {
		return username;
	}
	public Interlocutor setUsername(String username) {
		this.username = username;
		return this;
	}
	public String getNickname() {
		return nickname;
	}
	public Interlocutor setNickname(String nickname) {
		this.nickname = nickname;
		return this;
	}
	
	public void openIntercom() {
		Intercomstatus = true;
	}
	public void closeIntercom() {
		Intercomstatus = false;
	}
	
	public boolean getIntercomStatus(){
		return Intercomstatus;
	}

	public boolean getP2PStatus() {
		return P2PStatus;
	}
	public void startP2PIntercom(Interlocutor p2Pinterlocutor) {
		setP2Pinterlocutor(p2Pinterlocutor);
		P2PStatus = true;
	}

	public void stopP2PIntercom(){
		P2PStatus = false;
		if(getP2Pinterlocutor().getP2PStatus())
			getP2Pinterlocutor().stopP2PIntercom();
		P2Pinterlocutor = null;
	}
	public Interlocutor getP2Pinterlocutor() {
		return P2Pinterlocutor;
	}
	public void setP2Pinterlocutor(Interlocutor p2Pinterlocutor) {
		P2Pinterlocutor = p2Pinterlocutor;
	}
	public Group getGroup() {
		return group;
	}
	public void setGroup(Group group) {
		this.group = group;
	}

	public void clear(){
		if(group!=null){
			group.removeMember(username);
			group = null;
		}
		closeIntercom();
		stopP2PIntercom();
	}
}
