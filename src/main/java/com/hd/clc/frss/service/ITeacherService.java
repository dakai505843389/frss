package com.hd.clc.frss.service;

import com.hd.clc.frss.common.Result;

public interface ITeacherService {
    public Result register(String account, String password, String name, String invitation);
}
