package com.zf.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zf.domain.entity.CompanyCase;
import org.springframework.stereotype.Repository;

/**
* @author Amireux
* @description 针对表【company_case(公司案列分类表)】的数据库操作Mapper
* @createDate 2022-09-16 08:47:16
* @Entity com.zf.domain.CompanyCase
*/
@Repository
public interface CompanyCaseMapper extends BaseMapper<CompanyCase> {


}
