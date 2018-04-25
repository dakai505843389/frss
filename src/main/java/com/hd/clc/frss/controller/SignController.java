package com.hd.clc.frss.controller;

import com.hd.clc.frss.common.Result;
import com.hd.clc.frss.service.ISignService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RequestMapping("/sign")
@CrossOrigin(value = "*", maxAge = 3600)
@RestController
@Api(value = "SignController", tags = "邀请码")
public class SignController {

    @Autowired
    private ISignService signService;

    @RequestMapping(value = "/sign", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation("签到接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "courseId", value = "课程ID", required = true, dataType = "int", paramType = "form"),
            @ApiImplicitParam(name = "imagePath", value = "照片路径", required = true, dataType = "String", paramType = "form")
    })
    public Result sign(@RequestParam int courseId,
                       @RequestParam String imagePath){
        return signService.sign(courseId, imagePath);
    }

}
