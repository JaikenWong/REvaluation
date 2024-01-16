package com.hongyu.revaluation.api;

import com.hongyu.revaluation.entity.user.User;
import com.hongyu.revaluation.entity.user.UserCreateInfo;
import com.hongyu.revaluation.entity.user.UserEntity;
import com.hongyu.revaluation.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.jasypt.util.password.PasswordEncryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

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
    public ResponseEntity<UserEntity> createUser(@RequestBody UserCreateInfo userInfo) {
        log.info("create user ");
        try {
            // 校验用户信息
            String password = passwordEncryptor.encryptPassword(String.copyValueOf(userInfo.getPassword()));
            // 密码加密后存储
            User user = User.builder().userName(userInfo.getUserName()).
                    password(password).createTime(System.currentTimeMillis()).build();
            userMapper.insert(user);
            return ResponseEntity.ok(UserEntity.builder().userId(user.getId()).build());
        } finally {
            // 清理明文密码数组
            Arrays.fill(userInfo.getPassword(), (char) 0x00);
        }
    }

}
