package com.hd.clc.frss.service;

import com.hd.clc.frss.common.Result;

public interface ICourseService {
    Result getSignList(int teacherId, int courseId);
}
