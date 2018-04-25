package com.hd.clc.frss.controller;

import com.hd.clc.frss.common.FaceUtil;
import com.hd.clc.frss.common.Result;
import com.hd.clc.frss.service.IUtilService;
import com.hd.clc.frss.vo.FaceUtilVO.FaceVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

@RequestMapping("/util")
@CrossOrigin(value = "*", maxAge = 3600)
@RestController
@Api(value = "UtilController", tags = "工具类")
public class UtilController {

    @Autowired
    private IUtilService utilService;

    @RequestMapping(value = "detectFaceTest", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation("测试用")
    public Result detectFaceTest(@RequestParam String imagePath){
        Map<String, Object> data = new HashMap<>();
        try {
            FaceVO faceVO = FaceUtil.detect(imagePath);
            data.put("faceVO", faceVO);
        }catch (Exception e){
            e.printStackTrace();
        }
        return new Result<>(data);
    }

    @RequestMapping(value = "uploadImage", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation("上传图片接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "imageFile", value = "图片文件", required = true, dataType = "file", paramType = "form")
    })
    public Result uploadImage(@RequestParam MultipartFile imageFile){
        return utilService.uploadImage(imageFile);
    }

}
