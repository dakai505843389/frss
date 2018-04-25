package com.hd.clc.frss.service.impl;

import com.hd.clc.frss.common.BaseVar;
import com.hd.clc.frss.common.FaceUtil;
import com.hd.clc.frss.common.Result;
import com.hd.clc.frss.db.entity.FaceSet;
import com.hd.clc.frss.db.entity.Student;
import com.hd.clc.frss.db.impl.FaceSetMapper;
import com.hd.clc.frss.db.impl.StudentMapper;
import com.hd.clc.frss.service.IStudentService;
import com.hd.clc.frss.vo.FaceUtilVO.FaceSetVO;
import com.hd.clc.frss.vo.FaceUtilVO.FaceVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class StudentServiceImpl implements IStudentService {

    @Autowired
    private StudentMapper studentMapper;

    @Autowired
    private FaceSetMapper faceSetMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result register(int number, String name, String imagePath) {
        Map<String, Object> data = new HashMap<>();
        String msg;
        boolean rollback = false;
        try {
            FaceVO faceVO = FaceUtil.detect(BaseVar.BASE_IP + imagePath);
            if (faceVO.getError_message() == null){
                if (faceVO.getValue() >= faceVO.getThreshold()){
                    FaceSetVO faceSetVO = FaceUtil.addFace(BaseVar.FACESET_TOKEN, faceVO.getFace_token());
                    if (faceSetVO.getError_message() == null){
                        if (faceSetVO.getReason() == null){
                            Student student = new Student();
                            student.setNumber(number);
                            student.setName(name);
                            student.setImagePath(imagePath);
                            student.setFace_token(faceVO.getFace_token());
                            studentMapper.add(student);
                            FaceSet faceSet = faceSetMapper.queryById(BaseVar.TEST_FACESET_ID);
                            faceSet.setFace_count(faceSetVO.getFace_count());
                            faceSet.setUpdateTime(new Date(System.currentTimeMillis()));
                            faceSetMapper.update(faceSet);
                            data.put("student", student);
                            msg = "注册成功！";
                        }else {
                            rollback = true;
                            msg = "添加照片到集合失败：" + faceSetVO.getReason();
                        }
                    }else {
                        rollback = true;
                        msg = "添加照片到集合失败：" + faceSetVO.getError_message();
                    }
                }else {
                    rollback = true;
                    msg = "照片质量不符合要求，请重新拍摄上传！";
                }
            }else {
                rollback = true;
                msg = "注册照片失败：" + faceVO.getError_message();
            }
        }catch (IOException e){
            e.printStackTrace();
            rollback = true;
            msg = "注册照片出现异常！";
        }catch (Exception e){
            e.printStackTrace();
            rollback = true;
            msg = "注册失败，请稍后再试！";
        }
        if (rollback) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return new Result(msg);
        } else {
            return new Result<>(msg, data);
        }
    }

}
