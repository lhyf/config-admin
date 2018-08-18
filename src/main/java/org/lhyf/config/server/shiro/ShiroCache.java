package org.lhyf.config.server.shiro;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/****
 * @author YF
 * @date 2018-07-11 10:44
 * @desc RedisCache
 *
 **/

@Component
public class ShiroCache<K, V> implements Cache<K, V> {


    private static final String REDIS_SHIRO_CACHE = "shiro:cache:";

    private long expireTime = 7 * 24 * 60;// 缓存的超时时间，单位为s
    @Autowired
    private RedisTemplate<K, V> redisTemplate;


    @Override
    public V get(K k) throws CacheException {
        redisTemplate.boundValueOps(getCacheKey(k)).expire(expireTime, TimeUnit.MINUTES);
        return redisTemplate.boundValueOps(getCacheKey(k)).get();
    }

    @Override
    public V put(K k, V v) throws CacheException {
        V old = get(k);
        redisTemplate.boundValueOps(getCacheKey(k)).set(v);
        return old;
    }

    @Override
    public V remove(K k) throws CacheException {
        V old = get(k);
        redisTemplate.delete(getCacheKey(k));
        return old;
    }

    @Override
    public void clear() throws CacheException {
        redisTemplate.delete(keys());
    }

    @Override
    public int size() {
        return keys().size();
    }


    @Override
    public Set<K> keys() {
        return redisTemplate.keys(getCacheKey("*"));
    }


    @Override
    public Collection<V> values() {
        Set<K> set = keys();
        List<V> list = new ArrayList<>();
        for (K s : set) {
            list.add(get(s));
        }
        return list;
    }

    private K getCacheKey(Object k) {
        return (K) (REDIS_SHIRO_CACHE + k);
    }
}
