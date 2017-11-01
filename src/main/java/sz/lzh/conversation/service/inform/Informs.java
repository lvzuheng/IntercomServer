package sz.lzh.conversation.service.inform;

import org.springframework.stereotype.Controller;

import io.netty.channel.Channel;
import sz.lzh.conversation.bean.reply.ResponseReply;
import sz.lzh.conversation.config.ActionConfig;
import sz.lzh.conversation.util.coder.JsonEncoder;
import sz.lzh.conversation.util.connect.ChannelController;

public class Informs {

	//成功应答
	public static void successInform(Channel channel,String username,int requestProtocol){
		channel.writeAndFlush(new JsonEncoder().getByteEncoder(
				new ResponseReply(requestProtocol, username, ActionConfig.RESPONSE_SECUSS, ActionConfig.NOEXCEPTION,null)));
	}
	//用户名错误
	public static void errorUsernameInform(Channel channel,String username,int requestProtocol){
		channel.writeAndFlush(new JsonEncoder().getByteEncoder(
				new ResponseReply(requestProtocol, username, ActionConfig.RESPONSE_FAIL, ActionConfig.IDEXCEPTION,null)));
	}
	//密码错误
	public static void errorPasswordInform(Channel channel,String username,int requestProtocol){
		channel.writeAndFlush(new JsonEncoder().getByteEncoder(
				new ResponseReply(requestProtocol, username, ActionConfig.RESPONSE_FAIL, ActionConfig.PDEXCEPTION,null)));
	}
	//数据错误
	public static void errorDataInform(Channel channel,String username,int requestProtocol){
		channel.writeAndFlush(new JsonEncoder().getByteEncoder(
				new ResponseReply(requestProtocol, username, ActionConfig.RESPONSE_FAIL, ActionConfig.DATAEXCEPTION,null)));
	}
	//未进组
	public static void notGroupInform(Channel channel,String username,int requestProtocol){
		channel.writeAndFlush(new JsonEncoder().getByteEncoder(
				new ResponseReply(requestProtocol, username, ActionConfig.RESPONSE_FAIL, ActionConfig.NOGROUPNOEXCEPTION,null)));
	}
	//无权限
	public static void notPromission(Channel channel,String username,int requestProtocol){
		channel.writeAndFlush(new JsonEncoder().getByteEncoder(
				new ResponseReply(requestProtocol, username, ActionConfig.RESPONSE_FAIL, ActionConfig.NOPROMIISIONEXCEPTION,null)));
	}
	
}
