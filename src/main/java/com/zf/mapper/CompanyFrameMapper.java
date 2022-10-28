package com.zf.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zf.domain.entity.CompanyFrame;
import com.zf.domain.vo.DepVo;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author wenqin
 * @Date 2022/9/21 13:40
 */
@Repository
public interface CompanyFrameMapper extends BaseMapper<CompanyFrame> {

    List<DepVo> selectListByList( List<Long>depIdList);
}
