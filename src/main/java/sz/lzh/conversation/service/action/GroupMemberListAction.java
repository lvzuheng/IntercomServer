package sz.lzh.conversation.service.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;

import io.netty.channel.ChannelHandlerContext;
import sz.lzh.conversation.bean.receive.GroupMemberList;
import sz.lzh.conversation.bean.reply.GroupInfosReply;
import sz.lzh.conversation.bean.reply.GroupMemberListReply;
import sz.lzh.conversation.bean.reply.GroupRequestReply;
import sz.lzh.conversation.config.ActionConfig;
import sz.lzh.conversation.dao.cache.infos.CacheGroupInfo;
import sz.lzh.conversation.dao.cache.infos.CacheGroupMember;
import sz.lzh.conversation.service.ActionManager;
import sz.lzh.conversation.service.inform.GroupInforms;
import sz.lzh.conversation.service.inform.Informs;
import sz.lzh.conversation.util.Group.GroupManager;
import sz.lzh.conversation.util.Group.Interlocutor;
import sz.lzh.conversation.util.Group.InterlocutorManager;
import sz.lzh.conversation.util.coder.JsonEncoder;

@Component("6009")
public class GroupMemberListAction extends ActionManager{

	@Autowired
	private CacheGroupMember cacheGroupMember;
	@Autowired
	private CacheGroupInfo cacheGroupInfo;
	
	@Override
	public void excute(String msg, ChannelHandlerContext ctx) {
		// TODO Auto-generated method stub
		super.excute(msg, ctx);
		GroupMemberList groupMemberListRequest =JSONObject.parseObject(msg, GroupMemberList.class);
		List<String> nList = cacheGroupMember.getGroupUsername(groupMemberListRequest.getGroupId());//获取到组内成员名字
		if(nList ==null || !nList.contains(groupMemberListRequest.getUsername())){
			Informs.errorUsernameInform(ctx.channel(),groupMemberListRequest.getUsername(), ActionConfig.GROUPMEMBERLIST);
			return;
		}
		
		List<GroupMemberListReply.User> list = new ArrayList<GroupMemberListReply.User>();
		
		Set<String> iList = GroupManager.getInstance().getGroup(groupMemberListRequest.getGroupId()).getMemberNameList();
		
		if(iList!=null){
			for(String username:nList){
				list.add(new GroupMemberListReply.User(
						username, //用户名
						iList.contains(username), //是否正在组内
						iList.contains(username)?InterlocutorManager.getInstance().getInterlocutor(username).getIntercomStatus():false//对讲状态
								)
						);
			}
		}
				
		GroupInforms.GroupMemberListInfrom(ctx.channel(), groupMemberListRequest.getUsername(), groupMemberListRequest.getGroupId(), cacheGroupInfo.getGroupName(groupMemberListRequest.getGroupId()), list);	
	}
}
