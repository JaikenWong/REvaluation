package com.hongyu.revaluation.entity.user;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author JaikenWong
 * @since 2024-01-15 12:27
 **/
@Data
@TableName("`user`")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User implements Serializable {

    private static final long serialVersionUID = -1;

    @TableId(type = IdType.AUTO)
    private Long id;

    private String userName;

    private String password;

    private Long createTime;
}
