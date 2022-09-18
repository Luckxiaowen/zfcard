package com.zf.service;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zf.domain.entity.SysUser;
import com.zf.domain.vo.ResponseVo;
import org.springframework.stereotype.Service;

/**
* @author Amireux
* @description 针对表【sys_user(用户表)】的数据库操作Service
* @createDate 2022-09-16 08:47:17
*/
public interface SysUserService extends IService<SysUser> {


    ResponseVo add(SysUser sysUser,String updateId);

    ResponseVo delete(long userid,String updateId);

    ResponseVo modify(SysUser sysUser,String updateId);

    ResponseVo selectAll();
}
