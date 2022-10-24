package com.zf.controller.admin;

import com.zf.domain.dto.DingTalkDto;
import com.zf.domain.vo.ResponseVo;
import com.zf.service.DingTalkService;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/admin")
@Api(tags = "钉钉同步组织架构",value = "钉钉同步组织架构")
public class DingTalkController {

    @Resource
    private DingTalkService dingTalkService;

    @GetMapping("/getDep")
    public ResponseVo<?>getDep( ) throws Exception {
        return dingTalkService.getDep();
    }
}
