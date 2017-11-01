package sz.lzh.conversation.service.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.alibaba.fastjson.JSONObject;
import io.netty.channel.ChannelHandlerContext;
import sz.lzh.conversation.bean.receive.ModifyPassword;
import sz.lzh.conversation.bean.reply.ModifyPasswordReply;
import sz.lzh.conversation.config.ActionConfig;
import sz.lzh.conversation.dao.cache.infos.CacheUser;
import sz.lzh.conversation.dao.config.CacheConfig;
import sz.lzh.conversation.dao.domain.impl.SqlManagerImpl;
import sz.lzh.conversation.dao.domain.infos.User;
import sz.lzh.conversation.service.ActionManager;
import sz.lzh.conversation.service.inform.Informs;
import sz.lzh.conversation.util.coder.JsonEncoder;

/**
 * 修改密码
 * 
 * 未验证~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 * */
@Component("6005")
public class ModifyPasswordAction extends ActionManager{


	@Autowired
	private SqlManagerImpl sqlManagerImpl;
	@Autowired
	private CacheUser cacheUser;

	@Override
	public void excute(String msg, ChannelHandlerContext ctx) {
		super.excute(msg, ctx);		
		// TODO Auto-generated method stub
		ModifyPassword modifyPassword = JSONObject.parseObject(msg, ModifyPassword.class);
		System.out.println("设备["+modifyPassword.getUsername()+"]过来跪求修改密码");
		
		if(!cacheUser.getPassWord(modifyPassword.getUsername()).equals(modifyPassword.getPassword())){
			Informs.errorPasswordInform(ctx.channel(), modifyPassword.getUsername(), ActionConfig.MODIFYPASSWORD);
			return;
		}
		//返回修改密码同时修改缓存区的结果
		if(!sqlManagerImpl.updateByClassAndUpdataCache(User.class,"password", modifyPassword.getNewPassword()
				,"username",modifyPassword.getUsername(), CacheConfig.USER_PASSWORD,modifyPassword.getUsername(), modifyPassword.getNewPassword())){
			Informs.errorDataInform(ctx.channel(), modifyPassword.getUsername(), ActionConfig.MODIFYPASSWORD);
			return;
		}
		
		Informs.successInform(ctx.channel(), modifyPassword.getUsername(),ActionConfig.MODIFYPASSWORD);
		System.out.println("勉为其难地帮设备["+modifyPassword.getUsername()+"]修改了密码");
	}


}
