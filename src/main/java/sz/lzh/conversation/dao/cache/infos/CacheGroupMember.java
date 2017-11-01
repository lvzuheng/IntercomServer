package sz.lzh.conversation.dao.cache.infos;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sz.lzh.conversation.config.ActionConfig;
import sz.lzh.conversation.dao.cache.CacheInfo;
import sz.lzh.conversation.dao.cache.impl.CacheDataImpl;
import sz.lzh.conversation.dao.config.CacheConfig;
import sz.lzh.conversation.dao.domain.impl.SqlManagerImpl;
import sz.lzh.conversation.dao.domain.infos.GroupMember;




@Service
public class CacheGroupMember extends CacheInfo<GroupMember>{

	
	@Autowired
	private CacheDataImpl cacheDataImpl;
	@Autowired
	private SqlManagerImpl sqlManagerImpl;
	
	public CacheGroupMember() {
		// TODO Auto-generated constructor stub
	}
	
	public void init(){
		update(sqlManagerImpl.search(GroupMember.class));
	}

	@Override
	public void update(List<GroupMember> list) {
		// TODO Auto-generated method stub
		if(list == null) return;
		for(int i=0;i<list.size();i++)
			update(list.get(i));
	}

	@Override
	public void update(GroupMember groupMember) {
		// TODO Auto-generated method stub
		cacheDataImpl.save(CacheConfig.GROUPMEMBER_USERNAME, String.valueOf(groupMember.getId()),groupMember.getUsername());
		cacheDataImpl.save(CacheConfig.GROUPMEMBER_GROUPID,String.valueOf(groupMember.getId()),String.valueOf(groupMember.getGroupId()));
		cacheDataImpl.save(CacheConfig.GROUPMEMBER_GRADE, String.valueOf(groupMember.getId()),String.valueOf(groupMember.getGrade()));
	}

	@Override
	public void remove(List<GroupMember> list) {
		// TODO Auto-generated method stub
		for(int i = 0;i<list.size();i++)
			remove(list.get(i));
	}

	@Override
	public void remove(GroupMember groupMember) {
		// TODO Auto-generated method stub
		cacheDataImpl.del(CacheConfig.GROUPMEMBER_USERNAME, String.valueOf(groupMember.getId()));
		cacheDataImpl.del(CacheConfig.GROUPMEMBER_GROUPID, String.valueOf(groupMember.getId()));
		cacheDataImpl.del(CacheConfig.GROUPMEMBER_GRADE, String.valueOf(groupMember.getId()));
	}

	@Override
	protected void setData(GroupMember t) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void clean() {
		// TODO Auto-generated method stub
		cacheDataImpl.del(CacheConfig.GROUPMEMBER_USERNAME);//数据库id
		cacheDataImpl.del(CacheConfig.GROUPMEMBER_GROUPID);//
		cacheDataImpl.del(CacheConfig.GROUPMEMBER_GRADE);
	}
	
	public void removeGroupId(String userName){
		List<String> idList = cacheDataImpl.getField(CacheConfig.GROUPMEMBER_USERNAME, userName);
		String[] s = new String[idList.size()];
		idList.toArray(s);
		cacheDataImpl.del(CacheConfig.GROUPMEMBER_GROUPID,s);
	}
	public Set<Integer> getGroupId(String userName){
		List<String> uList =  cacheDataImpl.getField(CacheConfig.GROUPMEMBER_USERNAME, userName);
		Set<Integer> set = new HashSet<Integer>();
		for(String Id: uList){
			set.add(Integer.valueOf(cacheDataImpl.getValue(CacheConfig.GROUPMEMBER_GROUPID, Id)));
		}
		return set;
	}
	public void removeGroupUsername(String Group){
		List<String> gList = cacheDataImpl.getField(CacheConfig.GROUPMEMBER_USERNAME, Group);
		String[] s = null;
		cacheDataImpl.del(CacheConfig.GROUPMEMBER_USERNAME, gList.toArray(s));
	}
	public List<String> getGroupUsername(int GroupId){
		List<String> gList =  cacheDataImpl.getField(CacheConfig.GROUPMEMBER_GROUPID, String.valueOf(GroupId));
		List<String> uList = new ArrayList<String>();
		for(String Id: gList)
			uList.add(cacheDataImpl.getValue(CacheConfig.GROUPMEMBER_USERNAME, Id));
		return uList;
	}
	public Set<String> getUniqueGroupUsername(int GroupId){
		List<String> gList =  cacheDataImpl.getField(CacheConfig.GROUPMEMBER_GROUPID, String.valueOf(GroupId));
		Set<String> uSet = new HashSet<String>();
		for(String Id: gList)
			uSet.add(cacheDataImpl.getValue(CacheConfig.GROUPMEMBER_USERNAME, Id));
		
		return uSet;
	}
	public int getGrade(String username,int GroupId){
		List<String> gList =  cacheDataImpl.getField(CacheConfig.GROUPMEMBER_GROUPID, String.valueOf(GroupId));
		for(String Id: gList)
			if(cacheDataImpl.getValue(CacheConfig.GROUPMEMBER_USERNAME, Id) .equals(username)){
				return Integer.valueOf(cacheDataImpl.getValue(CacheConfig.GROUPMEMBER_GRADE, Id));
			}
		return -1;
	}
	
	public void removeByUsername(String userName){
		List<String> uList =  cacheDataImpl.getField(CacheConfig.GROUPMEMBER_USERNAME, userName);
		for(String id:uList){
			cacheDataImpl.del(CacheConfig.GROUPMEMBER_USERNAME,id);//数据库id
			cacheDataImpl.del(CacheConfig.GROUPMEMBER_GROUPID,id);//
			cacheDataImpl.del(CacheConfig.GROUPMEMBER_GRADE,id);
		}
	}
}
