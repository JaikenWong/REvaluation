package com.hongyu.revaluation.api;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.apache.commons.collections.CollectionUtils;
import org.jasypt.util.password.PasswordEncryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hongyu.revaluation.common.BadParamException;
import com.hongyu.revaluation.entity.response.Result;
import com.hongyu.revaluation.entity.user.User;
import com.hongyu.revaluation.entity.user.UserCreateInfo;
import com.hongyu.revaluation.mapper.UserMapper;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

/**
 * @author JaikenWong
 * @since 2024-01-15 11:19
 **/
@Slf4j
@RestController
public class UserManagementApi {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private PasswordEncryptor passwordEncryptor;

    @PostMapping(path = "/public/ui/api/user", produces = "application/json")
    public ResponseEntity<Result> createUser(@Valid @RequestBody UserCreateInfo userInfo) throws Exception {
        log.info("create user for {}", userInfo.getUserName());
        if (!Objects.deepEquals(userInfo.getPassword(), userInfo.getConfirmPasswd())) {
            throw new BadParamException("用户密码和确认密码不同");
        }
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("user_name", userInfo.getUserName());
        List<User> users = userMapper.selectList(wrapper);
        if (CollectionUtils.isNotEmpty(users)) {
            return ResponseEntity.badRequest().body(Result.builder().code(2001).message("用户名已存在").build());
        }
        // 校验用户信息
        String password = passwordEncryptor.encryptPassword(userInfo.getPassword());
        // 密码加密后存储
        User user = User.builder().userName(userInfo.getUserName()).password(password)
            .createTime(System.currentTimeMillis()).build();
        userMapper.insert(user);
        Map<String, Long> map = new HashMap<>();
        map.put("userId", user.getId());
        return ResponseEntity.ok(Result.builder().data(map).success(true).message("注册成功").build());
    }

}
