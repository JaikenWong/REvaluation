package com.hongyu.revaluation.entity.response;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * @author JaikenWong
 * @since 2024-01-15 11:23
 **/
@Data
@Builder
public class Result implements Serializable {

    private boolean success;

    private int code;

    private String message;

    private Object data;
}
