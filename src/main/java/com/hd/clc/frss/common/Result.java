package com.hd.clc.frss.common;

public class Result<T> {

    public int code;
    public String msg;
    public T data;

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
