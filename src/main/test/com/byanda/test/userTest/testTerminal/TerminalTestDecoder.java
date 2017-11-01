package com.byanda.test.userTest.testTerminal;

import java.util.List;

import com.alibaba.fastjson.JSON;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.bytes.ByteArrayDecoder;
import sz.lzh.conversation.bean.receive.GroupList;
import sz.lzh.conversation.bean.receive.GroupRequest;
import sz.lzh.conversation.bean.receive.IntercomRequest;
import sz.lzh.conversation.bean.receive.ModifyNickName;
import sz.lzh.conversation.bean.receive.ModifyPassword;
import sz.lzh.conversation.bean.receive.P2PIntercomRequest;
import sz.lzh.conversation.bean.receive.Stream;
import sz.lzh.conversation.bean.receive.UserList;
import sz.lzh.conversation.service.ActionManager;
import sz.lzh.conversation.service.action.P2PStreamAction;
import sz.lzh.conversation.util.Group.Interlocutor;

public class TerminalTestDecoder extends ByteArrayDecoder{

	
	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf msg, List<Object> out) throws Exception {
		// TODO Auto-generated method stub
		super.decode(ctx, msg, out);
		byte[] buf=new byte[msg.readableBytes()];
		msg.readBytes(buf);
		System.out.println(new String(buf));
//		if(JSON.parseObject(new String(buf)).getInteger("pid") == 7001){
//			GroupRequest groupRequest = new GroupRequest();
//			groupRequest.setAction(1);
//			groupRequest.setPid(6008);
//			groupRequest.setGroupId(11);
//			groupRequest.setUsername("ly");
//			ctx.writeAndFlush((JSON.toJSONString(groupRequest)+"\r\n").getBytes());
//		}
//		if(JSON.parseObject(new String(buf)).getInteger("pid") == 7008){
//			IntercomRequest intercomRequest = new IntercomRequest();
//			intercomRequest.setAction(true);
//			intercomRequest.setPid(6010);
//			intercomRequest.setUsername("ly");
//			ctx.writeAndFlush((JSON.toJSONString(intercomRequest)+"\r\n").getBytes());
//		}
//		if(JSON.parseObject(new String(buf)).getInteger("pid") == 7010){
//			Stream stream = new Stream();
//			stream.setPid(6011);
//			stream.setUsername("ly");
//			stream.setStream(new byte[]{1,2,3,4,3,5,8,5,2,6,5,7,8,9,5,2,54,6,3,2,5,6,2,5});
//			for(int i =0;i<20;i++)
//				ctx.writeAndFlush((JSON.toJSONString(stream)+"\r\n").getBytes());
//		}
		if(JSON.parseObject(new String(buf)).getInteger("pid") == 7001){
			P2PIntercomRequest p2pIntercomRequest = new P2PIntercomRequest(6012, "ly", "zdm", true);
			ctx.writeAndFlush((JSON.toJSONString(p2pIntercomRequest)+"\r\n").getBytes());
		}
		if(JSON.parseObject(new String(buf)).getInteger("pid") == 7013){
			Stream stream = new Stream();
			stream.setPid(6014);
			stream.setUsername("ly");
			stream.setStream(new byte[]{1,2,3,4,3,5,8,5,2,6,5,7,8,9,5,2,54,6,3,2,5,6,2,5});
			for(int i =0;i<20;i++)
				ctx.writeAndFlush((JSON.toJSONString(stream)+"\r\n").getBytes());
		}
	}
}
