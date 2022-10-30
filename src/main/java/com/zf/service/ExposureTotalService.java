package com.zf.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zf.domain.entity.ExposureTotal;
import com.zf.domain.vo.ResponseVo;

import java.util.Date;

/**
* @author Amireux
* @description 针对表【exposure_total(曝光统计)】的数据库操作Service
* @createDate 2022-09-16 08:47:17
*/
public interface ExposureTotalService extends IService<ExposureTotal> {

    ResponseVo getVisitorNum(String token);

    ResponseVo getSevenVisitorTrend(String userId);

    ResponseVo getExposureHistory(String userId, String startTime, String endTime);

    ResponseVo updateVisitor(String userId);

    ResponseVo getDayData(String userId);
}
