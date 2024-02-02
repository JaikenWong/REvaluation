package com.hongyu.revaluation.api;

import java.util.concurrent.TimeUnit;

import jakarta.servlet.http.HttpServletRequest;
import org.jasypt.util.password.PasswordEncryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hongyu.revaluation.common.JwtUtil;
import com.hongyu.revaluation.entity.response.Result;
import com.hongyu.revaluation.entity.session.LoginQuery;
import com.hongyu.revaluation.entity.user.User;
import com.hongyu.revaluation.mapper.UserMapper;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

/**
 * @author JaikenWong
 * @since 2024-01-21 10:13
 **/
@Slf4j
@RestController
public class LoginManagementApi {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private PasswordEncryptor passwordEncryptor;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private HttpServletResponse response;

    @PostMapping("/public/ui/api/login")
    public ResponseEntity<Result> login(@Valid @RequestBody LoginQuery query) {
        log.info("dashboard login username {}", query.getUserName());
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("user_name", query.getUserName());
        User user = userMapper.selectOne(wrapper);
        passwordEncryptor.checkPassword(String.valueOf(query.getPassword()), user.getPassword());
        if (user == null) {
            return ResponseEntity.badRequest().body(Result.builder().code(1001).message("用户名密码错误").build());
        }
        setSessionCookie(user);
        return ResponseEntity.ok().body(Result.builder().success(true).message("登录成功").build());
    }

    private void setSessionCookie(User user) {
        // 生成会话ID
        String token = JwtUtil.createToken(user);
        Cookie cookie = new Cookie("evaluation-token", token);
        cookie.setMaxAge(30 * 60 * 60); // 30 min
        cookie.setSecure(true);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        // add cookie to response
        response.addCookie(cookie);
        redisTemplate.opsForValue().set(token, user.getId(), 30, TimeUnit.MINUTES);
    }

    @GetMapping("/public/ui/api/logout")
    public ResponseEntity<Result> logout(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        for(Cookie cookie : cookies) {
            if (cookie.getName().equals("evaluation-token")){
                redisTemplate.delete(cookie.getValue());
            }
        }
        return ResponseEntity.ok().body(Result.builder().success(true).message("登出成功").build());
    }
}
