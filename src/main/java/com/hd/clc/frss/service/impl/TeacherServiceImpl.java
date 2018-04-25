package com.hd.clc.frss.service.impl;

import com.hd.clc.frss.common.Result;
import com.hd.clc.frss.common.StringUtil;
import com.hd.clc.frss.db.entity.Course;
import com.hd.clc.frss.db.entity.Invitation;
import com.hd.clc.frss.db.entity.Teacher;
import com.hd.clc.frss.db.impl.CourseMapper;
import com.hd.clc.frss.db.impl.InvitationMapper;
import com.hd.clc.frss.db.impl.TeacherMapper;
import com.hd.clc.frss.interceptor.UserInterceptor;
import com.hd.clc.frss.service.ITeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class TeacherServiceImpl implements ITeacherService {

    @Autowired
    private TeacherMapper teacherMapper;

    @Autowired
    private InvitationMapper invitationMapper;

    @Autowired
    private CourseMapper courseMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result register(String account, String password, String name, String invitationCode) {
        Map<String, Object> data = new HashMap<>();
        String msg;
        boolean rollback = false;
        Invitation invitation = invitationMapper.queryByCode(invitationCode);
        if (invitation != null) {
            if (invitation.getStatus() == 0) {
                Teacher teacher = new Teacher();
                teacher.setName(name);
                teacher.setAccount(account);
                teacher.setPassword(password);
                teacherMapper.add(teacher);
                invitation.setBelongTeacherId(teacher.getId());
                invitation.setStatus(1);
                invitationMapper.update(invitation);
                data.put("teacher", teacher);
                msg = "注册成功！";
            } else {
                msg = "该邀请码已使用！";
                rollback = true;
            }
        } else {
            msg = "不存在该邀请码！";
            rollback = true;
        }
        if (rollback) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return new Result(msg);
        } else {
            return new Result<>(msg, data);
        }
    }

    @Override
    public Result getInvitation(int teacherId, HttpServletRequest request) {
        Map<String, Object> data = new HashMap<>();
        String msg;
        boolean rollback = false;
        Teacher teacher = getTeacherByTeacherId(teacherId);
        if (teacher != null) {
            String code = System.currentTimeMillis() + StringUtil.getRandomString(19);
            Invitation invitation = new Invitation();
            invitation.setCode(code);
            invitation.setInviterId(teacherId);
            invitationMapper.add(invitation);
            data.put("invitation", invitation);
            msg = "获取邀请码成功！";
        } else {
            msg = "不存在该教师！";
            rollback = true;
        }
        if (rollback) {
            return new Result(msg);
        } else {
            return new Result<>(msg, data);
        }
    }

    @Override
    public Result releaseCourse(int teacherId, String courseName, Date beginTime, Date endTime) {
        Map<String, Object> data = new HashMap<>();
        String msg;
        boolean rollback = false;
        Teacher teacher = teacherMapper.queryById(teacherId);
        if (teacher != null){
            Course course = new Course();
            course.setCourseName(courseName);
            course.setBelongTeacherId(teacherId);
            course.setBeginTime(beginTime);
            course.setEndTime(endTime);
            courseMapper.add(course);
            data.put("course", course);
            msg = "发布课程成功！";
        }else {
            rollback = true;
            msg = "不存在该教师！";
        }
        if (rollback) {
            return new Result(msg);
        } else {
            return new Result<>(msg, data);
        }
    }


    /**-----------------------------------------公共方法-----------------------------------------**/

    /**
     * 通过教师ID获取教师信息
     *
     * @param teacherId
     * @return
     */
    @Override
    public Teacher getTeacherByTeacherId(int teacherId) {
        return teacherMapper.queryById(teacherId);
    }
}
