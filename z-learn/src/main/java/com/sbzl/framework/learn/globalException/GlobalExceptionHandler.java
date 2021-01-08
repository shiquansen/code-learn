package com.sbzl.framework.learn.globalException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler
    public ResultVO allException(Exception e) {
        e.printStackTrace();
        logger.error("GlobalExceptionHandler  {}", e.getLocalizedMessage());
        return ResultVO.error("service inner error !!!");
    }
}


