package com.blog.personalblog.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Result<T> {
    private Integer code;
    private String message;
    private T data;
    public static <E> Result<E> Success(E data){

        return new Result<E>(0,"success",data);
    }
    public static<T> Result<T> Success(){

        return new Result<T>(0,"success",null);
    }
    public static<T> Result<T> Fail(String msg){

        return new Result<T>(-1,msg,null);

    }
}
