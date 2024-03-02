package com.hongyu.revaluation.shiro;

import java.io.Serializable;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.CachingSessionDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

/**
 * @author JaikenWong
 * @since 2024-03-01 14:07
 **/
@Component
public class CustomRedisSessionDAO extends CachingSessionDAO {
    @Value("${session.timeout:1800}")
    private int sessionTimeout;

    @Value("${session.id-len:32}")
    private int idLen;

    @Value("${session.store-prefix}")
    private String storePrefix;

    @Autowired
    @Qualifier("sessionRedisTemplate")
    private RedisTemplate<Serializable, Session> redisTemplate;

    @Override
    protected Serializable doCreate(Session session) {
        Serializable serializable = RandomStringUtils.randomAlphanumeric(idLen);
        assignSessionId(session, serializable);
        // 将sessionid作为Key，session作为value存入redis
        redisTemplate.opsForValue().set(getStoreId(serializable), session);
        return serializable;
    }

    @Override
    protected Session doReadSession(Serializable sessionId) {
        if (sessionId == null) {
            return null;
        }
        // 从Redis中读取Session对象
        return redisTemplate.opsForValue().get(getStoreId(sessionId));
    }

    @Override
    protected void doUpdate(Session session) {
        // 将sessionid作为Key，session作为value存入redis，并设置有效期
        redisTemplate.opsForValue().set(getStoreId(session.getId()), session, sessionTimeout, TimeUnit.SECONDS);
    }

    @Override
    protected void doDelete(Session session) {
        // null 验证
        if (session == null) {
            return;
        }
        // 从Redis中删除指定SessionId的k-v
        redisTemplate.delete(getStoreId(session.getId()));
    }

    private Serializable getStoreId(Serializable id) {
        return storePrefix + id;
    }
}
