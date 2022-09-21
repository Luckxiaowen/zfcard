package com.zf.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zf.domain.entity.CompanyCase;
import com.zf.domain.entity.CompanyInfo;
import com.zf.domain.entity.SysUser;
import com.zf.domain.vo.ResponseVo;
import com.zf.enums.AppHttpCodeEnum;
import com.zf.mapper.CompanyCaseMapper;
import com.zf.mapper.SysUserMapper;
import com.zf.service.CompanyCaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Objects;

/**
 * @author Amireux
 * @description 针对表【company_case(公司案列分类表)】的数据库操作Service实现
 * @createDate 2022-09-16 08:47:16
 */
@Service
public class CompanyCaseServiceImpl extends ServiceImpl<CompanyCaseMapper, CompanyCase> implements CompanyCaseService {

    @Autowired
    private SysUserMapper sysUserMapper;
    @Autowired
    private CompanyCaseMapper companyCaseMapper;

    @Override
    public ResponseVo addCompanyCase(String userId, String caseName) {
        if ("".equals(caseName) || caseName == null) {
            return new ResponseVo(AppHttpCodeEnum.FAIL.getCode(), "公司案列分类名称不能为空");
        } else {
            if (caseName.length() < 2) {
                return new ResponseVo(AppHttpCodeEnum.FAIL.getCode(), "公司案列分类名称不能小于两个字符");
            } else {
                if (Objects.isNull(sysUserMapper.selectById(Long.parseLong(userId)).getCompanyid())) {
                    return new ResponseVo(AppHttpCodeEnum.FAIL.getCode(), "当前用户未加入任何公司不能创建案列分类");
                } else {

                    LambdaQueryWrapper<CompanyCase> queryWrapper = new LambdaQueryWrapper<>();
                    queryWrapper.eq(CompanyCase::getCompanyId, sysUserMapper.selectById(Long.parseLong(userId)).getCompanyid());
                    queryWrapper.and(wrapper -> {
                        wrapper.eq(CompanyCase::getCaseName, caseName);
                        wrapper.eq(CompanyCase::getDelFlag, 0);
                    });
                    if (Objects.isNull(companyCaseMapper.selectOne(queryWrapper))) {
                        CompanyCase companyCase = new CompanyCase();
                        companyCase.setCompanyId(sysUserMapper.selectById(Long.parseLong(userId)).getCompanyid());
                        companyCase.setCcreateBy(Long.parseLong(userId));
                        companyCase.setCreateTime(new Date());
                        companyCase.setUpdateBy(Long.parseLong(userId));
                        companyCase.setUpdateTime(new Date());
                        companyCase.setCaseName(caseName);
                        companyCase.setDelFlag(0);
                        companyCaseMapper.insert(companyCase);
                        return new ResponseVo(AppHttpCodeEnum.SUCCESS.getCode(), "案列分类添加成功");
                    } else {
                        return new ResponseVo(AppHttpCodeEnum.FAIL.getCode(), "当前输入案列名称重复,请修改");
                    }
                }
            }
        }
    }

    @Override
    public ResponseVo deleteCompanyCase(String userId, Long comCaseId) {
        LambdaQueryWrapper<CompanyCase> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(CompanyCase::getCompanyId, sysUserMapper.selectById(Long.parseLong(userId)).getCompanyid());
        queryWrapper.and(wrapper -> {
            wrapper.eq(CompanyCase::getId, comCaseId);
        });
        if (Objects.isNull(companyCaseMapper.selectOne(queryWrapper))){
            return new ResponseVo(AppHttpCodeEnum.FAIL.getCode(), "当前公司未添加案列分类标题");
        }else{
            queryWrapper=new LambdaQueryWrapper<>();
            queryWrapper.eq(CompanyCase::getCompanyId, sysUserMapper.selectById(Long.parseLong(userId)).getCompanyid());
            queryWrapper.and(wrapper -> {
                wrapper.eq(CompanyCase::getId, comCaseId);
                wrapper.eq(CompanyCase::getDelFlag, 1);
            });
            if (Objects.isNull(companyCaseMapper.selectOne(queryWrapper))){
                //TODO 执行操作
                queryWrapper=new LambdaQueryWrapper<>();
                queryWrapper.eq(CompanyCase::getCompanyId, sysUserMapper.selectById(Long.parseLong(userId)).getCompanyid());
                queryWrapper.and(wrapper -> {
                    wrapper.eq(CompanyCase::getId, comCaseId);
                    wrapper.eq(CompanyCase::getDelFlag, 0);
                });
                CompanyCase companyCase = companyCaseMapper.selectOne(queryWrapper);
                companyCase.setDelFlag(0);
                companyCase.setUpdateBy(Long.parseLong(userId));
                companyCase.setUpdateTime(new Date());
                companyCaseMapper.updateById(companyCase);
            }
        }
        return null;
    }
}
