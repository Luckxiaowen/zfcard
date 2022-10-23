package com.zf.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.zf.domain.entity.SysUser;
import com.zf.domain.vo.LoginUser;
import com.zf.mapper.SysMenuMapper;
import com.zf.mapper.SysUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private SysMenuMapper SysMenuMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String phoneNumber) throws UsernameNotFoundException {
        //TODO 查询用户信息
        LambdaQueryWrapper<SysUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysUser::getPhonenumber,phoneNumber);
        SysUser sysUser = sysUserMapper.selectOne(queryWrapper);
        //TODO 判断如果为空
        if (Objects.isNull(sysUser)) {
            throw new RuntimeException("用户名或者密码错误!");
        }
        // TODO 查询对应的权限信息
        List<String> list = SysMenuMapper.selectPermsByUserId(sysUser.getId());


        //TODO 封装数据返回
        return new LoginUser(sysUser,list);
    }
}
