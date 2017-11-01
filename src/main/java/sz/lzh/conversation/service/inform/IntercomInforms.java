package sz.lzh.conversation.service.inform;

import java.util.List;

import io.netty.channel.Channel;
import sz.lzh.conversation.bean.receive.IntercomRequest;
import sz.lzh.conversation.bean.reply.IntercomRequestReply;
import sz.lzh.conversation.bean.reply.P2PIntercomRequestReply;
import sz.lzh.conversation.bean.reply.P2PIntercomResponseReply;
import sz.lzh.conversation.bean.reply.StreamReply;
import sz.lzh.conversation.config.ActionConfig;
import sz.lzh.conversation.util.Group.Interlocutor;
import sz.lzh.conversation.util.coder.JsonEncoder;
import sz.lzh.conversation.util.connect.ChannelController;

public class IntercomInforms {
	
	public static void openInform(String username,String interlocutor){
		ChannelController.getChannel(username).writeAndFlush(new JsonEncoder().getByteEncoder(
				new IntercomRequestReply(ActionConfig.INTERCOMREQUEST, username, interlocutor, ActionConfig.OPENINTERCOM)));
	}
	public static void openInform(Interlocutor interlocutor,String iUser){
		interlocutor.getChannel().writeAndFlush(new JsonEncoder().getByteEncoder(
				new IntercomRequestReply(ActionConfig.INTERCOMREQUEST, interlocutor.getUsername(), iUser, ActionConfig.OPENINTERCOM)));
	}
	public static void openInform(List<Interlocutor> iList,String interlocutor){
		for(int i=0;i<iList.size();i++)
			openInform(iList.get(i),interlocutor);
		
	}
	public static void closeInform(Interlocutor interlocutor,String iUser){
		interlocutor.getChannel().writeAndFlush(new JsonEncoder().getByteEncoder(
				new IntercomRequestReply(ActionConfig.INTERCOMREQUEST, interlocutor.getUsername(), iUser, ActionConfig.CLOSEINTERCOM)));
	}
	public static void closeInform(List<Interlocutor> iList,String interlocutor){
		for(int i=0;i<iList.size();i++)
			closeInform(iList.get(i),interlocutor);
		
	}
	
	public static void P2POpenReqestInform(Interlocutor interlocutor,String iUser){
		interlocutor.getChannel().writeAndFlush(new JsonEncoder().getByteEncoder(
				new P2PIntercomRequestReply(ActionConfig.P2PINTERCOMREQUEST, interlocutor.getUsername(), iUser, ActionConfig.OPENINTERCOM)));
	}
	public static void P2PCloseReqestInform(Interlocutor interlocutor,String iUser){
		interlocutor.getChannel().writeAndFlush(new JsonEncoder().getByteEncoder(
				new P2PIntercomRequestReply(ActionConfig.P2PINTERCOMREQUEST, interlocutor.getUsername(), iUser, ActionConfig.CLOSEINTERCOM)));
	}
	public static void P2POpenResponseInform(Interlocutor interlocutor,String iUser){
		interlocutor.getChannel().writeAndFlush(new JsonEncoder().getByteEncoder(
				new P2PIntercomResponseReply(ActionConfig.P2PINTERCOMREPLY, interlocutor.getUsername(), iUser ,ActionConfig.OPENINTERCOM)));
	}
	public static void P2PCloseResponseInform(Interlocutor interlocutor,String iUser){
		interlocutor.getChannel().writeAndFlush(new JsonEncoder().getByteEncoder(
				new P2PIntercomResponseReply(ActionConfig.P2PINTERCOMREPLY, interlocutor.getUsername(), iUser ,ActionConfig.CLOSEINTERCOM)));
	}
	
	public static void P2PStreamInform(Interlocutor interlocutor,byte[] data){
			interlocutor.getP2Pinterlocutor().getChannel().writeAndFlush(new JsonEncoder().getByteEncoder(
					new StreamReply(ActionConfig.P2PSTREAM,interlocutor.getP2Pinterlocutor().getUsername(),interlocutor.getUsername(), data)));
	}

	public static void StreamInform(List<Interlocutor> iList,String iUser,byte[] data){
		for(int i=0;i<iList.size();i++)
			StreamInform(iList.get(i),iUser,data);
	}
	public static void StreamInform(Interlocutor interlocutor,String iUser,byte[] data){
		if(!interlocutor.getP2PStatus())//判断是否在点对点通话状态下
			interlocutor.getChannel().writeAndFlush(new JsonEncoder().getByteEncoder(
					new StreamReply(ActionConfig.STREAMREPLY, interlocutor.getUsername(),iUser, data)));
	}
}
