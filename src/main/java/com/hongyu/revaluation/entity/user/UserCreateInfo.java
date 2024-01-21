package com.hongyu.revaluation.entity.user;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

/**
 * @author JaikenWong
 * @since 2024-01-15 11:28
 **/
@Data
public class UserCreateInfo {

    @Length(min = 6, max = 32, message = "用户名长度在6～32")
    @Pattern(regexp = "^\\s*|[0-9A-Za-z]*$", message = "用户名为数字和字母组合")
    private String userName;

    @Length(min = 8, max = 32)
    @Pattern(regexp = "^\\s*|[0-9A-Za-z]*$", message = "密码为数字和字母组合")
    private String password;

    @Length(min = 8, max = 32)
    @Pattern(regexp = "^\\s*|[0-9A-Za-z]*$", message = "密码为数字和字母组合")
    private String confirmPasswd;

    @Email(message = "邮箱格式不正确")
    private String email;
}
