package com.zf.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zf.domain.entity.SysUser;
import com.zf.domain.vo.ResponseVo;
import com.zf.mapper.SysUserMapper;
import com.zf.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
* @author Amireux
* @description 针对表【sys_user(用户表)】的数据库操作Service实现
* @createDate 2022-09-16 08:47:17
*/
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

    @Autowired
    private SysUserMapper sysUserMapper;

    @Override
    public ResponseVo login(String username, String password) {

        return null;
    }
}
