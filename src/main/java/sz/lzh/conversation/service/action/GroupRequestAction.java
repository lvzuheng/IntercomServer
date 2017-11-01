package sz.lzh.conversation.service.action;

import java.util.List;

import javax.persistence.Id;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;

import io.netty.channel.ChannelHandlerContext;
import sz.lzh.conversation.bean.receive.GroupRequest;
import sz.lzh.conversation.bean.reply.GroupRequestReply;
import sz.lzh.conversation.bean.reply.IntercomRequestReply;
import sz.lzh.conversation.bean.reply.GroupInfosReply;
import sz.lzh.conversation.config.ActionConfig;
import sz.lzh.conversation.dao.cache.infos.CacheGroupInfo;
import sz.lzh.conversation.dao.cache.infos.CacheGroupMember;
import sz.lzh.conversation.dao.cache.infos.CacheUser;
import sz.lzh.conversation.service.ActionManager;
import sz.lzh.conversation.service.inform.GroupInforms;
import sz.lzh.conversation.service.inform.Informs;
import sz.lzh.conversation.service.inform.IntercomInforms;
import sz.lzh.conversation.util.Group.Group;
import sz.lzh.conversation.util.Group.GroupManager;
import sz.lzh.conversation.util.Group.Interlocutor;
import sz.lzh.conversation.util.Group.InterlocutorManager;
import sz.lzh.conversation.util.coder.JsonEncoder;

@Component("6008")
public class GroupRequestAction extends ActionManager{

	@Autowired
	private CacheGroupInfo cacheGroupInfo;
	@Autowired
	private CacheGroupMember cacheGroupMember;
	@Autowired
	private CacheUser cacheUser;


	@Override
	public void excute(String msg, ChannelHandlerContext ctx) {
		// TODO Auto-generated method stub
		super.excute(msg, ctx);
		GroupRequest groupRequest = JSONObject.parseObject(msg, GroupRequest.class);
		if(!cacheUser.isExist(groupRequest.getUsername()) || !cacheGroupInfo.isExist(groupRequest.getGroupId())){
			Informs.notGroupInform(ctx.channel(), groupRequest.getUsername(), ActionConfig.GROUPREQUEST);
			return;
		}
		switch(groupRequest.getAction()){
		case ActionConfig.INGROUP:
			enterGroup(groupRequest, ctx);
			break;
		case ActionConfig.OUTGROUP:
			leaveGroup(groupRequest, ctx);
			break;
		}
	}


	private void enterGroup(GroupRequest groupRequest, ChannelHandlerContext ctx){

		Group pointGroup = GroupManager.getInstance().getGroup(groupRequest.getGroupId());//目标组
		String pointGroupName = cacheGroupInfo.getGroupName(groupRequest.getGroupId());//目标组名
		Interlocutor interlocutor = InterlocutorManager.getInstance().getInterlocutor(groupRequest.getUsername());




		if(interlocutor.getGroup()!=null){

			if(interlocutor.getGroup().getGroupId() == groupRequest.getGroupId())
				return ;

			interlocutor.getGroup().removeMember(groupRequest.getUsername());
			GroupInforms.leaveGroupInform(interlocutor.getGroup().getMemberList(), groupRequest.getUsername());
			interlocutor.closeIntercom();

		}

		if(pointGroup ==null) 
			pointGroup = new Group(groupRequest.getGroupId(), pointGroupName);
		else //给其他成员发送进组通知
			GroupInforms.enterGroupInform(pointGroup.getMemberList(), groupRequest.getUsername());

		pointGroup.setMember(interlocutor.getUsername(), interlocutor);

		GroupInforms.enterGroupSuccessInform(interlocutor);

		if(pointGroup.getStatus()){//下发打开对讲信息
			IntercomInforms.openInform(groupRequest.getUsername(), pointGroup.getIntercomTransmitter());
		}

	}
	private void leaveGroup(GroupRequest groupRequest, ChannelHandlerContext ctx){
		Interlocutor interlocutor = InterlocutorManager.getInstance().getInterlocutor(groupRequest.getUsername());

		if(interlocutor.getGroup()!=null){
			
			Informs.successInform(ctx.channel(), groupRequest.getUsername(), ActionConfig.GROUPREQUEST);
			//下发关闭对讲信息
			if(interlocutor.getGroup().getIntercomTransmitter().equals(groupRequest.getUsername()))
				IntercomInforms.closeInform(interlocutor.getGroup().getOtherMemberList(groupRequest.getUsername()), groupRequest.getUsername());
			
			interlocutor.getGroup().removeMember(interlocutor.getUsername());
			
			GroupInforms.leaveGroupInform(interlocutor.getGroup().getMemberList(), groupRequest.getUsername());
		}



	}
}
