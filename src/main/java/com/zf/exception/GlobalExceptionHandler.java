package com.zf.exception;


import com.zf.domain.vo.ResponseVo;
import com.zf.enums.AppHttpCodeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Objects;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    /**
     * 自定义系统异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler(SystemException.class)
    public ResponseVo systemExceptionHandler(SystemException e) {
        //打印异常信息
        log.error("出现了异常！ {}", e);
        //从异常对象中获取提示信息封装返回
        return ResponseVo.errorResult(e.getCode(), e.getMsg());
    }

    /**
     * 登录异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseVo loginExceptionHandler(UsernameNotFoundException e) {
        //打印异常信息
        log.error("出现了异常！ {}", e);
        //从异常对象中获取提示信息封装返回
        return ResponseVo.errorResult(AppHttpCodeEnum.LOGIN_ERROR);
    }

    /**
     * 空指针异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler(NullPointerException.class)
    public ResponseVo nullPointerExceptionHandler(NullPointerException e) {
        //打印异常信息
        log.error("出现了异常！ {}", e);
        //从异常对象中获取提示信息封装返回
        return ResponseVo.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);
    }

    /**
     * 权限异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseVo accessDeniedExceptionHandler(AccessDeniedException e) {
        //打印异常信息
        log.error("出现了异常！ {}", e);
        //从异常对象中获取提示信息封装返回
        return ResponseVo.errorResult(AppHttpCodeEnum.NO_OPERATOR_AUTH);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseVo methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e){
        //打印异常信息
        log.error("出现了异常！ {}", e);
        //从异常对象中获取提示信息封装返回
        return ResponseVo.errorResult(405, Objects.requireNonNull(e.getFieldError()).getDefaultMessage());
    }

    /**
     * 其他异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler(Exception.class)
    public ResponseVo exceptionHandler(Exception e) {
        //打印异常信息
        log.error("出现了异常！ {}", e);
        //从异常对象中获取提示信息封装返回
        if (e instanceof InternalAuthenticationServiceException) {
            return ResponseVo.errorResult(AppHttpCodeEnum.LOGIN_ERROR.getCode(), e.getMessage());
        }
        return ResponseVo.errorResult(AppHttpCodeEnum.SYSTEM_ERROR.getCode(), e.getMessage());
    }
}
