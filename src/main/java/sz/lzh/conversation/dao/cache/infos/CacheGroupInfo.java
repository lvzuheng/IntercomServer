package sz.lzh.conversation.dao.cache.infos;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sz.lzh.conversation.dao.cache.CacheInfo;
import sz.lzh.conversation.dao.cache.impl.CacheDataImpl;
import sz.lzh.conversation.dao.config.CacheConfig;
import sz.lzh.conversation.dao.domain.impl.SqlManagerImpl;
import sz.lzh.conversation.dao.domain.infos.GroupInfo;

@Service
public class CacheGroupInfo  extends CacheInfo<GroupInfo>{

	@Autowired
	private CacheDataImpl cacheDataImpl;
	@Autowired
	private SqlManagerImpl sqlManagerImpl;
	
	public CacheGroupInfo() {
		// TODO Auto-generated constructor stub
//		init();
	}
	
	@Override
	public void init() {
		// TODO Auto-generated method stub
		update(sqlManagerImpl.search(GroupInfo.class));
	}

	@Override
	public void update(List<GroupInfo> list) {
		// TODO Auto-generated method stub
		if(list == null) return;
		for(int i=0;i<list.size();i++)
			update(list.get(i));
	}

	@Override
	public void update(GroupInfo groupInfo) {
		// TODO Auto-generated method stub
		cacheDataImpl.save(CacheConfig.GROUP_NAME, String.valueOf(groupInfo.getId()),groupInfo.getName());
		cacheDataImpl.save(CacheConfig.GROUP_OWNER,  String.valueOf(groupInfo.getId()), groupInfo.getOwner());
	}

	@Override
	public void remove(List<GroupInfo> list) {
		// TODO Auto-generated method stub
		for(int i=0;i<list.size();i++)
			remove(list.get(i));
	}

	@Override
	public void remove(GroupInfo groupInfo) {
		// TODO Auto-generated method stub
		cacheDataImpl.del(CacheConfig.GROUP_NAME, String.valueOf(groupInfo.getId()));
		cacheDataImpl.del(CacheConfig.GROUP_OWNER, String.valueOf(groupInfo.getId()));
	}

	@Override
	protected void setData(GroupInfo t) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void clean() {
		// TODO Auto-generated method stub
		cacheDataImpl.del(CacheConfig.GROUP_NAME);//数据库id
		cacheDataImpl.del(CacheConfig.GROUP_OWNER);//用户名
	}
	
	public Map<String, String> getGroup(){
		return cacheDataImpl.getMap(CacheConfig.GROUP_NAME);
	}
	
	public void removeGroupName(String field){
		cacheDataImpl.del(CacheConfig.GROUP_NAME, field);
	}
	public void removeGroupOwner(String field){
		cacheDataImpl.del(CacheConfig.GROUP_OWNER, field);
	}
	
	public Set<String> getGroupId(String groupName){
		return cacheDataImpl.getField(groupName);
	}
	
	public String getGroupName(int groupId){
		return cacheDataImpl.getValue(CacheConfig.GROUP_NAME, String.valueOf(groupId));
	}
	public String getGroupOwner(String field){
		return cacheDataImpl.getValue(CacheConfig.GROUP_OWNER, field);
	}
	
	public boolean isExist(String field){
		return cacheDataImpl.isExist(CacheConfig.GROUP_NAME, field);
	}
	public boolean isExist(int field){
		return cacheDataImpl.isExist(CacheConfig.GROUP_NAME, String.valueOf(field));
	}
}
