package com.hd.clc.frss.controller;

import com.hd.clc.frss.common.Result;
import com.hd.clc.frss.service.IFaceSetService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/faceSet")
@CrossOrigin(value = "*", maxAge = 3600)
@RestController
@Api(value = "FaceSetController", tags = "人脸集合")
public class FaceSetController {

    @Autowired
    private IFaceSetService faceSetService;

    @RequestMapping(value = "addNewFaceSet", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation("新建人脸集合")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "description", value = "介绍", required = true, dataType = "String", paramType = "form")
    })
    public Result addNewFaceSet(@RequestParam String description){
        return faceSetService.addNewFaceSet(description);
    }

}
