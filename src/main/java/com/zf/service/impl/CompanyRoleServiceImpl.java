package com.zf.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zf.domain.entity.CompanyRole;
import com.zf.domain.vo.LoginUser;
import com.zf.domain.vo.ResponseVo;
import com.zf.enums.AppHttpCodeEnum;
import com.zf.exception.SystemException;
import com.zf.mapper.CompanyRoleMapper;
import com.zf.service.CompanyRoleService;
import com.zf.service.CompanyService;
import com.zf.utils.JwtUtil;
import com.zf.utils.UserUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

/**
 * @Author wenqin
 * @Date 2022/9/21 13:42
 */
@Service
@Slf4j
public class CompanyRoleServiceImpl extends ServiceImpl<CompanyRoleMapper, CompanyRole> implements CompanyRoleService {

    @Resource
    private CompanyService companyService;

    @Override
    public ResponseVo getCompanyFramework(String token) {
        LoginUser loginUser = UserUtils.getLoginUser();
        Long companyId = loginUser.getSysUser().getCompanyid();
        if (companyId == null)throw new SystemException(AppHttpCodeEnum.SYSTEM_ERROR);
        LambdaQueryWrapper<CompanyRole> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.eq(CompanyRole::getCompanyId,companyId);
        queryWrapper.eq(CompanyRole::getParentId,-1);
        List<CompanyRole> companyRoleList = list(queryWrapper);
        for (CompanyRole companyRole : companyRoleList) {
            List<CompanyRole> childrenRoleList = getChildrenRole(companyRole.getId());
            companyRole.setChildren(childrenRoleList);
        }
        return ResponseVo.okResult(companyRoleList);
    }

    @Override
    public ResponseVo addCompanyRole(CompanyRole companyRole) {
        LoginUser loginUser = UserUtils.getLoginUser();
        Long companyId = loginUser.getSysUser().getCompanyid();
        if (companyService.getById(companyId) == null)
            throw new SystemException(AppHttpCodeEnum.COMPANY_NOF_FIND);

        if (companyRole.getParentId() == null)
            companyRole.setParentId(-1);

        companyRole.setCompanyId(companyId);

        companyRole.setCreateBy(Math.toIntExact(loginUser.getSysUser().getId()));
        companyRole.setUpdateBy(Math.toIntExact(loginUser.getSysUser().getId()));

        boolean res = save(companyRole);
        return res ? ResponseVo.okResult() : ResponseVo.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);
    }

    @Override
    public ResponseVo updateCompanyRole(CompanyRole companyRole) {

        updateById(companyRole);
        return null;
    }

    public List<CompanyRole> getChildrenRole(Integer id){
        LambdaQueryWrapper<CompanyRole> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(CompanyRole::getParentId,id);
        return list(queryWrapper);
    }
}
