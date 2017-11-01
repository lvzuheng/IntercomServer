package sz.lzh.conversation.service;





import com.alibaba.fastjson.JSON;

import io.netty.channel.ChannelHandlerContext;
import sz.lzh.conversation.bean.CodeInfo;
import sz.lzh.conversation.util.connect.ChannelController;
import sz.lzh.conversation.util.manager.ApplicationUtil;


public abstract class ActionManager{

	public void excute(String uid,String msg,ChannelHandlerContext ctx){
		//一种把不知名的、不怀好意的、不知所措的、图谋不轨的外来连接以迅雷不及掩耳盗铃儿响叮当仁不让的姿势断开的防入侵防火墙机制
		if(!ChannelController.isExist(uid,ctx.channel())){
			ctx.close();
			return;
		}
		excute(msg, ctx);
	}
	public  void excute(String msg,ChannelHandlerContext ctx){
		//一种把不知名的、不怀好意的、不知所措的、图谋不轨的外来连接以打雷下雨来不及收衣服之势断开的防入侵防火墙机制
	}
}
