package com.hongyu.revaluation.entity.user;

import lombok.Data;

/**
 * @author JaikenWong
 * @since 2024-01-15 11:28
 **/
@Data
public class UserCreateInfo {
    private String userName;

    private char[] password;

    private char[] confirmPasswd;

    private String email;
}
