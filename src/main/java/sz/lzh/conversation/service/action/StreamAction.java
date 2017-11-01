package sz.lzh.conversation.service.action;

import java.util.List;

import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;

import io.netty.channel.ChannelHandlerContext;
import sz.lzh.conversation.bean.receive.Stream;
import sz.lzh.conversation.service.ActionManager;
import sz.lzh.conversation.service.inform.IntercomInforms;
import sz.lzh.conversation.util.Group.Group;
import sz.lzh.conversation.util.Group.GroupManager;
import sz.lzh.conversation.util.Group.Interlocutor;
import sz.lzh.conversation.util.Group.InterlocutorManager;
import sz.lzh.conversation.util.manager.StreamManager;

@Component("6011")
public class StreamAction extends ActionManager{

	@Override
	public void excute(String msg, ChannelHandlerContext ctx) {
		// TODO Auto-generated method stub
		super.excute(msg, ctx);
		Stream stream = JSONObject.parseObject(msg, Stream.class);
		Interlocutor interlocutor  =InterlocutorManager.getInstance().getInterlocutor(stream.getUsername());
		
		Group group = interlocutor.getGroup();
		if(group == null){
			//无对讲组，需要重新进组
			return;
		}
		if(!group.getStatus() || group.getIntercomTransmitter() == null || !group.getIntercomTransmitter().equals(stream.getUsername()))
			return ; 
		StreamManager.getInstance().send(interlocutor, stream.getStream());
	}
}
