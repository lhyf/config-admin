package org.lhyf.config.server.redis;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.eis.AbstractSessionDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/****
 * @author YF
 * @date 2018-07-10 19:04
 * @desc RedisSessionDao
 *
 **/
@Component
public class RedisSessionDao extends AbstractSessionDAO {


    private static Logger logger = LoggerFactory.getLogger(RedisSessionDao.class);

    private final String shiro_session_prefix = "shiro:session:";

    @Autowired
    private RedisTemplate<Object, Object> redisTemplate;



    private Object redisGet(String key) {
        return redisTemplate.opsForValue().get(getKey(key));
    }

    private void redisSet(String key, Object value) {
        redisTemplate.opsForValue().set(getKey(key), value);
        redisTemplate.expire(key, 600, TimeUnit.SECONDS);
    }

    private void redisDel(String key) {
        redisTemplate.delete(getKey(key));
    }

    private String getKey(String key) {
        return shiro_session_prefix + key;
    }

    private void saveSession(Session session) {
        if (session != null && session.getId() != null) {
            String key = session.getId().toString();
            redisSet(key, session);
        }
    }

    @Override
    protected Serializable doCreate(Session session) {
        Serializable sessionId = generateSessionId(session);
        assignSessionId(session, sessionId);
        redisSet(session.getId().toString(), session);
        return sessionId;
    }

    @Override
    protected Session doReadSession(Serializable sessionId) {
//        logger.info("read session...");
        if (sessionId == null) {
            return null;
        }
        Object o = redisGet(sessionId.toString());
        return (Session) o;
    }

    @Override
    public void update(Session session) throws UnknownSessionException {
        saveSession(session);
    }

    @Override
    public void delete(Session session) {
        if (session == null || session.getId() == null) {
            return;
        }
        redisDel(session.getId().toString());
    }

    @Override
    public Collection<Session> getActiveSessions() {
        Set<Object> keys = redisTemplate.keys(shiro_session_prefix);

        Set<Session> sessions = new HashSet<>();
        if (CollectionUtils.isEmpty(keys)) {
            return sessions;
        }

        for (Object key : keys) {
            Session session = (Session) redisGet(key.toString());
            sessions.add(session);
        }
        return sessions;
    }

}
