package sz.lzh.conversation.dao.domain.impl;

import java.util.List;
import java.util.Map;

import org.hibernate.sql.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mysql.jdbc.Field;

import sz.lzh.conversation.dao.cache.CacheInfo;
import sz.lzh.conversation.dao.cache.impl.CacheDataImpl;
import sz.lzh.conversation.dao.domain.SqlManager;

@Transactional
@Service
@SuppressWarnings({ "rawtypes", "unchecked" })
public class SqlManagerImpl extends SqlManager{
	
	@Autowired
	private CacheDataImpl cacheDataImpl;
	
	private static SqlManagerImpl sqlManagerImpl;
	
	private UpdateCacheInterface updateCacheInterface;
	
	public SqlManagerImpl() {
		// TODO Auto-generated constructor stub
	}
	
	public static SqlManagerImpl getInstance() {
		if(sqlManagerImpl == null)
			sqlManagerImpl = new SqlManagerImpl();
		return sqlManagerImpl;
	}
	
	public void setInstance(SqlManagerImpl sqlManagerImpl) {
		this.sqlManagerImpl = sqlManagerImpl;
	}
	
	public boolean save(Object object) {
		try {
			getSession().save(object);
			getSession().flush();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return false;
		}
		return true;
	}
	public boolean saveAndUpdataCache(Object object, CacheInfo cacheInfo) {
		try {
			getSession().save(object);
			getSession().flush();
			cacheInfo.update(object);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public boolean remove(String sql) {
		try {
			getSession().createQuery(sql).executeUpdate();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public boolean removeAndUpdataCache(String sql,String CacheKey,String key) {
		try {
			
			getSession().createQuery(sql).executeUpdate();
			cacheDataImpl.del(CacheKey,key);
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public boolean removeByClass(Object object) {
		try {
			
			getSession().delete(object);
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public boolean removeByClassAndUpdataCache(Object object,CacheInfo cacheInfo) {
		try {
			getSession().delete(object);
			getSession().flush();
			cacheInfo.remove(object);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public boolean update(String Sql) {
		try {
			getSession().createQuery(Sql).executeUpdate();
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public boolean updateAndUpdataCache(String Sql,String CacheKey,String Cachefield, String CacheValue) {
		try {
			
			getSession().createQuery(Sql).executeUpdate();
			cacheDataImpl.save(CacheKey, Cachefield,CacheValue);
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return false;
		}
		return true;
	}
	public <T> boolean updateByClass(Class<T> t,String[] field,String[] value,String[] condition,String[] key) {
		try {
			StringBuffer sql =new StringBuffer();
			sql.append("UPDATE ").append(t.getSimpleName()).append(" SET ");
			for(int i=0;i<field.length;i++){
				sql.append(" ").append(field[i]).append(" = ").append(value[i]).append(",");
			}
			sql.subSequence(0, sql.length()-1);
			sql.append(" WHERE ");
			for(int i=0;i<condition.length;i++){
				sql.append(condition[i]).append(" = ").append(key[i]).append(",");
			}
			sql.subSequence(0, sql.length()-1);
			
			getSession().createQuery(sql.toString()).executeUpdate();
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public <T> boolean updateByClassAndUpdataCache(Class<T> t,String[] field,String[] value,String[] condition,String[] key,String CacheKey,String Cachefield, String CacheValue) {
		try {
			
			updateByClass(t,field,value,condition,key);
			cacheDataImpl.save(CacheKey, Cachefield,CacheValue);
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return false;
		}
		return true;
	}
	public <T> boolean updateByClass(Class<T> t,String field,Object value,String condition,Object key) {
		try {
			StringBuffer sql =new StringBuffer();
			sql.append("UPDATE ").append(t.getSimpleName())
				.append(" SET ").append(field).append(" = ").append(" '").append(value).append("' ")
				.append(" WHERE ").append(condition).append(" = ").append(" '").append(key).append("' ");	
//			System.out.println(sql);
			getSession().createQuery(sql.toString()).executeUpdate();
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public <T> boolean updateByClassAndUpdataCache(Class<T> t,String field,String value,String condition,String key,String CacheKey,String Cachefield, String CacheValue) {
		try {
			cacheDataImpl.save(CacheKey, Cachefield,CacheValue); 
			if(!updateByClass(t,field,value,condition,key))
				return false;
		} catch (Exception e) {
			// TODO: handle exception
//			e.printStackTrace();
			return false;
		}
		return true;
	}
	public <T> boolean updateByClassAndUpdataCache(Class<T> t,String field,Object value,String condition,Object conditionValue,CacheInfo cacheInfo) {
		try {
			
			updateByClass(t,field,value,condition,conditionValue);
			searchAndUpdataCache(t.getSimpleName(), condition, (String) conditionValue,cacheInfo);
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public <T> List<T> search(Class<T> t) {
		try {
			return getSession().createQuery("FROM "+ t.getName()).list();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return null;
		}
	}
	
	
	
	public List<Object> search(String ClassName) {
		try {
			return getSession().createQuery("FROM "+ ClassName).list();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return null;
		}
	}
	

	public boolean searchAndUpdataCache(String className, CacheInfo cacheInfo) {
		try {
//			System.out.println(getSession().createQuery("FROM "+className).list());
			cacheInfo.update(getSession().createQuery("FROM "+className).list());
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return false;
		}
		return true;
	}
	public  List<Object> search(String className,String condition,String value) {
		try {
			String sql = "FROM "+className + " WHERE "+condition +" = '"+value+"'";
			return getSession().createQuery(sql).list();
		} catch (Exception e) {
			// TODO: handle exception
			return null;
		}
	}
	
	
	public  boolean searchAndUpdataCache(String className,String condition,String value, CacheInfo cacheInfo) {
		try {
			cacheInfo.update(search(className,condition,value));
			
		} catch (Exception e) {
			// TODO: handle exception
			return false;
		}
		return true;
	}
	
	
	
	
	public List<Object> searchBySql(String Sql) {
		try {
			
			return getSession().createQuery(Sql).list();
			
		} catch (Exception e) {
			// TODO: handle exception
			return null;
		}
	}
	

	public boolean searchBySqlAndUpdataCache(String Sql , CacheInfo cacheInfo) {
		try {
			
			cacheInfo.update(getSession().createQuery(Sql).list());
			
		} catch (Exception e) {
			// TODO: handle exception
			return false;
		}
		return true;
	}
	
	
	
	 public  interface UpdateCacheInterface{
		 void updateListen();
	}
	
}
