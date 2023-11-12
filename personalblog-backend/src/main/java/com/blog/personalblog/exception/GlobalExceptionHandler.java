package com.blog.personalblog.exception;

import com.blog.personalblog.vo.Result;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
    public Result handleException(Exception e){
        e.printStackTrace();
        return Result.Fail(StringUtils.hasLength(e.getMessage())?e.getMessage():"操作失败");
    }
}
