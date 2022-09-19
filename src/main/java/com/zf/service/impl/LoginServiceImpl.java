package com.zf.service.impl;

import com.zf.domain.entity.SysUser;
import com.zf.domain.vo.LoginUser;
import com.zf.domain.vo.ResponseVo;
import com.zf.enums.AppHttpCodeEnum;
import com.zf.service.LoginService;
import com.zf.utils.JwtUtil;
import com.zf.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private RedisCache redisCache;

    @Override
    public ResponseVo login(SysUser sysUser) {
        // TODO 使用authenticationManger authenticate 进行用户认证
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(sysUser.getUsername(), sysUser.getPassword());
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);
        // TODO 没通过给出相应的注解
        if (Objects.isNull(authenticate)) {
            throw new RuntimeException("登录失败");
        }
        //TODO 如果认证同通过了，使用userId生成一个jwt
        LoginUser loginUser = (LoginUser) authenticate.getPrincipal();
        String userId = loginUser.getSysUser().getId().toString();
        String token = JwtUtil.createJWT(userId);
        Map<String, String> map = new HashMap<>();
        map.put("token",token);
        //TODO 把完整的用户信息存入到redis userid作为key
        redisCache.setCacheObject("login:"+userId,loginUser);
        return new ResponseVo(AppHttpCodeEnum.SUCCESS.getCode(), AppHttpCodeEnum.SUCCESS.getMsg(),map);
    }

    @Override
    public ResponseVo logout() {
        UsernamePasswordAuthenticationToken authenticationToken = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        LoginUser loginUser = (LoginUser) authenticationToken.getPrincipal();
        Long userId = loginUser.getSysUser().getId();
        redisCache.deleteObject("login:"+userId);
        return new ResponseVo(AppHttpCodeEnum.SUCCESS.getCode(),"注销成功",null);
    }


}
