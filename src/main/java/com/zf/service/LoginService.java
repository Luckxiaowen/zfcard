package com.zf.service;

import com.zf.domain.entity.SysUser;
import com.zf.domain.vo.ResponseVo;

public interface LoginService {
    ResponseVo login(SysUser sysUser);

    ResponseVo logout();

    ResponseVo getCode(String email);
}
