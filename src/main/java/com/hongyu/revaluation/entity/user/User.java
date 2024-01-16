package com.hongyu.revaluation.entity.user;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Builder;
import lombok.Data;

/**
 * @author JaikenWong
 * @since 2024-01-15 12:27
 **/
@Data
@Builder
@TableName("`user`")
public class User {

    @TableId(type = IdType.AUTO)
    private Long id;

    private String userName;

    private String password;

    private Long createTime;
}
