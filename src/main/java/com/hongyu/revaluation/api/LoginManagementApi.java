package com.hongyu.revaluation.api;

import org.jasypt.util.password.PasswordEncryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hongyu.revaluation.common.CommonConst;
import com.hongyu.revaluation.entity.session.LoginQuery;
import com.hongyu.revaluation.entity.user.User;
import com.hongyu.revaluation.mapper.UserMapper;

import jakarta.servlet.http.HttpSession;
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

    @PostMapping("/public/ui/api/login")
    public ResponseEntity<String> login(@Valid @RequestBody LoginQuery query, HttpSession session) {
        log.info("dashboard login username {}", query.getUserName());
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("user_name", query.getUserName());
        User user = userMapper.selectOne(wrapper);
        passwordEncryptor.checkPassword(String.valueOf(query.getPassword()), user.getPassword());
        if (user == null) {
            return ResponseEntity.badRequest().body("用户名密码错误");
        }
        setAttribute(session, user.getUserName(), user.getId());
        return ResponseEntity.ok().body("success");
    }

    private void setAttribute(HttpSession session, String username, Long uid) {
        if (session == null) {
            log.warn("session is null, username#{}, uid#{}", username, uid);
        } else {
            session.setAttribute(CommonConst.SESSION_USERNAME, username);
            session.setAttribute(CommonConst.SESSION_UID, uid);
        }
    }
}
