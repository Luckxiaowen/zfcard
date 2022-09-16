package com.zf.controller;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(value = "测试", tags = "测试")
public class TestController {

    @ApiOperation(value = "测试")
    @GetMapping("/test")
    public String listCategory(){
        return "ok";
    }
}
