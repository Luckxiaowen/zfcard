package com.zf.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.zf.domain.entity.SysUser;
import com.zf.domain.vo.LoginUser;
import com.zf.domain.vo.ResponseVo;
import com.zf.enums.AppHttpCodeEnum;
import com.zf.mapper.SysUserMapper;
import com.zf.service.SysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequestMapping("/user")
@Api(tags = "个人简介")
public class UserInfoController {

    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private SysUserService sysUserService;


    @ApiOperation(value = "个人简介接口")
    @GetMapping("/userinfo")
    @ApiImplicitParams({
            @ApiImplicitParam(dataType = "string", name = "userId", value = "员工id或者员工token", required = true),
    })
    public ResponseVo getUserInfo(@RequestParam("userId")String userId) {
        return sysUserService.selectUserInfoByWu(userId);
    }

    @ApiOperation(value = "个人职业照接口")
    @GetMapping("/professional-photo")
    @ApiImplicitParams({
            @ApiImplicitParam(dataType = "string", name = "userId", value = "员工id或者员工token", required = true),
    })
    public ResponseVo ProPhoto(@RequestParam("userId")String userId) {
        return sysUserService.selectUserProPhotoByWu(userId);
    }


}
