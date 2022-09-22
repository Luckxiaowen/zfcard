package com.zf.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zf.domain.entity.CompanyInfo;
import com.zf.domain.vo.ResponseVo;

/**
* @author Amireux
* @description 针对表【company_info(公司简介)】的数据库操作Service
* @createDate 2022-09-16 08:47:16
*/
public interface CompanyInfoService extends IService<CompanyInfo> {

    ResponseVo insert(String token, CompanyInfo companyInfo);

    ResponseVo delete(String subject, Long cominfoid);

    ResponseVo modify(String userId, CompanyInfo companyInfo);


    ResponseVo selectAll(String userId);
}
