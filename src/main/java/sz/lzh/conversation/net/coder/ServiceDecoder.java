package sz.lzh.conversation.net.coder;

import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.bytes.ByteArrayDecoder;
import sz.lzh.conversation.bean.CodeInfo;
import sz.lzh.conversation.service.ActionManager;
import sz.lzh.conversation.util.manager.ApplicationUtil;




public class ServiceDecoder extends ByteArrayDecoder{
		
		@Override
		protected void decode(ChannelHandlerContext ctx, ByteBuf msg, List<Object> out) throws Exception {
			// TODO Auto-generated method stub
			super.decode(ctx, msg, out);
			byte[] buf=new byte[msg.readableBytes()];
			msg.readBytes(buf);
			try {
				getAction(new String(buf), ctx);
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
		
		public void getAction(String json,ChannelHandlerContext ctx ){
			try {
				CodeInfo codeInfo = JSON.parseObject(json,CodeInfo.class);
//				if(codeInfo.getPid()!=8000)
//					System.out.println(codeInfo.getUsername()+"发送的长度为"+json.length()+"的协议号为"+codeInfo.getPid()+"的消息:");
				ActionManager actionManager = (ActionManager) ApplicationUtil.getBean(String.valueOf(codeInfo.getPid()));
				actionManager.excute(codeInfo.getUsername(),json,ctx);
			}catch (Exception e) {
			// TODO Auto-generated catch block
				e.printStackTrace();
				ctx.writeAndFlush("请不要发我看不懂的消息，谢谢");
//				TerminalErrorAction.ExceptionHandle(json, ctx, e);
			}
		}
}
