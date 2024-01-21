package com.hongyu.revaluation.api;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Objects;

import com.hongyu.revaluation.entity.response.Result;
import org.jasypt.util.password.PasswordEncryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.hongyu.revaluation.common.BadParamException;
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
        log.info("create user ");
        try {
            if (!Objects.deepEquals(userInfo.getPassword(), userInfo.getConfirmPasswd())) {
                throw new BadParamException("用户密码和确认密码不同");
            }
            // 校验用户信息
            String password = passwordEncryptor.encryptPassword(userInfo.getPassword());
            // 密码加密后存储
            User user = User.builder().userName(userInfo.getUserName()).password(password)
                .createTime(System.currentTimeMillis()).build();
            userMapper.insert(user);
            return ResponseEntity.ok(Result.builder().success(true).message("注册成功").build());
        } finally {
            // 清理明文密码数组
            Field valueFieldOfString = String.class.getDeclaredField("value");
            valueFieldOfString.setAccessible(true);
            Arrays.fill((char[]) valueFieldOfString.get(userInfo.getPassword()), (char) 0x00);
            Arrays.fill((char[]) valueFieldOfString.get(userInfo.getConfirmPasswd()), (char) 0x00);
        }
    }

}
