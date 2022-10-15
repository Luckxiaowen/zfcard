package com.zf.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zf.domain.vo.CompanyClientVo;
import com.zf.domain.vo.SysUserVo;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CompanyClientVoMapper extends BaseMapper<CompanyClientVo> {

    Page<CompanyClientVo> selectByCreatBy(Page<CompanyClientVo> page, String userid);
    Page<CompanyClientVo> selectByLike(Page<CompanyClientVo> page, String userid,String query);



}
