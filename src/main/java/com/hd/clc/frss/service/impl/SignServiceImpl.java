package com.hd.clc.frss.service.impl;

import com.hd.clc.frss.common.BaseVar;
import com.hd.clc.frss.common.FaceUtil;
import com.hd.clc.frss.common.Result;
import com.hd.clc.frss.db.entity.Course;
import com.hd.clc.frss.db.entity.Sign;
import com.hd.clc.frss.db.entity.Student;
import com.hd.clc.frss.db.entity.Teacher;
import com.hd.clc.frss.db.impl.CourseMapper;
import com.hd.clc.frss.db.impl.SignMapper;
import com.hd.clc.frss.db.impl.StudentMapper;
import com.hd.clc.frss.interceptor.UserInterceptor;
import com.hd.clc.frss.service.ISignService;
import com.hd.clc.frss.vo.FaceUtilVO.FaceSearchResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class SignServiceImpl implements ISignService {

    @Autowired
    private CourseMapper courseMapper;

    @Autowired
    private StudentMapper studentMapper;

    @Autowired
    private SignMapper signMapper;

    @Override
    public Result sign(int courseId, String imagePath) {
        Map<String, Object> data = new HashMap<>();
        String msg;
        boolean rollback = false;
        try {
            FaceSearchResultVO faceSearchResultVO = FaceUtil.search(BaseVar.BASE_IP + imagePath, BaseVar.FACESET_TOKEN);
            if (faceSearchResultVO.getError_message() == null) {
                if (faceSearchResultVO.getConfidence() >= faceSearchResultVO.getE_3()) {
                    Student student = studentMapper.queryByFace_Token(faceSearchResultVO.getFace_token());
                    Course course = courseMapper.queryById(courseId);
                    Sign sign = new Sign();
                    sign.setSignTime(new Date(System.currentTimeMillis()));
                    if (new Date(System.currentTimeMillis() - 15*60*1000).before(course.getBeginTime())){
                        sign.setStatus(1);
                    }else {
                        sign.setStatus(2);
                    }
                    sign.setCourseId(courseId);
                    sign.setStudentId(student.getId());
                    sign.setStudentNumber(student.getNumber());
                    sign.setStudentName(student.getName());
                    signMapper.sign(sign);
                    data.put("sign", sign);
                    msg = student.getName() + "签到成功！";
                }else {
                    rollback = true;
                    msg = "签到失败，数据库中无相匹配的人脸数据！";
                }
            } else {
                rollback = true;
                msg = "在集合中匹配搜索失败：" + faceSearchResultVO.getError_message();
            }
        } catch (IOException e) {
            e.printStackTrace();
            rollback = true;
            msg = "请求face++出错！";
        } catch (Exception e) {
            e.printStackTrace();
            rollback = true;
            msg = "签到失败，请稍后再试！";
        }
        if (rollback) {
            return new Result(msg);
        } else {
            return new Result<>(msg, data);
        }
    }
}
