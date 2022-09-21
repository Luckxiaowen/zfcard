package com.zf.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zf.domain.entity.CompanyRole;
import com.zf.domain.vo.ResponseVo;

/**
 * @Author wenqin
 * @Date 2022/9/21 13:41
 */

public interface CompanyRoleService extends IService<CompanyRole> {
    ResponseVo getCompanyFramework(String token);

    ResponseVo addCompanyRole(CompanyRole companyRole);
}
