package sz.lzh.conversation.dao.cache.impl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import sz.lzh.conversation.dao.cache.CacheManager;



/**
 * redis缓存对象的方法
 * 以键值对方式存储
 * <key,Map<key,obj>>
 * 第一个key是redis的key值，第二个key是存储的Map的key，obj是对象序列化以后的byte值
 * */
//@Transactional
@SuppressWarnings("unchecked")
//@Service
public class CacheObjectImpl extends CacheManager{

	@Autowired
	private static CacheObjectImpl cacheObjectImpl;

	public static CacheObjectImpl getInstance() {
		if(cacheObjectImpl==null)
			cacheObjectImpl = new CacheObjectImpl();
		return cacheObjectImpl;
	}

	public void setInstance(CacheObjectImpl cacheObjectImpl){
		this.cacheObjectImpl = cacheObjectImpl;
	}


	public void putObject(String key,String field,Object obj) {
		Map<byte[], byte[]> map = new HashMap<byte[], byte[]>();
		map.put(field.getBytes(), serialize(obj));
		getRedisTemplate().opsForHash().putAll(key.getBytes(),map);
	}

	public Object getObject(String key,String field) {
		return unserialize((byte[]) getRedisTemplate().opsForHash().get(key.getBytes(), field.getBytes()));

	}

	//获取全部对象
	public List<Object> getAllObject(String key) {
		List<Object> list = new ArrayList<Object>();
		Set<byte[]> set =(Set<byte[]>) getRedisTemplate().opsForHash().values(key);
		Iterator<byte[]> iterator=set.iterator();
		while (iterator.hasNext()) {
			list.add(unserialize(iterator.next()));
		}
		return list;
	}

	//获取map里的全部key值
	public List<String> getAllKey(String key) {
		List<String> list = new ArrayList<String>();
		Set<byte[]> set =(Set<byte[]>) getRedisTemplate().opsForHash().values(key);
		Iterator<byte[]> iterator=set.iterator();
		while (iterator.hasNext()) {
			list.add(new String(iterator.next()));
		}
		return list;
	}


	public boolean remove(String key,String field){
		try {
			getRedisTemplate().opsForHash().delete(key, field);
		} catch (Exception e) {
			// TODO: handle exception
			return false;
		}
		return true;
	}
	public boolean remove(String key){
		try {
			getRedisTemplate().delete(key);
		} catch (Exception e) {
			// TODO: handle exception
			return false;
		}
		return true;
	}
	
	//通过value拿到map的key
	public List<String> getField(String key,Object value){
		List<String> list = new ArrayList<String>();
		Iterator<String> iterator = getRedisTemplate().opsForHash().values(key).iterator();
		while (iterator.hasNext()) {
			if(getObject(key,iterator.next()).equals(value))
				list.add(iterator.toString());
		}
		return list;
	}

	public boolean isExist(String key,String field) {
		if (getRedisTemplate().hasKey(key)) 
			return getRedisTemplate().opsForHash().hasKey(key, field);
		return false;
	}


	//序列化
	public  byte[] serialize(Object object) {
		ObjectOutputStream oos = null;
		ByteArrayOutputStream baos = null;
		try {
			// 序列化
			baos = new ByteArrayOutputStream();
			oos = new ObjectOutputStream(baos);
			oos.writeObject(object);
			byte[] bytes = baos.toByteArray();
			return bytes;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	//反序列化
	public  Object unserialize( byte[] bytes) {
		ByteArrayInputStream bais = null;
		try {
			// 反序列化
			bais = new ByteArrayInputStream(bytes);
			ObjectInputStream ois = new ObjectInputStream(bais);
			return ois.readObject();
		} catch (Exception e) {

		}
		return null;
	}
}
