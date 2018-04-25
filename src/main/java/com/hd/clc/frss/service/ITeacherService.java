package com.hd.clc.frss.service;

import com.hd.clc.frss.common.Result;
import com.hd.clc.frss.db.entity.Teacher;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

public interface ITeacherService {
    Result register(String account, String password, String name, String invitation);
    Result getInvitation(int teacherId, HttpServletRequest request);
    Result releaseCourse(int teacherId, String courseName, Date beginTime, Date endTime);

    /**-----------------------------------------公共方法-----------------------------------------**/

    Teacher getTeacherByTeacherId(int teacherId);
}
