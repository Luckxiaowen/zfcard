package com.zf.controller.admin;

import com.zf.domain.dto.DingTalkDto;
import com.zf.domain.vo.ResponseVo;
import com.zf.service.DingTalkService;
import com.zf.utils.JwtUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/admin")
@Api(tags = "PC钉钉同步组织架构",value = "PC钉钉同步组织架构")
public class DingTalkController {

    @Resource
    private DingTalkService dingTalkService;

    @ApiOperation(value = "第三方钉钉互联接口")
    @PostMapping("/getDep")
    public ResponseVo<?>getDep(@RequestHeader("token")String token,@RequestBody DingTalkDto dingTalkDto) throws Exception {
        return dingTalkService.bindDingDing(JwtUtil.parseJWT(token).getSubject(),dingTalkDto);
    }

}
