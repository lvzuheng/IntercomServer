package sz.lzh.conversation.service.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;

import io.netty.channel.ChannelHandlerContext;
import sz.lzh.conversation.bean.receive.ModifyNickName;
import sz.lzh.conversation.bean.reply.ModifyUserNameReply;
import sz.lzh.conversation.config.ActionConfig;
import sz.lzh.conversation.dao.cache.infos.CacheUser;
import sz.lzh.conversation.dao.config.CacheConfig;
import sz.lzh.conversation.dao.domain.impl.SqlManagerImpl;
import sz.lzh.conversation.dao.domain.infos.User;
import sz.lzh.conversation.service.ActionManager;
import sz.lzh.conversation.service.inform.Informs;
import sz.lzh.conversation.service.inform.UserInforms;
import sz.lzh.conversation.util.coder.JsonEncoder;


/**
 * 修改用户名
 * 
 * 未验证~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 * 
 * */
@Component("6004")
public class ModifyNicknameAction extends ActionManager{

	@Autowired
	private SqlManagerImpl sqlManagerImpl;
	@Autowired
	private CacheUser cacheUser;

	@SuppressWarnings({ "static-access" })
	@Override
	public void excute(String msg, ChannelHandlerContext ctx) {
		super.excute(msg, ctx);
		// TODO Auto-generated method stub
		ModifyNickName modifyNickName = new JSONObject().parseObject(msg, ModifyNickName.class);
		System.out.println("设备["+modifyNickName.getUsername()+"]过来跪求修改用户名");

		if(!cacheUser.getPassWord(modifyNickName.getUsername()).equals(modifyNickName.getPassword())){
			Informs.errorPasswordInform(ctx.channel(), modifyNickName.getUsername(), ActionConfig.MODIFYNICKNAME);
			return;
		}

		if(!sqlManagerImpl.updateByClassAndUpdataCache(User.class, "nickname", modifyNickName.getNewNickName()
				, "username",modifyNickName.getUsername(), CacheConfig.USER_NICKNAME, modifyNickName.getUsername(),  modifyNickName.getNewNickName())){
			Informs.errorDataInform(ctx.channel(), modifyNickName.getUsername(), ActionConfig.MODIFYNICKNAME);
			return;
		}

		UserInforms.modifyUsernameSuccessful(modifyNickName.getUsername(),  modifyNickName.getNewNickName());	

	}

}
