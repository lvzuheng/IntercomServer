package sz.lzh.conversation.service.action;





import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;

import io.netty.channel.ChannelHandlerContext;
import sz.lzh.conversation.bean.receive.Login;
import sz.lzh.conversation.bean.reply.LoginReply;
import sz.lzh.conversation.bean.reply.UserListReply;
import sz.lzh.conversation.config.ActionConfig;
import sz.lzh.conversation.config.MemoryConfig;
import sz.lzh.conversation.dao.cache.impl.CacheDataImpl;
import sz.lzh.conversation.dao.cache.infos.CacheGroupMember;
import sz.lzh.conversation.dao.cache.infos.CacheUser;
import sz.lzh.conversation.service.ActionManager;
import sz.lzh.conversation.service.inform.Informs;
import sz.lzh.conversation.service.inform.UserInforms;
import sz.lzh.conversation.util.Group.Interlocutor;
import sz.lzh.conversation.util.coder.JsonEncoder;
import sz.lzh.conversation.util.connect.ChannelController;

/**
 * 设备登陆
 * 
 * */
@Component("6001")
public class LoginAction extends ActionManager{

	@Autowired
	private CacheUser cacheUser;


	@Override
	public void excute(String uid,String msg,ChannelHandlerContext ctx) {
		// TODO Auto-generated method stub
		Login login = JSON.parseObject(msg, Login.class);

		if(		//不存在用户名 
				!cacheUser.isExist(login.getUsername())||
				//密码错误
				!cacheUser.getPassWord(login.getUsername()).equals(login.getPassword())||
				//有在线用户名
				ChannelController.isExist(login.getUsername())||
				//有在线连接
				ChannelController.isExist(ctx.channel())
		){
			Informs.errorUsernameInform(ctx.channel(),login.getUsername(), ActionConfig.LOGIN_ANSWER);
			return;
		}

		//没有已登录的用户号
		String nickName = cacheUser.getNickName(login.getUsername());
		ChannelController.putChannel(login.getUsername(), ctx.channel());
		new Interlocutor(login.getUsername(), nickName,  ctx.channel());
		//返回上线成功通知
		UserInforms.loginSuccessful(ctx.channel(), login.getUsername(), nickName);
	}

}


