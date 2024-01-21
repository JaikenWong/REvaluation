package com.hongyu.revaluation.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * @author JaikenWong
 * @since 2024-01-21 09:33
 **/
@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {
    @ResponseBody
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handle(MethodArgumentNotValidException e) {
        log.error("Bad request error {}", e.getCause());
        FieldError fieldError = e.getBindingResult().getFieldError();
        return ResponseEntity.status(500).body(fieldError.getDefaultMessage());
    }

    @ResponseBody
    @ExceptionHandler(BadParamException.class)
    public ResponseEntity<String> handle(BadParamException e) {
        log.error("Bad Param Error {}", e.getCause());
        return ResponseEntity.status(500).body(e.getMessage());
    }
}

