package com.zf.service;

import com.zf.domain.vo.ResponseVo;

import java.text.ParseException;

/**
 * @Author wenqin
 * @Date 2022/10/19 22:00
 */

public interface DepartmentService {
    ResponseVo getdepartmentRank(int depId,String startTime,String endTime);

    ResponseVo getCardExposure() throws ParseException;

    ResponseVo getMouthClientNum();

    ResponseVo getMouthExcellent();

}
