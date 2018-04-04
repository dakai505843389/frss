package com.hd.clc.frss.controller;

import com.hd.clc.frss.common.Result;
import com.hd.clc.frss.service.ITeacherService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RequestMapping(value = "/teacher")
@CrossOrigin(value = "*", maxAge = 3600)
@RestController
@Api(value = "TeacherController", tags = "老师相关接口")
public class TeacherController {

    @Autowired
    private ITeacherService teacherService;

    @RequestMapping(value = "register", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "注册")
    @ApiImplicitParams({
        @ApiImplicitParam(value = "account", name = "帐号", required = true, dataType = "String", paramType = "form"),
        @ApiImplicitParam(value = "password", name = "密码", required = true, dataType = "String", paramType = "form"),
        @ApiImplicitParam(value = "name", name = "姓名", required = true, dataType = "String", paramType = "form"),
        @ApiImplicitParam(value = "invitation", name = "邀请码", required = true, dataType = "String", paramType = "form")
    })
    public Result register(@RequestParam String account,
                           @RequestParam String password,
                           @RequestParam String name,
                           @RequestParam String invitation){
        return teacherService.register(account, password, name, invitation);
    }



}
