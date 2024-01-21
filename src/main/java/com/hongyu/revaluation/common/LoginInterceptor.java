package com.hongyu.revaluation.common;

import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

/**
 * @author JaikenWong
 * @since 2024-01-21 10:19
 **/
@Slf4j
public class LoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) {
        String reqPath = httpServletRequest.getServletPath();
        // 只有返回true才会继续向下执行，返回false取消当前请求
        HttpSession session = httpServletRequest.getSession();
        if (session == null || StringUtils.isEmpty(session.getAttribute(CommonConst.SESSION_USERNAME))) {
            log.trace("access#{}, not logged in, return", reqPath);
            httpServletResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
            return false;
        }
        return true;
    }
}
