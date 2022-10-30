package com.zf.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zf.domain.entity.Company;
import com.zf.domain.vo.NewCompanyVo;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
* @author Amireux
* @description 针对表【company(公司表)】的数据库操作Mapper
* @createDate 2022-09-16 08:47:16
* @Entity com.zf.domain.Company
*/
@Repository
public interface CompanyMapper extends BaseMapper<Company> {


    List<Company> selectByConditions(@Param("conditions") Object conditions, @Param("status")String status,@Param("num")int num);

    NewCompanyVo selectOneCompany(Integer companyId);
}
