package sz.lzh.conversation.service.inform;

import java.util.List;

import io.netty.channel.Channel;
import sz.lzh.conversation.bean.receive.GroupList;
import sz.lzh.conversation.bean.reply.GroupInfosReply;
import sz.lzh.conversation.bean.reply.GroupListReply;
import sz.lzh.conversation.bean.reply.GroupMemberListReply;
import sz.lzh.conversation.bean.reply.GroupRequestReply;
import sz.lzh.conversation.config.ActionConfig;
import sz.lzh.conversation.util.Group.Interlocutor;
import sz.lzh.conversation.util.coder.JsonEncoder;

public class GroupInforms {

	public static void enterGroupInform(Interlocutor interlocutor,String eUser){
		interlocutor.getChannel().writeAndFlush(
				new JsonEncoder().getByteEncoder(
						new GroupInfosReply(ActionConfig.GROUPINFOS, interlocutor.getUsername(),eUser,ActionConfig.INGROUP)));
	}
	public static void enterGroupInform(List<Interlocutor> iList,String eMember){
		for(int i=0;i<iList.size();i++)
			enterGroupInform(iList.get(i),eMember);
	}
	public static void leaveGroupInform(Interlocutor interlocutor,String eUser){
		interlocutor.getChannel().writeAndFlush(
				new JsonEncoder().getByteEncoder(
						new GroupInfosReply(ActionConfig.GROUPINFOS, interlocutor.getUsername(),eUser,ActionConfig.OUTGROUP)));
	}
	public static void leaveGroupInform(List<Interlocutor> iList,String eMember){
		for(int i=0;i<iList.size();i++)
			enterGroupInform(iList.get(i),eMember);
		
	}
	
	public static void enterGroupSuccessInform(Interlocutor interlocutor){
		System.out.println("grouprequest:"+new JsonEncoder().getByteEncoder(
						new GroupRequestReply(ActionConfig.GROUPREQUEST, interlocutor.getUsername(),interlocutor.getGroup().getGroupId(),interlocutor.getGroup().getGroupName())));
		interlocutor.getChannel().writeAndFlush(
				new JsonEncoder().getByteEncoder(
						new GroupRequestReply(ActionConfig.GROUPREQUEST, interlocutor.getUsername(),interlocutor.getGroup().getGroupId(),interlocutor.getGroup().getGroupName())));
	}
	
	public static void GroupListInfrom(Channel channel,String username,List<GroupListReply.Group> gList){
		channel.writeAndFlush(new JsonEncoder().getByteEncoder(new GroupListReply(ActionConfig.GROUPLIST, username, gList)));
	}
	
	public static void GroupMemberListInfrom(Channel channel,String username,int groupId,String groupName,List<GroupMemberListReply.User> uList){
		channel.writeAndFlush(new JsonEncoder().getByteEncoder(
				new GroupMemberListReply(ActionConfig.GROUPMEMBERLIST, username,groupId,groupName,uList)));
	}
}
