package com.zf.service;

import com.zf.domain.entity.CompanyCase;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zf.domain.vo.ResponseVo;

/**
* @author Amireux
* @description 针对表【company_case(公司案列分类表)】的数据库操作Service
* @createDate 2022-09-16 08:47:16
*/
public interface CompanyCaseService extends IService<CompanyCase> {

    ResponseVo addCompanyCase(String token, String caseName);

    ResponseVo deleteCompanyCase(String userId, Long comCaseId);
}
