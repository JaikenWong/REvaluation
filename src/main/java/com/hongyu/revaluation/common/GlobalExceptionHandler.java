package com.hongyu.revaluation.common;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hongyu.revaluation.entity.response.Result;

import lombok.extern.slf4j.Slf4j;

/**
 * @author JaikenWong
 * @since 2024-01-21 09:33
 **/
@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {
    @ResponseBody
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Result> handle(MethodArgumentNotValidException e) {
        log.error("Bad request error {}", e);
        FieldError fieldError = e.getBindingResult().getFieldError();
        return ResponseEntity.status(500).body(Result.builder().message(fieldError.getDefaultMessage()).build());
    }

    @ResponseBody
    @ExceptionHandler(BadParamException.class)
    public ResponseEntity<Result> handle(BadParamException e) {
        log.error("Bad Param Error {}", e);
        return ResponseEntity.status(500).body(Result.builder().message(e.getMessage()).build());
    }
}
