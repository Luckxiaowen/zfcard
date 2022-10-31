package com.zf.controller;

import com.zf.domain.vo.ResponseVo;
import com.zf.service.PushService;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Api(tags = "微信推送接口")
@RestController
@RequestMapping("/push")
public class WXPushController {


    @Resource
    private PushService pushService;

    @GetMapping("/")
    public ResponseVo<?> pushWx(){
        return pushService.getAllOpenId();
    }

}
