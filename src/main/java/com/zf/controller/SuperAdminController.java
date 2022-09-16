package com.zf.controller;

import com.zf.domain.entity.SysUser;
import com.zf.domain.vo.ResponseVo;
import com.zf.enums.AppHttpCodeEnum;
import com.zf.service.CompanyService;
import com.zf.service.LoginService;
import com.zf.service.SysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@Api(value = "提供超级管理员对公司的增删改查", tags = "公司管理")
@RequestMapping("/admin")
public class SuperAdminController {

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private CompanyService companyService;

    @Autowired
    private LoginService loginService;

    @ApiOperation(value = "用户登录接口")
    @ApiImplicitParams({
            @ApiImplicitParam(dataType = "string", name = "username", value = "用户登录账号", required = true),
            @ApiImplicitParam(dataType = "string", name = "password", value = "用户登录密码", required = true)
    })
    @PostMapping("/login")
    public ResponseVo login(@RequestParam(value = "username")String username,@RequestParam(value = "password")String password){
        SysUser sysUser=new SysUser();
        sysUser.setUsername(username);
        sysUser.setPassword(password);
        return loginService.login(sysUser);
    }

    @ApiOperation(value = "用户注销接口")
    @PostMapping("/logout")
    public ResponseVo logout(@RequestHeader("token") String token){
       return  loginService.logout();
    }


    @ApiOperation(value = "公司列表查询接口")
    @GetMapping("/list-company")
    public ResponseVo list(@RequestHeader("token")String token){
       return new ResponseVo(AppHttpCodeEnum.SUCCESS.getCode(), AppHttpCodeEnum.SUCCESS.getMsg(),companyService.list());
    }
}
