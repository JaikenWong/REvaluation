package com.hongyu.revaluation.entity.session;

import lombok.Data;

/**
 * @author JaikenWong
 * @since 2024-01-21 10:16
 **/
@Data
public class LoginQuery {

    private String userName;

    private char[] password;
}
