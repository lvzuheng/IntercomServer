package sz.lzh.conversation.service.action;

import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;

import io.netty.channel.ChannelHandlerContext;
import sz.lzh.conversation.bean.receive.Stream;
import sz.lzh.conversation.config.ActionConfig;
import sz.lzh.conversation.service.ActionManager;
import sz.lzh.conversation.util.Group.Interlocutor;
import sz.lzh.conversation.util.Group.InterlocutorManager;
import sz.lzh.conversation.util.manager.StreamManager;

@Component("6014")
public class P2PStreamAction extends ActionManager{

	@Override
	public void excute(String msg, ChannelHandlerContext ctx) {
		// TODO Auto-generated method stub
		super.excute(msg, ctx);
		Stream stream = JSONObject.parseObject(msg, Stream.class);
		Interlocutor interlocutor  =InterlocutorManager.getInstance().getInterlocutor(stream.getUsername());
		if(interlocutor.getP2PStatus())
			StreamManager.getInstance().send(interlocutor, stream.getStream());
	}
}
