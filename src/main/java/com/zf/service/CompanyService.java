package com.zf.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zf.domain.dto.CompanyDto;
import com.zf.domain.entity.Company;
import com.zf.domain.vo.ResponseVo;

import java.util.List;
import java.util.Map;

/**
* @author Amireux
* @description 针对表【company(公司表)】的数据库操作Service
* @createDate 2022-09-16 08:47:16
*/
public interface CompanyService extends IService<Company> {

    ResponseVo insert(CompanyDto companyDto);

    ResponseVo delete(Long companyid,String updateId);

    ResponseVo modify(CompanyDto companyDto);

    ResponseVo searchCompany(String conditions);
}
