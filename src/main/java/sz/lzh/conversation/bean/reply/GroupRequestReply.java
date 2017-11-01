package sz.lzh.conversation.bean.reply;

import sz.lzh.conversation.bean.CodeInfo;

public class GroupRequestReply extends CodeInfo{


	private String groupName;
	private int groupId;
	
	public GroupRequestReply(int pid,String username,int groupId,String groupName) {
		// TODO Auto-generated constructor stub
		setPid(pid);
		setUsername(username);
		setGroupId(groupId);
		setGroupName(groupName);
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

	
}
