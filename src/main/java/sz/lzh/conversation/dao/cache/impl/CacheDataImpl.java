package sz.lzh.conversation.dao.cache.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import org.hibernate.property.IndexPropertyAccessor.IndexGetter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import redis.clients.jedis.Jedis;
import sz.lzh.conversation.dao.cache.CacheManager;


@Transactional
@SuppressWarnings("unchecked")
@Service
public class CacheDataImpl  extends CacheManager {


	//插入键值对String-string
	public void save(String key,String field) {
		getRedisTemplate().opsForValue().append(key, field);
	}
	//插入键值对string-map<string,string>
	public void save(String key,Map<String, String> map) {
		getRedisTemplate().opsForHash().putAll(key, map);

	}

	public void save(String key,String field,String value) {
		try {
			getRedisTemplate().opsForHash().put(key, field,value);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}


	//插入键值对string-List
	public void saveList(String key,String... field) {
		getRedisTemplate().opsForList().leftPushAll(key, field);
	}
	public String get(String key){
		return (String) getRedisTemplate().opsForValue().get(key);
	}
	public List<String> getList(String key,long start,long end){
		return getRedisTemplate().opsForList().range(key, start, end);
	}
	
	
	public Map<String, String> getMap(String key){
		return getRedisTemplate().opsForHash().entries(key);
	}
	//通过key拿到map的key
	public Set<String> getField(String key){
		return getRedisTemplate().opsForHash().keys(key);
	}
	//通过value拿到map的key,最好别用，太慢。。。
	public List<String> getField(String key,String value){
		List<String> list = new ArrayList<String>();
		Map<String,String> map = getRedisTemplate().opsForHash().entries(key);
		for(Map.Entry<String, String> entry:map.entrySet())
			if(entry.getValue().equals(value))
				list.add(entry.getKey());
		return list;
	}

	public List<String> getMapAllValue(String key){
		return getRedisTemplate().opsForHash().values(key);
	}
	public String getValue(String key,String field) {
		return (String) getRedisTemplate().opsForHash().get(key, field);

	}

	public void del(String key) {
		getRedisTemplate().delete(key);
	}
	public void del(String key,String... field) {
		getRedisTemplate().opsForHash().delete(key, field);
	}

	//删除List
	public void delList(String key,String value){
		getRedisTemplate().opsForList().remove(key, 0, value);
	}

	public boolean isExist(String key,String field) {
		if(getRedisTemplate().hasKey(key))
			return getRedisTemplate().opsForHash().hasKey(key, field);
		return false;
	}
	public boolean isExist(String key) {
		return getRedisTemplate().hasKey(key);
	}
	public boolean flush(){
//		System.out.println(getRedisTemplate().keys(""));
		Iterator<String> iterator = getRedisTemplate().keys("*").iterator();
		while (iterator.hasNext()) {
			getRedisTemplate().delete(iterator.next());
		}
		return false;
	}

}
