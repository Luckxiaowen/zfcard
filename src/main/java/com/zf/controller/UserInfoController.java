package com.zf.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.zf.domain.entity.SysUser;
import com.zf.domain.vo.LoginUser;
import com.zf.domain.vo.ResponseVo;
import com.zf.enums.AppHttpCodeEnum;
import com.zf.mapper.SysUserMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
@RequestMapping("/user")
@Api(tags = "个人简介")
public class UserInfoController {

    @Autowired
    private SysUserMapper sysUserMapper;


    @ApiOperation(value = "个人简介接口")
    @GetMapping("/userinfo")
    public ResponseVo info(@RequestHeader("token")String token){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        Integer id = Math.toIntExact(loginUser.getSysUser().getId());
        //        将查询条件放入LambdaQueryWrapper中
        LambdaQueryWrapper<SysUser> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.like(SysUser::getId,id);

        SysUser sysUser = sysUserMapper.selectOne(lambdaQueryWrapper);

        String info = sysUser.getInfo();

        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("info",info);
        ResponseVo<HashMap<String, String>> vo = new ResponseVo<>(AppHttpCodeEnum.SUCCESS.getCode(), AppHttpCodeEnum.SUCCESS.getMsg(), hashMap);
        return vo;
    }

    @ApiOperation(value = "个人职业照接口")
    @GetMapping("/professional-photo")
    public ResponseVo ProPhoto(@RequestHeader("token")String token){

//        获取token
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        Integer id = Math.toIntExact(loginUser.getSysUser().getId());
//        将查询条件放入LambdaQueryWrapper中
        LambdaQueryWrapper<SysUser> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.like(SysUser::getId,id);
        SysUser sysUser = sysUserMapper.selectOne(lambdaQueryWrapper);
        String avatar = sysUser.getAvatar();
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("photo",avatar);
        new ResponseVo<>(AppHttpCodeEnum.SUCCESS.getCode(), AppHttpCodeEnum.SUCCESS.getMsg(),hashMap);
        return new ResponseVo(AppHttpCodeEnum.SUCCESS.getCode(), AppHttpCodeEnum.SUCCESS.getMsg(), hashMap);
    }

}
