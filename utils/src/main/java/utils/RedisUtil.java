package utils;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * Redis 工具类
 */
public class RedisUtil {

    private JedisPool jedisPool;
    private Jedis jedis;

    public RedisUtil(String host, int part){
        jedisPool = new JedisPool(host,part);
        jedis = jedisPool.getResource();
    }

    /**
     * 添加永久有效的key
     * @param key
     * @param value
     */
    public void set(String key, String value){
        jedis.set(key,value);
    }

    /**
     * 获取值
     * @param key
     * @return
     */
    public String get(String key){
        return jedis.get(key);
    }

    /**
     * 获取有效期
     * @param key
     * @return
     */
    public Long ttl(String key){
        return jedis.ttl(key);
    }

    /**
     * 检测是否存在
     * @param key
     * @return
     */
    public Boolean exists(String key){
        return jedis.exists(key);
    }

    /**
     * 添加有效期的key
     * @param key
     * @param value
     * @param ttl
     */
    public void setex(String key, String value, int ttl){
        jedis.setex(key,ttl,value);
    }

    /**
     * 删除
     * @param key
     */
    public void del(String key){
        jedis.del(key);
    }

    /**
     * 为指定的 key 设置过期时间
     * @param key
     * @param ttl
     */
    public void expire(String key, int ttl){
        jedis.expire(key,ttl);
    }
}
