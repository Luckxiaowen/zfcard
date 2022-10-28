package com.zf.controller;

import com.zf.domain.vo.ResponseVo;
import com.zf.service.ExposureTotalService;
import com.zf.utils.JwtUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;

@RestController
@RequestMapping("/exposure")
@Api(tags = "曝光统计")
public class ExposureController {

    @Resource
    private ExposureTotalService exposureTotalService;


    @ApiOperation(value ="访客概况接口")
    @GetMapping("/visitor-outline")
    public ResponseVo getVisitorNum(@RequestHeader("token")String token) throws Exception {
        return exposureTotalService.getVisitorNum(JwtUtil.parseJWT(token).getSubject());
    }


    @ApiOperation(value ="近七日访客趋势接口")
    @GetMapping("/seven_visitor_trend")
    public ResponseVo sevenVisitorTrend(@RequestHeader("token")String token) throws Exception {
        return exposureTotalService.getSevenVisitorTrend(JwtUtil.parseJWT(token).getSubject());
    }

    @ApiOperation(value = "历史数据接口")
    @GetMapping("/exposure-history")
    @ApiImplicitParams({
            @ApiImplicitParam(dataType = "string", name = "startTime", value = "开始时间", required = false),
            @ApiImplicitParam(dataType = "string", name = "endTime", value = "结束时间", required = false),
    })
    public ResponseVo exposureHistory(@RequestHeader("token") String token, String startTime, String endTime) throws Exception {
        return exposureTotalService.getExposureHistory(JwtUtil.parseJWT(token).getSubject(),startTime,endTime);
    }

    @ApiOperation(value = "访客量增加接口")
    @PutMapping("/add-visitor/{userId}")
    @ApiImplicitParams({
            @ApiImplicitParam(dataType = "string", name = "userId", value = "员工id", required = true),
    })
    public ResponseVo addVisitor(@PathVariable("userId") String userId){
        return exposureTotalService.updateVisitor(userId);
    }
}
