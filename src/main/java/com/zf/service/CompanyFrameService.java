package com.zf.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zf.domain.dto.AppKey;
import com.zf.domain.entity.CompanyFrame;
import com.zf.domain.vo.ResponseVo;

/**
 * @Author wenqin
 * @Date 2022/9/21 13:41
 */

public interface CompanyFrameService extends IService<CompanyFrame> {
    ResponseVo getCompanyFramework(String token);

    ResponseVo addCompanyRole(CompanyFrame companyFrame);

    ResponseVo updateCompanyRole(CompanyFrame companyFrame);

    ResponseVo<?> delCompanyFrameworkById(Long id);

    ResponseVo dingDingImport(AppKey appKey, String rootName);
}
