package com.zf.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zf.domain.entity.ExpoSnapshot;
import com.zf.domain.vo.ExposureVo;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
* @author Amireux
* @description 针对表【expo_snapshot(曝光快照表)】的数据库操作Mapper
* @createDate 2022-09-16 08:47:16
* @Entity com.zf.domain.ExpoSnapshot
*/
@Repository
public interface ExpoSnapshotMapper extends BaseMapper<ExpoSnapshot> {


    List<Integer> selectSevenDayByDate(@Param("expoTotalId") Long expoTotalId,@Param("sevenDate") List<String> sevenDate);

    List<ExpoSnapshot> selectSevenDayByTotalId(Long id, List<String> sevenDate);

    ExposureVo selectHistory(@Param("id")String id, @Param("startTime") String startTime, @Param("endTime")String endTime);

    ExposureVo selectHistoryWithOut(String id);

    List<ExposureVo> selectListById(@Param("id")Integer userId);

    List<ExposureVo> selectListByTime(@Param("id")String id, @Param("startTime") String startTime, @Param("endTime")String endTime);
}
