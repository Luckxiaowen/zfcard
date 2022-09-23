package com.zf.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.zf.domain.entity.SysMenu;
import com.zf.domain.entity.SysUser;
import com.zf.domain.vo.LoginUser;
import com.zf.domain.vo.MenuVo;
import com.zf.domain.vo.ResponseVo;
import com.zf.enums.AppHttpCodeEnum;
import com.zf.mapper.SysUserMapper;
import com.zf.service.LoginService;
import com.zf.service.SysMenuService;
import com.zf.utils.*;
import com.zf.utils.emailutil.RandomUtil;
import com.zf.utils.emailutil.SendMailUtil;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.*;

@Service
public class LoginServiceImpl implements LoginService {


    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private RedisCache redisCache;

    @Resource
    private SysMenuService menuService;

    @Override
    public ResponseVo login(SysUser sysUser) {
        if (!Validator.isMobile(sysUser.getPhonenumber())) {
            return new ResponseVo(AppHttpCodeEnum.FAIL.getCode(), "手机号格式有误请检查");
        }

        // TODO 使用authenticationManger authenticate 进行用户认证
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(sysUser.getPhonenumber(), sysUser.getPassword());
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);
        // TODO 没通过给出相应的注解
        if (Objects.isNull(authenticate)) {
            throw new RuntimeException("登录失败");
        }
        //TODO 如果认证同通过了，使用userId生成一个jwt
        LoginUser loginUser = (LoginUser) authenticate.getPrincipal();
        String userId = loginUser.getSysUser().getId().toString();
        String token = JwtUtil.createJWT(userId);
        Map<String, Object> map = new HashMap<>();
        map.put("token", token);
        //获取动态菜单
        List<MenuVo> sysMenus = menuService.getSysMenuByUserId(Long.valueOf(userId));

        map.put("menu", sysMenus);
        //TODO 把完整的用户信息存入到redis userid作为key
        redisCache.setCacheObject("login:" + userId, loginUser);
        return new ResponseVo(AppHttpCodeEnum.SUCCESS.getCode(), AppHttpCodeEnum.SUCCESS.getMsg(), map);
    }

    @Override
    public ResponseVo logout() {
        UsernamePasswordAuthenticationToken authenticationToken = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        LoginUser loginUser = (LoginUser) authenticationToken.getPrincipal();
        Long userId = loginUser.getSysUser().getId();
        redisCache.deleteObject("login:" + userId);
        return new ResponseVo(AppHttpCodeEnum.SUCCESS.getCode(), "注销成功", null);
    }

    @Override
    public ResponseVo getCode(String email) {
        if (Validator.isEmail(email)) {
            String code = RandomUtil.randomCode();
            System.out.println("code = " + code);
            SendMailUtil.send(email, null, code);

            return new ResponseVo(AppHttpCodeEnum.SUCCESS.getCode(), "验证码发送成功", null);
        } else {
            return new ResponseVo(AppHttpCodeEnum.SUCCESS.getCode(), "邮箱格式有误", null);
        }
    }

    @Override
    public ResponseVo wxAuthLogin(String code) throws IOException {
        Map<String, Object> resultMap = WXUtils.getOpenId(code);
        String openId = (String) resultMap.get("openId");
        if (StringUtils.isEmpty(openId)) {
            return ResponseVo.okResult("未获取到openId");
        }else{
            LambdaQueryWrapper<SysUser>queryWrapper=new LambdaQueryWrapper<>();
            queryWrapper.eq(SysUser::getOpenedId,openId);
            SysUser sysUser = sysUserMapper.selectOne(queryWrapper);
            login(sysUser);
        }
        return null;
    }
}