package com.zf.controller;

import com.zf.domain.vo.ResponseVo;
import com.zf.service.SysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/user")
@Api(tags = "PC批量导入查重接口")
public class FilterTelController {

    @Resource
    private SysUserService sysUserService;

    @ApiOperation(value ="手机号查重")
    @ApiImplicitParams({
            @ApiImplicitParam(dataType = "string", name = "tel", value = "电话号码", required = true),
    })
    @GetMapping("/filter-tel")
    public ResponseVo filterTel(@RequestParam("tel")String tel){
        return sysUserService.flterTel(tel);
    }
}
