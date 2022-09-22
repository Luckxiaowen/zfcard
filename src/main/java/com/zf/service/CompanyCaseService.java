package com.zf.service;

import com.zf.domain.entity.CompanyCase;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zf.domain.vo.ResponseVo;
import org.apache.ibatis.annotations.Param;

import com.zf.domain.vo.ResponseVo;

/**
* @author Amireux
* @description 针对表【company_case(公司案列分类表)】的数据库操作Service
* @createDate 2022-09-16 08:47:16
*/
public interface CompanyCaseService extends IService<CompanyCase> {

    ResponseVo getcaseNames(@Param("token") String token);
    ResponseVo addCompanyCase(String userId, String caseName);

    ResponseVo deleteCompanyCase(String userId, Long comCaseId);

    ResponseVo updateCompanyCase(String userId, CompanyCase companyCase);

    ResponseVo selectAll(String userId);
}
