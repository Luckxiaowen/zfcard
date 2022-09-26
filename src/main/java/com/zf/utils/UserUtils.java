package com.zf.utils;

import com.zf.domain.vo.LoginUser;
import com.zf.enums.AppHttpCodeEnum;
import com.zf.exception.SystemException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Objects;

/**
 * @Author wenqin
 * @Date 2022/9/21 13:52
 */

public class UserUtils {
    public static LoginUser getLoginUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        if (Objects.isNull(loginUser))
            throw new SystemException(AppHttpCodeEnum.NEED_LOGIN);
        return  loginUser;
    }
}
