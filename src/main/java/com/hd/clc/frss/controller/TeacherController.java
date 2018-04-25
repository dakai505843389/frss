package com.hd.clc.frss.controller;

import com.hd.clc.frss.common.Result;
import com.hd.clc.frss.service.ITeacherService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RequestMapping(value = "/teacher")
@CrossOrigin(value = "*", maxAge = 3600)
@RestController
@Api(value = "TeacherController", tags = "老师相关接口")
public class TeacherController {

    @Autowired
    private ITeacherService teacherService;

    @RequestMapping(value = "/getSign", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "教师获取邀请码")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "teacherId", value = "teacherId", required = true, dataType = "int", paramType = "form")
    })
    public Result getInvitation(@RequestParam int teacherId, HttpServletRequest request){
        return teacherService.getInvitation(teacherId, request);
    }

    @RequestMapping(value = "register", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "注册")
    @ApiImplicitParams({
        @ApiImplicitParam(value = "account", name = "帐号", required = true, dataType = "String", paramType = "form"),
        @ApiImplicitParam(value = "password", name = "密码", required = true, dataType = "String", paramType = "form"),
        @ApiImplicitParam(value = "name", name = "姓名", required = true, dataType = "String", paramType = "form"),
        @ApiImplicitParam(value = "invitationCode", name = "邀请码", required = true, dataType = "String", paramType = "form")
    })
    public Result register(@RequestParam String account,
                           @RequestParam String password,
                           @RequestParam String name,
                           @RequestParam String invitationCode){
        return teacherService.register(account, password, name, invitationCode);
    }

    @RequestMapping(value = "releaseCourse", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "发布课程")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "teacherId", value = "教师ID", required = true, dataType = "int", paramType = "form"),
            @ApiImplicitParam(name = "courseName", value = "课程名", required = true, dataType = "String", paramType = "form"),
            @ApiImplicitParam(name = "beginTime", value = "开始时间（yyyy-mm-dd HH:mm:ss）", required = true, dataType = "String", paramType = "form"),
            @ApiImplicitParam(name = "endTime", value = "结束时间（yyyy-mm-dd HH:mm:ss）", required = true, dataType = "String", paramType = "form")
    })
    public Result releaseCourse(@RequestParam int teacherId,
                                @RequestParam String courseName,
                                @RequestParam @DateTimeFormat(pattern = "yyyy-mm-dd HH:mm:ss") Date beginTime,
                                @RequestParam @DateTimeFormat(pattern = "yyyy-mm-dd HH:mm:ss") Date endTime){
        return teacherService.releaseCourse(teacherId, courseName, beginTime, endTime);
    }




}
