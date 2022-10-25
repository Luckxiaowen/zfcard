package com.zf.controller.admin;

import com.zf.domain.vo.ResponseVo;
import com.zf.service.DepartmentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.text.ParseException;

/**
 * @Author wenqin
 * @Date 2022/10/19 21:45
 */

@RestController
@RequestMapping("admin")
@Api(tags = "PC首页管理",value = "PC首页管理")
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
    public ResponseVo getCardExposure() throws ParseException {
        return departmentService.getCardExposure();
    }

    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "token", required = true),
    })
    @ApiOperation(value = "首页名片客户数量折线图")
    @GetMapping("/mouth-clientnum")
    public ResponseVo getClientNum(){
        return departmentService.getMouthClientNum();
    }

    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "token", required = true),
    })
    @ApiOperation(value = "本月优秀人员")
    @GetMapping("/mouth-excellent")
    public ResponseVo getMouthExcellent(){
        return departmentService.getMouthExcellent();
    }

}
