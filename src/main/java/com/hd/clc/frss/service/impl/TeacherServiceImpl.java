package com.hd.clc.frss.service.impl;

import com.hd.clc.frss.common.Result;
import com.hd.clc.frss.service.ITeacherService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class TeacherServiceImpl implements ITeacherService {

    @Override
    public Result register(String account, String password, String name, String invitation){
        Map<String, Object> data = new HashMap<>();
        String msg = null;



        return new Result(msg, data);
    }
}
