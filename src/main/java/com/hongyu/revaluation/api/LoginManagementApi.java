package com.hongyu.revaluation.api;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.hongyu.revaluation.entity.response.Result;
import com.hongyu.revaluation.entity.session.LoginQuery;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

/**
 * @author JaikenWong
 * @since 2024-01-21 10:13
 **/
@Slf4j
@RestController
public class LoginManagementApi {

    @PostMapping("/public/ui/api/login")
    public ResponseEntity<Result> login(@Valid @RequestBody LoginQuery query) {
        log.info("dashboard login username {}", query.getUserName());
        Subject currentUser = SecurityUtils.getSubject();
        try {
            currentUser.login(new UsernamePasswordToken(query.getUserName(), query.getPassword()));
        } catch (UnknownAccountException uae) {
            return ResponseEntity.badRequest().body(Result.builder().code(1001).message("用户名密码错误").build());
        } catch (AuthenticationException ae) {
            return ResponseEntity.badRequest().body(Result.builder().code(1002).message("认证失败").build());
        }
        currentUser = SecurityUtils.getSubject();
        Map<String, Object> token = new HashMap<>();
        token.put("token", currentUser.getSession().getId());
        return ResponseEntity.ok().body(Result.builder().data(token).success(true).message("登录成功").build());
    }

    @GetMapping("/public/ui/api/logout")
    public ResponseEntity<Result> logout(HttpServletRequest request) {
        Subject currentUser = SecurityUtils.getSubject();
        currentUser.logout();
        return ResponseEntity.ok().body(Result.builder().success(true).message("登出成功").build());
    }

    @GetMapping("/public/ui/api/touch")
    public ResponseEntity<Result> touch() {
        Subject currentUser = SecurityUtils.getSubject();
        currentUser.getSession().touch();
        Serializable sessionId = currentUser.getSession().getId();
        log.info("touch session Id {}", sessionId);
        Map<String, Serializable> map = new HashMap<>();
        map.put("touch-token", sessionId);
        return ResponseEntity.ok().body(Result.builder().data(map).build());
    }
}
