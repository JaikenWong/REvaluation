package com.hongyu.revaluation.shiro;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.SessionListener;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

/**
 * @author JaikenWong
 * @since 2024-03-01 10:08
 **/
@Slf4j
@Component
public class CustomSessionListener implements SessionListener {

    @Override
    public void onStart(Session session) {
        log.info("登陆==>" + session.getId());
    }

    @Override
    public void onStop(Session session) {
        log.info("登出==>" + session.getId());
    }

    @Override
    public void onExpiration(Session session) {
        log.info("登陆过期==>" + session.getId());
    }
}
