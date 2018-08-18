package org.lhyf.config.server.shiro;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.cache.CacheManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/****
 * @author YF
 * @date 2018-07-11 10:44
 * @desc RedisCacheManager
 *
 **/
@Component
public class ShiroCacheManager implements CacheManager {

    @Autowired
    private ShiroCache redisCache;

    @Override
    public <K, V> Cache<K, V> getCache(String s) throws CacheException {
        return redisCache;
    }
}
