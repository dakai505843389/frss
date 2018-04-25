package com.hd.clc.frss.service.impl;

import com.hd.clc.frss.common.FaceUtil;
import com.hd.clc.frss.common.Result;
import com.hd.clc.frss.db.entity.FaceSet;
import com.hd.clc.frss.db.impl.FaceSetMapper;
import com.hd.clc.frss.service.IFaceSetService;
import com.hd.clc.frss.vo.FaceUtilVO.FaceSetVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class FaceSetServiceImpl implements IFaceSetService {

    @Autowired
    private FaceSetMapper faceSetMapper;

    @Override
    public Result addNewFaceSet(String description) {
        Map<String, Object> data = new HashMap<>();
        String msg;
        boolean rollback = false;
        try {
            FaceSetVO faceSetVO = FaceUtil.createFaceSet();
            if (faceSetVO.getError_message() == null){
                FaceSet faceSet = new FaceSet();
                faceSet.setDescription(description);
                faceSet.setFaceset_token(faceSetVO.getFaceset_token());
                faceSet.setFace_count(faceSetVO.getFace_count());
                faceSet.setAddTime(new Date(System.currentTimeMillis()));
                faceSetMapper.add(faceSet);
                data.put("faceSet", faceSet);
                msg = "建立FaceSet成功！";
            }else {
                rollback = true;
                msg = "请求face++失败，返回错误信息：" + faceSetVO.getError_message();
            }
        }catch (IOException e){
            rollback = true;
            e.printStackTrace();
            msg = "请求face++出错！";
        }catch (Exception e){
            rollback = true;
            e.printStackTrace();
            msg = "建立FaceSet出错！";
        }
        if (rollback) {
            return new Result(msg);
        } else {
            return new Result<>(msg, data);
        }
    }
}
