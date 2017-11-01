package sz.lzh.conversation.service.action;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sz.lzh.conversation.bean.reply.UserInfoReply;
import sz.lzh.conversation.config.ActionConfig;
import sz.lzh.conversation.dao.cache.infos.CacheGroupMember;
import sz.lzh.conversation.dao.cache.infos.CacheUser;
import sz.lzh.conversation.util.Group.GroupManager;
import sz.lzh.conversation.util.Group.Interlocutor;
import sz.lzh.conversation.util.Group.InterlocutorManager;
import sz.lzh.conversation.util.coder.JsonEncoder;
import sz.lzh.conversation.util.connect.ChannelController;
import sz.lzh.conversation.util.manager.ApplicationUtil;
import sz.lzh.conversation.util.manager.CacheThreadManager;

@Service
public class UserOnOffLineAction {

	public static void sendUserOnOffLineMessage(final String username,final boolean status){
		CacheThreadManager.getInstance().getCachedThreadPool().execute(new Runnable() {

			public void run() {
				// TODO Auto-generated method stub
				CacheGroupMember cacheGroupMember = ApplicationUtil.getApplicationContext().getBean(CacheGroupMember.class);
				Set<Integer> iSet = cacheGroupMember.getGroupId(username);
				Set<String> uSet = new HashSet<String>();
				for(int i : iSet)
					for(String user:cacheGroupMember.getUniqueGroupUsername(i)){
						if(!uSet.contains(user) && !user.equals(username)  &&  ChannelController.isExist(user)){
							uSet.add(user);
							Interlocutor interlocutor = InterlocutorManager.getInstance().getInterlocutor(user);
							ChannelController.getChannel(user).writeAndFlush(new JsonEncoder().getByteEncoder(
									new UserInfoReply(ActionConfig.ONOFFLINEINFOS, user,username,
											interlocutor.getGroup()==null?ActionConfig.NOTINGROUP:interlocutor.getGroup().getGroupId(),
											interlocutor.getGroup()==null?null:interlocutor.getGroup().getGroupName(),
											status
											)));
						} 
					}
			}
		});
	}

}
