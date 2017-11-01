package com.byanda.test.userTest.testTerminal;


import com.lzh.net.handler.TcpHandler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.bytes.ByteArrayEncoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.IdleStateHandler;

public class TerminalTestHandler extends TcpHandler{
	@Override
	public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
		// TODO Auto-generated method stub
		super.channelRegistered(ctx);
		ctx.pipeline().addFirst(new IdleStateHandler(10,10,10));
		ctx.pipeline().addFirst(new ByteArrayEncoder());
		ctx.pipeline().addLast(new LineBasedFrameDecoder(100*1024));
		ctx.pipeline().addLast(new TerminalTestDecoder());
//		ctx.pipeline().addLast( new LoggingHandler(LogLevel.INFO));
	}
	
	
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		// TODO Auto-generated method stub
		super.channelActive(ctx);
		System.out.println("与服务端["+ctx.channel().remoteAddress()+"]建立连接");
		WriteMessage writeMessage = new WriteMessage();
		writeMessage.write(ctx.channel());
		
	}
	
	@Override
	public void setAllIdleState(ChannelHandlerContext ctx) {
		// TODO Auto-generated method stub
		super.setAllIdleState(ctx);
//		byte[] bs= "{\"header\":{\"version\":\"v1\",\"uid\":\"000001\",\"pid\":7030},\"body\":{\"gpsStatus\":\"A\",\"date\":\"170128 111111\",\"longitude\":\"2266\",\"latitude\":\"6622\",\"speed\":\"10\",\"direction\":\"N\",\"totalStorage\":\"\",\"valueStorage\":\"\",\"electricity\":\"\"}}\r\n".getBytes();
//		byte[] bs = TerminalTestJsonCode.Heart.getBytes();
//		ctx.writeAndFlush(bs);
	}
	
	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		// TODO Auto-generated method stub
		super.channelInactive(ctx);
		System.out.println("与服务端["+ctx.channel().remoteAddress()+"]断开链接");
	}
}
