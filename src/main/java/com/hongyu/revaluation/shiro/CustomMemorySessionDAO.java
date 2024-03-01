package com.hongyu.revaluation.shiro;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.MemorySessionDAO;

import java.io.Serializable;

/**
 * @author JaikenWong
 * @since 2024-03-01 14:07
 **/
public class CustomMemorySessionDAO extends MemorySessionDAO {
    @Override
    protected Serializable generateSessionId(Session session) {
        return RandomStringUtils.randomAlphanumeric(64);
    }
}
