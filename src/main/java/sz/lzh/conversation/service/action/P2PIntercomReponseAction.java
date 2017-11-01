package sz.lzh.conversation.service.action;

import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;

import io.netty.channel.ChannelHandlerContext;
import sz.lzh.conversation.bean.CodeInfo;
import sz.lzh.conversation.bean.receive.P2PIntercomResponse;
import sz.lzh.conversation.bean.reply.P2PIntercomRequestReply;
import sz.lzh.conversation.config.ActionConfig;
import sz.lzh.conversation.service.ActionManager;
import sz.lzh.conversation.service.inform.Informs;
import sz.lzh.conversation.service.inform.IntercomInforms;
import sz.lzh.conversation.util.Group.Interlocutor;
import sz.lzh.conversation.util.Group.InterlocutorManager;
import sz.lzh.conversation.util.coder.JsonEncoder;
import sz.lzh.conversation.util.connect.ChannelController;
import sz.lzh.conversation.util.manager.StreamManager;

@Component("6013")
public class P2PIntercomReponseAction extends ActionManager{

	@Override
	public void excute(String msg, ChannelHandlerContext ctx) {
		// TODO Auto-generated method stub
		super.excute(msg, ctx);
		P2PIntercomResponse p2pIntercomResponse = JSONObject.parseObject(msg, P2PIntercomResponse.class);
		Interlocutor answer = InterlocutorManager.getInstance().getInterlocutor(p2pIntercomResponse.getUsername());
		Interlocutor applicant = InterlocutorManager.getInstance().getInterlocutor(p2pIntercomResponse.getApplicant());
		if(answer!=null && p2pIntercomResponse.isAnswer()){
			answer.startP2PIntercom(applicant);
			applicant.startP2PIntercom(answer);
		}
		
		if(p2pIntercomResponse.isAnswer()){
			StreamManager.getInstance().setP2PIntercom(answer);
			StreamManager.getInstance().setP2PIntercom(applicant);
			IntercomInforms.P2POpenResponseInform(applicant, p2pIntercomResponse.getUsername());
		}
		else
			IntercomInforms.P2PCloseResponseInform(applicant, p2pIntercomResponse.getUsername());
	}
}
