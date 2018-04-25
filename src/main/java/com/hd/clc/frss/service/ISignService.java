package com.hd.clc.frss.service;

import com.hd.clc.frss.common.Result;

import javax.servlet.http.HttpServletRequest;

public interface ISignService {
    Result sign(int courseId, String imagePath);
}
