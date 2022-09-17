package com.zf.controller;

import com.zf.domain.entity.SysUser;
import com.zf.domain.vo.ResponseVo;
import com.zf.service.LoginService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@Api(value = "登录与注销接口管理", tags = "登录与注销接口管理")
@RestController
public class LoginController {

    @Autowired
    private LoginService loginService;

    @ApiOperation(value = "用户登录接口")
    @ApiImplicitParams({
            @ApiImplicitParam(dataType = "string", name = "username", value = "用户登录账号", required = true),
            @ApiImplicitParam(dataType = "string", name = "password", value = "用户登录密码", required = true)
    })
    @PostMapping("/login")
    public ResponseVo login(@RequestParam(value = "username")String username, @RequestParam(value = "password")String password){
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

}
