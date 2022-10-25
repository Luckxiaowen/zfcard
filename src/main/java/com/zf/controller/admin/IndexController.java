package com.zf.controller.admin;

import com.zf.domain.vo.ResponseVo;
import com.zf.service.DepartmentService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Author wenqin
 * @Date 2022/10/19 21:45
 */

@RestController
@RequestMapping("admin")
public class IndexController {
    @Resource
    private DepartmentService departmentService;


    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "token", required = true),
    })
    @ApiOperation(value = "获取首页部门统计")
    @GetMapping("/dep/rank")
    public ResponseVo getdepartmentRank(){
        return departmentService.getdepartmentRank();
    }

    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "token", required = true),
    })
    @ApiOperation(value = "首页名片曝光折线图")
    @GetMapping("/card-exp")
    public ResponseVo getCardExposure(){
        return departmentService.getCardExposure();
    }

}
