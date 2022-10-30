package com.zf.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zf.domain.entity.CompanyFrame;
import com.zf.domain.vo.DepVo;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author wenqin
 * @Date 2022/9/21 13:40
 */
@Repository
public interface CompanyFrameMapper extends BaseMapper<CompanyFrame> {

    List<DepVo> selectListByList(@Param("depIdList") List<Long>depIdList,@Param("startTime") String startTime, @Param("endTime")String endTime,@Param("companyId")String companyId);
}
