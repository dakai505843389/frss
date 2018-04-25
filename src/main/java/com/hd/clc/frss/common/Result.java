package com.hd.clc.frss.common;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Result<T> {

    private int code;
    private String msg;
    private T data;

    public Result(T data){
        this.code = 0;
        this.msg = "请求成功！";
        this.data = data;
    }

    public Result(String msg, T data){
        this.code = 0;
        this.msg = msg;
        this.data = data;
    }

    public Result(String msg){
        this.code = -1;
        this.msg = msg;
    }

}
