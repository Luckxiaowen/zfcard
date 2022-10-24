package com.zf.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import com.zf.domain.vo.CompanyClientVo;
import com.zf.domain.vo.CompanyVo;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyVoMapper extends BaseMapper<CompanyVo> {

    Page<CompanyVo> selectByCreatBy(Page<CompanyVo> page);

//    Page<CompanyVo> selectByCreatBy(Page<CompanyVo> page, String userId);
}
