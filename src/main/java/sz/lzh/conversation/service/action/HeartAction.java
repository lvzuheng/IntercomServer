package sz.lzh.conversation.service.action;

import org.springframework.stereotype.Component;


import io.netty.channel.ChannelHandlerContext;
import sz.lzh.conversation.service.ActionManager;

@Component("8000")
public class HeartAction extends ActionManager{

	@Override
	public void excute(String msg, ChannelHandlerContext ctx) {
		// TODO Auto-generated method stub
		super.excute(msg, ctx);
	}

}
