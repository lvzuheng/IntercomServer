package sz.lzh.conversation.util.connect;


import java.util.HashMap;
import java.util.Map;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import sz.lzh.conversation.bean.reply.GroupListReply.Group;
import sz.lzh.conversation.config.MemoryConfig;
import sz.lzh.conversation.dao.cache.impl.CacheDataImpl;
import sz.lzh.conversation.service.action.UserOnOffLineAction;
import sz.lzh.conversation.util.Group.InterlocutorManager;
import sz.lzh.conversation.util.manager.ApplicationUtil;

public class ChannelController  {
	
	private static CacheDataImpl cacheDataImpl = ApplicationUtil.getBean(CacheDataImpl.class);
	
	private static Map<String, Channel> ctxMap = new HashMap<String, Channel>();
	private static Map<Channel, String> userMap = new HashMap<Channel, String>();
	
	
	private static ChannelController channelController  = new ChannelController();
	public static ChannelController getInstance() {
		if(channelController == null){
			channelController = new ChannelController();
		}
		return channelController;
	}
	
	public static void putChannel(String uid,Channel channel){
		ctxMap.put(uid, channel);
		userMap.put(channel, uid);
		UserOnOffLineAction.sendUserOnOffLineMessage(uid, true);
	}
	
	
	public static  Channel getChannel(String key) {
		return ctxMap.get(key);
	}
	
	public static  String getChannelUid(ChannelHandlerContext ctx) {
		return userMap.get(ctx);
	}
	
	public static void remove(String key) {
		userMap.remove(ctxMap.get(key));
		ctxMap.remove(key);
		cacheDataImpl.del(MemoryConfig.ONLINEUSER, key);
		UserOnOffLineAction.sendUserOnOffLineMessage(key, false);
		InterlocutorManager.getInstance().removeInterlocutor(key);
	}
	
	public static void remove(Channel channel) {
		if(!userMap.containsKey(channel))
			return;
		String user = userMap.get(channel);
		remove(user);
	}
	
	public static boolean isExist(String key){
		return ctxMap.containsKey(key);
	}
	public static boolean isExist(Channel ctx){
		return userMap.containsKey(ctx);
	}
	
	public static boolean isExist(String key,Channel ctx){
		if(ctxMap.get(key)!= null  && ctxMap.get(key).equals(ctx))		
			return true;
		return false;
	}
	
}
