package sz.lzh.conversation.service.inform;

import io.netty.channel.Channel;
import sz.lzh.conversation.bean.reply.LoginReply;
import sz.lzh.conversation.config.ActionConfig;
import sz.lzh.conversation.util.coder.JsonEncoder;

public class UserInforms {

	public static void loginSuccessful(Channel channel,String username,String nickname){
		channel.writeAndFlush(new JsonEncoder().getByteEncoder(
				new LoginReply(ActionConfig.LOGIN_ANSWER,username,nickname)));
	}
	public static void modifyUsernameSuccessful(String username,String nickname){
		
	}

}
