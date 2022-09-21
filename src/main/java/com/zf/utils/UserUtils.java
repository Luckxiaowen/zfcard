package com.zf.utils;

import com.zf.domain.vo.LoginUser;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * @Author wenqin
 * @Date 2022/9/21 13:52
 */

public class UserUtils {
    public static LoginUser getLoginUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return  (LoginUser) authentication.getPrincipal();
    }
}
