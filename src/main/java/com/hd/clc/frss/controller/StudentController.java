package com.hd.clc.frss.controller;

import com.hd.clc.frss.common.Result;
import com.hd.clc.frss.service.IStudentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RequestMapping(value = "/student")
@CrossOrigin(value = "*", maxAge = 3600)
@RestController
@Api(value = "StudentController", tags = "学生相关接口")
public class StudentController {

    @Autowired
    private IStudentService studentService;

    @RequestMapping(value = "register", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation("学生注册")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "number", value = "学号", required = true, dataType = "int", paramType = "form"),
            @ApiImplicitParam(name = "name", value = "姓名", required = true, dataType = "String", paramType = "form"),
            @ApiImplicitParam(name = "imagePath", value = "照片", required = true, dataType = "String", paramType = "form")
    })
    public Result register(@RequestParam int number,
                           @RequestParam String name,
                           @RequestParam String imagePath){
        return studentService.register(number, name, imagePath);
    }
}
