package sz.lzh.conversation.dao.cache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

@SuppressWarnings("rawtypes")
@Service
public class CacheManager {
	@Autowired
	private StringRedisTemplate redisTemplate;

	public RedisTemplate getRedisTemplate() {
		return redisTemplate;
	}
	
//	@Autowired
//	private ShardedJedisPool shardedJedisPool;
//	protected ShardedJedis jedis;
//	public ShardedJedisPool getShardedJedisPool() {
//		return shardedJedisPool;
//	}
//
//	public void setShardedJedisPool(ShardedJedisPool shardedJedisPool) {
//		this.shardedJedisPool = shardedJedisPool;
//
//	}
//
//	protected synchronized ShardedJedis getJedis() {
//		if(jedis == null)
//			jedis = shardedJedisPool.getResource();
//		return jedis;
//	}
}
