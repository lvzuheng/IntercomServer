package com.byanda.test.userTest.testClient;

import java.util.List;

import com.alibaba.fastjson.JSON;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.bytes.ByteArrayDecoder;
import sz.lzh.conversation.bean.receive.GroupRequest;
import sz.lzh.conversation.bean.receive.P2PIntercomResponse;
import sz.lzh.conversation.bean.reply.P2PIntercomRequestReply;
import sz.lzh.conversation.service.ActionManager;

public class ClientTestDecoder extends ByteArrayDecoder{

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
//			groupRequest.setUsername("zdm");
//			ctx.writeAndFlush((JSON.toJSONString(groupRequest)+"\r\n").getBytes());
//		}
		if(JSON.parseObject(new String(buf)).getInteger("pid") == 7012){
			P2PIntercomResponse p2pIntercomResponse = new P2PIntercomResponse(6013, "zdm", JSON.parseObject(new String(buf)).getString("applicant"), true);
			ctx.writeAndFlush((JSON.toJSONString(p2pIntercomResponse)+"\r\n").getBytes());
		}
	}
}
