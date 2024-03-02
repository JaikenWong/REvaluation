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

    @Autowired
    @Qualifier("sessionRedisTemplate")
    private RedisTemplate<Serializable, Session> redisTemplate;

    @Override
    protected Serializable doCreate(Session session) {
        Serializable serializable = RandomStringUtils.randomAlphanumeric(idLen);
        assignSessionId(session, serializable);
        // 将sessionid作为Key，session作为value存入redis
        redisTemplate.opsForValue().set(serializable, session);
        return serializable;
    }

    @Override
    protected Session doReadSession(Serializable sessionId) {
        if (sessionId == null) {
            return null;
        }
        // 从Redis中读取Session对象
        Session session = redisTemplate.opsForValue().get(sessionId);
        return session;
    }

    @Override
    protected void doUpdate(Session session) {
        // 设置session有效期
        session.setTimeout(sessionTimeout * 1000);
        // 将sessionid作为Key，session作为value存入redis，并设置有效期
        redisTemplate.opsForValue().set(session.getId(), session, sessionTimeout, TimeUnit.SECONDS);
    }

    @Override
    protected void doDelete(Session session) {
        // null 验证
        if (session == null) {
            return;
        }
        // 从Redis中删除指定SessionId的k-v
        redisTemplate.delete(session.getId());
    }
}
