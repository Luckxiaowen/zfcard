package com.zf.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zf.domain.entity.ExposureTotal;
import com.zf.domain.vo.ExposureVo;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
* @author Amireux
* @description 针对表【exposure_total(曝光统计)】的数据库操作Mapper
* @createDate 2022-09-16 08:47:17
* @Entity com.zf.domain.ExposureTotal
*/
@Repository
public interface ExposureTotalMapper extends BaseMapper<ExposureTotal> {


    List<Integer> selectSevenDayByExposureTotal(@Param("expoTotalId") Long expoTotalId, @Param("sevenDate") List<String> sevenDate);



}
