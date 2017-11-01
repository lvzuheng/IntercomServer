package sz.lzh.conversation.service.action;

import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;

import io.netty.channel.ChannelHandlerContext;
import sz.lzh.conversation.bean.receive.IntercomRequest;
import sz.lzh.conversation.bean.reply.IntercomRequestReply;
import sz.lzh.conversation.config.ActionConfig;
import sz.lzh.conversation.service.ActionManager;
import sz.lzh.conversation.service.inform.Informs;
import sz.lzh.conversation.service.inform.IntercomInforms;
import sz.lzh.conversation.util.Group.Group;
import sz.lzh.conversation.util.Group.GroupManager;
import sz.lzh.conversation.util.Group.Interlocutor;
import sz.lzh.conversation.util.Group.InterlocutorManager;
import sz.lzh.conversation.util.coder.JsonEncoder;
import sz.lzh.conversation.util.manager.StreamManager;

@Component("6010")
public class IntercomRequestAction extends ActionManager{

	@Override
	public void excute(String msg, ChannelHandlerContext ctx) {
		// TODO Auto-generated method stub
		super.excute(msg, ctx);
		IntercomRequest intercomRequest = JSONObject.parseObject(msg, IntercomRequest.class);
		Interlocutor interlocutor = InterlocutorManager.getInstance().getInterlocutor(intercomRequest.getUsername());
		Group group = interlocutor.getGroup();
		if(group==null){//无对讲组，需要重新进组
			Informs.notGroupInform(interlocutor.getChannel(),interlocutor.getUsername(), ActionConfig.INTERCOMREQUEST);
			return;
		}	
		if(intercomRequest.isAction())
			openIntercom(interlocutor,group);
		else
			closeIntercom(interlocutor,group);
		
	}
	
	private void openIntercom(Interlocutor interlocutor,Group group){
		if(group.requestForIntercomTransmitter(interlocutor.getUsername())){
			Informs.successInform(interlocutor.getChannel(),interlocutor.getUsername(), ActionConfig.INTERCOMREQUEST);
			IntercomInforms.openInform(group.getOtherMemberList(interlocutor.getUsername()), interlocutor.getUsername());
			StreamManager.getInstance().setIntercom(interlocutor);//打开流监听
		}else{
			Informs.notPromission(interlocutor.getChannel(), interlocutor.getUsername(), ActionConfig.INTERCOMREQUEST);
		}
	}
	
	private void closeIntercom(Interlocutor interlocutor,Group group){
		if(group.getIntercomTransmitter().equals(interlocutor.getUsername())){
			group.closeIntercom();
		}
	}
}
