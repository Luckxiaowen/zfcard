package com.zf.aspect;

import com.alibaba.fastjson.JSON;

import com.zf.annoticon.SysLog;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Component
@Aspect
@Slf4j
public class LogAspect {
//    @Autowired
//    private VisitLogService visitLogService;
//
//    @Pointcut("@annotation(com.wen.annoticon.SysLog)")
//    public void pt() {
//
//    }
//
//    @Around("pt()")
//    public Object printLog(ProceedingJoinPoint joinPoint) throws Throwable {
//        VisitLog visitLog = new VisitLog();
//
//
//        Object res;
//        try {
//            handleBefore(joinPoint, visitLog);
//            res = joinPoint.proceed();
//            handleAfter(res, visitLog);
//        } finally {
//            //log.info("===============END===============" + System.lineSeparator());
//            visitLogService.save(visitLog);
//        }
//
//        return res;
//    }
//
//    private void handleBefore(ProceedingJoinPoint joinPoint, VisitLog visitLog) {
//
//
//        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
//        HttpServletRequest request = requestAttributes.getRequest();
//
//        //获取被增强方法的注解对象
//        SysLog sysLog = getSysLog(joinPoint);
//
//
//        //log.info("===============Start===============");
//        // 打印请求 URL
//        //log.info("请求接口: {}",request.getRequestURI());
//        visitLog.setVisitInterface(request.getRequestURI());
//        // 打印描述信息
//        //log.info("接口描述: {}", sysLog.businessName());
//        visitLog.setInterfaceDescribe(sysLog.businessName());
//        // 打印 Http method
//        //log.info("请求方式: {}", request.getMethod());
//        visitLog.setRequestMethod(request.getMethod());
//        // 打印调用 controller 的全路径以及执行方法
//        //log.info("方法类名: {}.{}",joinPoint.getSignature().getDeclaringTypeName(),((MethodSignature)joinPoint.getSignature()).getName());
//        // 打印请求的 IP
//        //log.info("请求IP : {}",request.getRemoteHost());
//        visitLog.setVisitIp(request.getRemoteHost());
//        // 打印请求入参
//        Signature signature = joinPoint.getSignature();
//        // 参数名数组
//        String[] parameterNames = ((MethodSignature) signature).getParameterNames();
//        //log.info("请求参数: {}", JSON.toJSONString(joinPoint.getArgs()));
//        visitLog.setRequestArgs(null);
//    }
//
//    private SysLog getSysLog(ProceedingJoinPoint joinPoint) {
//        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
//        SysLog sysLog = signature.getMethod().getAnnotation(SysLog.class);
//        return sysLog;
//    }
//
//    private void handleAfter(Object res, VisitLog visitLog) {
//        // 打印出参
//        ResponseResult r = (ResponseResult) res;
//
//        //log.info("响应结果: {}",JSON.toJSONString("code:" +r.getCode() + " , " + "msg:" + r.getMsg()));
//        visitLog.setResponseResult(JSON.toJSONString("code:" + r.getCode() + " , " + "msg:" + r.getMsg()));
//    }
}
