package com.zf.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zf.domain.entity.Company;
import com.zf.domain.entity.CompanyInfo;
import com.zf.domain.vo.CompanyProfileVo;
import com.zf.domain.vo.ResponseVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CompanyProfileVoMapper extends BaseMapper<CompanyProfileVo> {

    List<CompanyProfileVo> companyProfile(@Param("companyId") Integer companyId);

}
