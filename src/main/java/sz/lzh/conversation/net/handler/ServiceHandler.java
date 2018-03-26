package sz.lzh.conversation.net.handler;


import org.apache.log4j.Logger;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.bytes.ByteArrayEncoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.IdleStateHandler;
import sz.lzh.LogController.server.NettyHandler;
import sz.lzh.conversation.net.coder.ServiceDecoder;
import sz.lzh.conversation.util.connect.ChannelController;

public class ServiceHandler extends NettyHandler{
	
	private static Logger logger = Logger.getLogger("ServiceHandler");
	
	@Override
	public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
		// TODO Auto-generated method stub
		super.channelRegistered(ctx);
		ctx.pipeline().addFirst(new IdleStateHandler(TIMEOUT,TIMEOUT,TIMEOUT));
		ctx.pipeline().addFirst(new ByteArrayEncoder());
//		ctx.pipeline().addLast( new LoggingHandler(LogLevel.INFO));
		ctx.pipeline().addLast(new LineBasedFrameDecoder(1000*1024));
		ctx.pipeline().addLast(new ServiceDecoder());
	}
	
	
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		// TODO Auto-generated method stub
		super.channelActive(ctx);
//		System.out.println("与["+ctx.channel().remoteAddress()+"]建立连接");
		logger.info("与["+ctx.channel().remoteAddress()+"]建立连接");
	}
	
	@Override
	public void setAllIdleState(ChannelHandlerContext ctx) {
		// TODO Auto-generated method stub
		super.setAllIdleState(ctx);
		System.out.println(ctx.channel().remoteAddress()+"超时");
		ctx.close();
	}
	
	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		// TODO Auto-generated method stub
		super.channelInactive(ctx);
		System.out.println("与"+ctx.channel().remoteAddress()+"断开链接");
		ChannelController.remove(ctx.channel());
	}
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		// TODO Auto-generated method stub
		super.channelRead(ctx, msg);
//		System.out.println(ctx.channel().remoteAddress()+"来消息了");
	}
	@Override
	public void setReadIdleState(ChannelHandlerContext ctx) {
		// TODO Auto-generated method stub
		super.setReadIdleState(ctx);
//		System.out.println("ReadIdle");
	}
	@Override
	public void setWriteIdleState(ChannelHandlerContext ctx) {
		// TODO Auto-generated method stub
		super.setWriteIdleState(ctx);
//		System.out.println("WriteIdle");
	}
//	
}
