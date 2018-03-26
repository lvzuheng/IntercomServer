package sz.lzh.conversation.service.action;

import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import sz.lzh.conversation.bean.receive.P2PIntercomRequest;
import sz.lzh.conversation.config.ActionConfig;
import sz.lzh.conversation.service.ActionManager;
import sz.lzh.conversation.service.inform.Informs;
import sz.lzh.conversation.service.inform.IntercomInforms;
import sz.lzh.conversation.util.Group.Interlocutor;
import sz.lzh.conversation.util.Group.InterlocutorManager;
import sz.lzh.conversation.util.coder.JsonEncoder;
import sz.lzh.conversation.util.connect.ChannelController;

@Component("6012")
public class P2PIntercomRequestAction extends ActionManager{

	@Override
	public void excute(String msg, ChannelHandlerContext ctx) {
		// TODO Auto-generated method stub
		super.excute(msg, ctx);
		P2PIntercomRequest p2pIntercomRequest = JSONObject.parseObject(msg,P2PIntercomRequest.class);
		Interlocutor interlocutor = InterlocutorManager.getInstance().getInterlocutor(p2pIntercomRequest.getReceiver());
		
		if(interlocutor==null){
			Informs.errorDataInform(ctx.channel(), p2pIntercomRequest.getUsername(), ActionConfig.P2PINTERCOMREQUEST);
			return;
		}
		if(p2pIntercomRequest.isAction())
			IntercomInforms.P2POpenReqestInform(interlocutor,p2pIntercomRequest.getUsername());
		else {
			IntercomInforms.P2PCloseReqestInform(interlocutor, p2pIntercomRequest.getUsername());
			InterlocutorManager.getInstance().getInterlocutor(p2pIntercomRequest.getUsername()).stopP2PIntercom();;
			interlocutor.stopP2PIntercom();
		}
	
	}

}
