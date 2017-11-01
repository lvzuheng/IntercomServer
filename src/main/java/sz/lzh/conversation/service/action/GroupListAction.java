package sz.lzh.conversation.service.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.sun.org.apache.bcel.internal.generic.NEW;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import sz.lzh.conversation.bean.receive.GroupList;
import sz.lzh.conversation.bean.reply.GroupListReply;
import sz.lzh.conversation.config.ActionConfig;
import sz.lzh.conversation.dao.cache.infos.CacheGroupInfo;
import sz.lzh.conversation.dao.cache.infos.CacheGroupMember;
import sz.lzh.conversation.service.ActionManager;
import sz.lzh.conversation.service.inform.GroupInforms;
import sz.lzh.conversation.util.Group.Group;
import sz.lzh.conversation.util.Group.GroupManager;
import sz.lzh.conversation.util.coder.JsonEncoder;

@Component("6007")
public class GroupListAction extends ActionManager{

	@Autowired
	private CacheGroupMember cacheGroupMember;
	@Autowired
	private CacheGroupInfo cacheGroupInfo;
	
	@Override
	public void excute(String msg, ChannelHandlerContext ctx) {
		// TODO Auto-generated method stub
		super.excute(msg, ctx);
		GroupList groupList = JSONObject.parseObject(msg, GroupList.class);
		Set<Integer> idSet = cacheGroupMember.getGroupId(groupList.getUsername());//获取到所拥有组的所有groupId
		List<GroupListReply.Group> glist = new ArrayList<GroupListReply.Group>();
		for(int i:idSet){
			Group group = GroupManager.getInstance().getGroup(i);
			glist.add(new GroupListReply.Group(i,cacheGroupInfo.getGroupName(i),
					group == null?0:group.getMemberList().size(),
					group == null?false:group.getStatus()
					));
		}
		GroupInforms.GroupListInfrom(ctx.channel(), groupList.getUsername(), glist);
	}
}
