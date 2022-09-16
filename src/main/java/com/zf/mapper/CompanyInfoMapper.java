package com.zf.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zf.domain.entity.CompanyInfo;
import org.springframework.stereotype.Repository;

/**
* @author Amireux
* @description 针对表【company_info(公司简介)】的数据库操作Mapper
* @createDate 2022-09-16 08:47:16
* @Entity com.zf.domain.CompanyInfo
*/
@Repository
public interface CompanyInfoMapper extends BaseMapper<CompanyInfo> {


}


