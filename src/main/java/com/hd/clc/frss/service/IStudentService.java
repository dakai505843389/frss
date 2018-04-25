package com.hd.clc.frss.service;

import com.hd.clc.frss.common.Result;

public interface IStudentService {
    Result register(int number, String name, String imagePath);
}
