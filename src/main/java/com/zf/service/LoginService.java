package com.zf.service;

import com.zf.domain.entity.SysUser;
import com.zf.domain.vo.ResponseVo;

import java.io.IOException;

public interface LoginService {
    ResponseVo login(SysUser sysUser);

    ResponseVo logout();

    ResponseVo getCode(String email);

    ResponseVo wxAuthLogin(String code) throws IOException;

    ResponseVo getWxOpenId(String code) throws IOException;
}
