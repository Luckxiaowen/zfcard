package com.zf.service;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zf.domain.entity.SysUser;
import com.zf.domain.vo.ResponseVo;

/**
* @author Amireux
* @description 针对表【sys_user(用户表)】的数据库操作Service
* @createDate 2022-09-16 08:47:17
*/

public interface SysUserService extends IService<SysUser> {

    ResponseVo login(String username, String password);
}
