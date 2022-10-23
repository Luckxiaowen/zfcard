package com.zf.controller;

import com.zf.domain.vo.ResponseVo;
import com.zf.service.ExposureTotalService;
import com.zf.utils.JwtUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Api(value = "个人中心", tags = "个人中心")

@RestController
public class PersonalCenterController {

    @Resource
    private ExposureTotalService exposureTotalService;

    @ApiOperation(value = "个人中心数据查询")
    @GetMapping("/center")
    public ResponseVo<?> getVisitor(@RequestHeader("token")String token) throws Exception {
        return exposureTotalService.getDayData(JwtUtil.parseJWT(token).getSubject());
    }

}
