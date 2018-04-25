package com.hd.clc.frss.service.impl;

import com.hd.clc.frss.common.BaseVar;
import com.hd.clc.frss.common.FileUtil;
import com.hd.clc.frss.common.Result;
import com.hd.clc.frss.common.StringUtil;
import com.hd.clc.frss.db.entity.Invitation;
import com.hd.clc.frss.db.entity.Teacher;
import com.hd.clc.frss.service.IUtilService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
public class UtilServiceImpl implements IUtilService{

    @Override
    public Result uploadImage(MultipartFile image) {
        Map<String, Object> data = new HashMap<>();
        String msg;
        boolean rollback = false;
        try {
            String name = FileUtil.uploadStream(image.getInputStream(), BaseVar.BASE_IP + BaseVar.IMAGE_URL, image.getOriginalFilename());
            if (name != null) {
                String imagePath = BaseVar.IMAGE_URL + name;
                data.put("imagePath", imagePath);
                msg = "上传成功！";
            }else {
                msg = "图片格式错误！";
                rollback = true;
            }
        }catch (IOException e){
            msg = "保存文件出错！";
            rollback = true;
            e.printStackTrace();
        }catch (Exception e){
            msg = "上传图片失败！";
            rollback = true;
            e.printStackTrace();
        }
        if (rollback) {
            return new Result(msg);
        } else {
            return new Result<>(msg, data);
        }
    }

}
