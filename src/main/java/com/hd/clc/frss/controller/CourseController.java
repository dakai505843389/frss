package com.hd.clc.frss.controller;

import com.hd.clc.frss.common.Result;
import com.hd.clc.frss.service.ICourseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RequestMapping(value = "/course")
@RestController
@CrossOrigin(value = "*", maxAge = 3600)
@Api(value = "CourseController", tags = "课程")
public class CourseController {

    @Autowired
    private ICourseService courseService;

    @RequestMapping(value ="getSignList", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation("教师查询签到")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "teacherId", value = "教师ID", required = true, dataType = "int", paramType = "form"),
            @ApiImplicitParam(name = "courseId", value = "课程ID", required = true, dataType = "int", paramType = "form")
    })
    public Result getSignList(@RequestParam int teacherId,
                              @RequestParam int courseId){
        return courseService.getSignList(teacherId, courseId);
    }

}
