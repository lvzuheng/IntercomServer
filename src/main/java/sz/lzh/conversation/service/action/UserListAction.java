package sz.lzh.conversation.service.action;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import io.netty.channel.ChannelHandlerContext;
import sz.lzh.conversation.bean.receive.UserList;
import sz.lzh.conversation.bean.reply.UserListReply;
import sz.lzh.conversation.config.ActionConfig;
import sz.lzh.conversation.dao.cache.infos.CacheGroupInfo;
import sz.lzh.conversation.dao.cache.infos.CacheGroupMember;
import sz.lzh.conversation.service.ActionManager;
import sz.lzh.conversation.util.Group.Group;
import sz.lzh.conversation.util.Group.GroupManager;
import sz.lzh.conversation.util.Group.Interlocutor;
import sz.lzh.conversation.util.Group.InterlocutorManager;
import sz.lzh.conversation.util.coder.JsonEncoder;
import sz.lzh.conversation.util.connect.ChannelController;

/**
 * 请求用户列表
 * 
 * 未测试~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 * 
 * */

@Component("6006")
public class UserListAction extends ActionManager{

	@Autowired
	private CacheGroupMember cacheGroupMember;
	@Autowired
	private CacheGroupInfo cacheGroupInfo;

	@Override
	public void excute(String msg, ChannelHandlerContext ctx) {
		super.excute( msg, ctx);
		// TODO Auto-generated method stub
		UserList userList = JSON.parseObject(msg, UserList.class);
		System.out.println("设备["+userList.getUsername()+"]过来乞求成员列表");
		List<UserListReply.User> list = new ArrayList<UserListReply.User>();
		Set<Integer> groupSet =cacheGroupMember.getGroupId(userList.getUsername());
		Set<String> set =new  HashSet<String>();
		for(int groupId:groupSet){
			set.addAll(cacheGroupMember.getUniqueGroupUsername(groupId));
		}
		for(String username:set){
			Interlocutor interlocutor = InterlocutorManager.getInstance().getInterlocutor(username);
			list.add(new UserListReply.User(
						username,
						(interlocutor==null || interlocutor.getGroup()==null)?ActionConfig.NOTINGROUP:interlocutor.getGroup().getGroupId(), 
						(interlocutor==null || interlocutor.getGroup()==null)?null:interlocutor.getGroup().getGroupName(),
										ChannelController.isExist(username)
						)
					);

		}

		UserListReply userListReply = new UserListReply(ActionConfig.USERLIST,userList.getUsername(),list);

		ctx.writeAndFlush(new JsonEncoder().getByteEncoder(userListReply));
	}

}
