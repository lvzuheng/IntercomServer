package com.byanda.test.userTest.testClient;


import com.lzh.net.handler.TcpHandler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.bytes.ByteArrayEncoder;
import io.netty.handler.timeout.IdleStateHandler;

public class ClientTestHandler extends TcpHandler{

	@Override
	public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
		// TODO Auto-generated method stub
		super.channelRegistered(ctx);
		ctx.pipeline().addFirst(new IdleStateHandler(10,10,10));
		ctx.pipeline().addFirst(new ByteArrayEncoder());
		ctx.pipeline().addLast(new LineBasedFrameDecoder(100*1024));
		ctx.pipeline().addLast(new ClientTestDecoder());
//		ctx.pipeline().addLast( new LoggingHandler(LogLevel.INFO));
	}
	
	
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		// TODO Auto-generated method stub
		super.channelActive(ctx);
		System.out.println("与服务端["+ctx.channel().remoteAddress()+"]建立连接");
		WriteMessageToClient writeMessageToClient = new WriteMessageToClient();
		writeMessageToClient.write(ctx.channel());
	}
	
	@Override
	public void setAllIdleState(ChannelHandlerContext ctx) {
		// TODO Auto-generated method stub
		super.setAllIdleState(ctx);
//		ChannelController.remove(ctx.channel());
//		ctx.close();
	}
	
	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		// TODO Auto-generated method stub
		super.channelInactive(ctx);
		System.out.println("与服务端["+ctx.channel().remoteAddress()+"]断开链接");
	}
}
