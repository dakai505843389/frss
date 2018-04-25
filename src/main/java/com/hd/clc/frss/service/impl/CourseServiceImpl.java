package com.hd.clc.frss.service.impl;

import com.hd.clc.frss.common.Result;
import com.hd.clc.frss.db.entity.Course;
import com.hd.clc.frss.db.entity.Sign;
import com.hd.clc.frss.db.entity.Teacher;
import com.hd.clc.frss.db.impl.CourseMapper;
import com.hd.clc.frss.db.impl.SignMapper;
import com.hd.clc.frss.db.impl.TeacherMapper;
import com.hd.clc.frss.service.ICourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CourseServiceImpl implements ICourseService {

    @Autowired
    private TeacherMapper teacherMapper;

    @Autowired
    private CourseMapper courseMapper;

    @Autowired
    private SignMapper signMapper;

    @Override
    public Result getSignList(int teacherId, int courseId) {
        Map<String, Object> data = new HashMap<>();
        String msg;
        boolean rollback = false;
        try {
            Teacher teacher = teacherMapper.queryById(teacherId);
            if (teacher != null){
                Course course = courseMapper.queryById(courseId);
                if (course != null){
                    if (course.getBelongTeacherId() == teacherId){
                        List<Sign> signList = signMapper.queryByCourseId(courseId);
                        data.put("signList", signList);
                        msg = "查询成功！";
                    }else{
                        rollback = true;
                        msg = "该课程不属于该教师！";
                    }
                }else {
                    rollback = true;
                    msg = "不存在该课程！";
                }
            }else {
                rollback = true;
                msg = "不存在该教师";
            }
        }catch (Exception e){
            rollback = true;
            msg = "查询失败！";
        }
        if (rollback){
            return new Result<>(msg);
        }else {
            return new Result<>(msg, data);
        }
    }

}
