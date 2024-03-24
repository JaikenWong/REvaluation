package com.hongyu.revaluation.shiro;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.apache.shiro.realm.Realm;
import org.apache.shiro.session.SessionListener;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author JaikenWong
 * @since 2024-02-29 22:36
 **/
@Configuration
public class CustomShiroConfig {

    @Value("${session.timeout:1800}")
    private int sessionTimeout;

    /**
     * 指定本系统sessionid, 问题: 与servlet容器名冲突, 如jetty, tomcat 等默认jsessionid, 当跳出shiro
     * servlet时如error-page容器会为jsessionid重新分配值导致登录会话丢失!
     *
     * @return
     */
    @Bean
    public SimpleCookie sessionIdCookie() {
        SimpleCookie simpleCookie = new SimpleCookie("X-RE-Token");
        // 防止xss攻击，窃取cookie内容
        simpleCookie.setHttpOnly(true);
        return simpleCookie;
    }

    /**
     * 会话管理 默认使用容器session，这里改为自定义session session的全局超时时间默认是30分钟
     *
     * @return
     */
    @Bean
    public DefaultWebSessionManager sessionManager(SessionListener customSessionListener,
        CustomRedisSessionDAO customRedisSessionDAO) {
        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
        // 会话超时时间，单位：毫秒
        sessionManager.setGlobalSessionTimeout(sessionTimeout * 1000);
        // 定时清理失效会话, 清理用户直接关闭浏览器造成的孤立会话
        sessionManager.setSessionValidationInterval(10 * 60 * 1000);
        // 是否开启定时清理失效会话
        sessionManager.setSessionValidationSchedulerEnabled(true);
        // 指定sessionid
        sessionManager.setSessionIdCookie(sessionIdCookie());
        // 是否允许将sessionId 放到 cookie 中
        sessionManager.setSessionIdCookieEnabled(true);
        // 是否允许将 sessionId 放到 Url 地址拦中
        sessionManager.setSessionIdUrlRewritingEnabled(false);
        sessionManager.setSessionListeners(Collections.singleton(customSessionListener));
        sessionManager.setSessionDAO(customRedisSessionDAO);
        return sessionManager;
    }

    @Bean
    public DefaultWebSecurityManager securityManager(Realm userAuthorizingRealm,
        DefaultWebSessionManager sessionManager) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(userAuthorizingRealm);
        // 换成由Spring Session + redis 管理
        securityManager.setSessionManager(sessionManager);
        return securityManager;
    }

    @Bean("shiroFilter")
    public ShiroFilterFactoryBean
        shiroFilterFactoryBean(@Qualifier("securityManager") DefaultWebSecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        Map<String, String> map = new HashMap<>();
        map.put("/**", "authc");
        // 登录注册接口
        map.put("/public/ui/api/login", "anon");
        map.put("/public/ui/api/user", "anon");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(map);
        shiroFilterFactoryBean.setLoginUrl("/login.html");
        shiroFilterFactoryBean.setSuccessUrl("/index.html");
        return shiroFilterFactoryBean;
    }
}
