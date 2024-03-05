package com.hongyu.revaluation.common;

/**
 * @author JaikenWong
 * @since 2024-01-21 10:03
 **/
public class BadParamException extends Exception {

    public BadParamException(String message) {
        super(message);
    }

    public BadParamException() {}

    public BadParamException(String message, Throwable cause) {
        super(message, cause);
    }

    public BadParamException(Throwable cause) {
        super(cause);
    }
}
