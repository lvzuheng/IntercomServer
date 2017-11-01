package sz.lzh.conversation.dao.cache.infos;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sz.lzh.conversation.dao.cache.CacheInfo;
import sz.lzh.conversation.dao.cache.impl.CacheDataImpl;
import sz.lzh.conversation.dao.config.CacheConfig;
import sz.lzh.conversation.dao.domain.impl.SqlManagerImpl;
import sz.lzh.conversation.dao.domain.infos.User;

@Service
public class CacheUser extends CacheInfo<User>{

	@Autowired
	private CacheDataImpl cacheDataImpl;
	@Autowired
	private SqlManagerImpl sqlManagerImpl;
	
	public CacheUser() {
		// TODO Auto-generated constructor stub
//		init();
	}
	
	public void init(){
		update(sqlManagerImpl.search(User.class));
	}

	@Override
	public void update(List<User> list) {
		// TODO Auto-generated method stub
		if(list == null) return;
		for(int i=0;i<list.size();i++)
			update(list.get(i));
	}

	@Override
	public void update(User user) {
		// TODO Auto-generated method stub
		cacheDataImpl.save(CacheConfig.USER_ID, user.getUsername(), String.valueOf(user.getId()));
		cacheDataImpl.save(CacheConfig.USER_NICKNAME, user.getUsername(), user.getNickname());
		cacheDataImpl.save(CacheConfig.USER_PASSWORD, user.getUsername(), user.getPassword());
	}

	@Override
	public void remove(List<User> list) {
		// TODO Auto-generated method stub
		for(int i = 0;i<list.size();i++)
			remove(list.get(i));
	}

	@Override
	public void remove(User user) {
		// TODO Auto-generated method stub
		cacheDataImpl.del(CacheConfig.USER_ID,user.getUsername());//数据库id
		cacheDataImpl.del(CacheConfig.USER_NICKNAME, user.getUsername());//用户名
		cacheDataImpl.del(CacheConfig.USER_PASSWORD, user.getUsername());//密码
	}

	@Override
	protected void setData(User t) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void clean() {
		// TODO Auto-generated method stub
		cacheDataImpl.del(CacheConfig.USER_ID);//数据库id
		cacheDataImpl.del(CacheConfig.USER_NICKNAME);//用户名
		cacheDataImpl.del(CacheConfig.USER_PASSWORD);//密码
	}
	
	public void removeNickName(String field){
		cacheDataImpl.del(CacheConfig.USER_NICKNAME, field);
	}
	public void removePassWord(String field){
		cacheDataImpl.del(CacheConfig.USER_PASSWORD, field);
	}
	public String getNickName(String field){
		return cacheDataImpl.getValue(CacheConfig.USER_NICKNAME, field);
	}
	public String getPassWord(String field){
		return cacheDataImpl.getValue(CacheConfig.USER_PASSWORD, field);
	}
	
	public Map<String, String> getUsers(){
		return cacheDataImpl.getMap(CacheConfig.USER_NICKNAME);
	}
	
	public boolean isExist(String field){
		return cacheDataImpl.isExist(CacheConfig.USER_ID,field);
	}
	
	public void removeByUsername(String userName) {
		// TODO Auto-generated method stub
		cacheDataImpl.del(CacheConfig.USER_ID,userName);//数据库id
		cacheDataImpl.del(CacheConfig.USER_NICKNAME, userName);//用户名
		cacheDataImpl.del(CacheConfig.USER_PASSWORD, userName);//密码
	}
	
}
