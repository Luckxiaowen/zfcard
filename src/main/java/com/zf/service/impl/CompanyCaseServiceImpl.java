package com.zf.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zf.domain.entity.CompanyCase;
import com.zf.domain.entity.SysUser;
import com.zf.domain.vo.ResponseVo;
import com.zf.enums.AppHttpCodeEnum;
import com.zf.domain.entity.CompanyInfo;
import com.zf.domain.entity.SysUser;
import com.zf.domain.vo.ResponseVo;
import com.zf.enums.AppHttpCodeEnum;
import com.zf.mapper.CompanyCaseMapper;
import com.zf.mapper.SysUserMapper;
import com.zf.service.CompanyCaseService;
import com.zf.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
                        companyCase.setCreateBy(Long.parseLong(userId));
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
        if (Objects.isNull(companyCaseMapper.selectOne(queryWrapper))) {
            return new ResponseVo(AppHttpCodeEnum.FAIL.getCode(), "当前公司未添加案列分类标题");
        } else {
            queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(CompanyCase::getCompanyId, sysUserMapper.selectById(Long.parseLong(userId)).getCompanyid());
            queryWrapper.and(wrapper -> {
                wrapper.eq(CompanyCase::getId, comCaseId);
                wrapper.eq(CompanyCase::getDelFlag, 1);
            });
            if (Objects.isNull(companyCaseMapper.selectOne(queryWrapper))) {
                //TODO 执行操作
                queryWrapper = new LambdaQueryWrapper<>();
                queryWrapper.eq(CompanyCase::getCompanyId, sysUserMapper.selectById(Long.parseLong(userId)).getCompanyid());
                queryWrapper.and(wrapper -> {
                    wrapper.eq(CompanyCase::getId, comCaseId);
                    wrapper.eq(CompanyCase::getDelFlag, 0);
                });
                CompanyCase companyCase = companyCaseMapper.selectOne(queryWrapper);
                companyCase.setDelFlag(1);
                companyCase.setUpdateBy(Long.parseLong(userId));
                companyCase.setUpdateTime(new Date());
                companyCaseMapper.updateById(companyCase);
                return new ResponseVo(AppHttpCodeEnum.SUCCESS.getCode(), "删除成功");
            } else {
                return new ResponseVo(AppHttpCodeEnum.SUCCESS.getCode(), "当前公司未添加案列分类标题或者已删除，请刷新页面 !");
            }
        }

    }



    @Override
    public ResponseVo getcaseNames(String token) {
        //        获取用户id
        Integer id = null;
        try {
            id = Integer.valueOf(JwtUtil.parseJWT(token).getSubject());
        } catch (Exception e) {
            e.printStackTrace();
        }
//        通过用户id获取用户全部信息
        SysUser user = sysUserMapper.selectById(id);
//        获取用户信息中的公司id
        Long companyid = user.getCompanyid();
//        将用户信息中的公司id与案例名称中的公司id比对查找
        LambdaQueryWrapper<CompanyCase> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(CompanyCase::getCompanyId,companyid);

        List<CompanyCase> companyCases = companyCaseMapper.selectList(wrapper);
//        获取一列数据
//        companyCases.stream().map(CompanyCase::getCaseName).collect(Collectors.toList());
//        获取两列数据

        Map<Long, String> collect = companyCases.stream().collect(Collectors.toMap(CompanyCase::getId, CompanyCase::getCaseName));
        for (Map.Entry<Long, String> map:collect.entrySet()) {
            System.out.println(map.getKey() + "," + map.getValue());
        }
//        System.out.println();
        return ResponseVo.okResult(collect);
    }
    @Override
    public ResponseVo updateCompanyCase(String userId, CompanyCase companyCase) {
        if (Strings.isEmpty(companyCase.getCaseName())) {
            return new ResponseVo(AppHttpCodeEnum.SUCCESS.getCode(), "请输入公司相关信息");
        } else {
            if ("".equals(companyCase.getCaseName()) || companyCase == null) {
                return new ResponseVo(AppHttpCodeEnum.SUCCESS.getCode(), "案列分类名称不能为空");
            } else {
                if (companyCase.getCaseName().trim().length() < 2) {
                    return new ResponseVo(AppHttpCodeEnum.SUCCESS.getCode(), "案列分类名称不能小于两个字符");
                } else {
                    LambdaQueryWrapper<CompanyCase> queryWrapper = new LambdaQueryWrapper<>();
                    queryWrapper.eq(CompanyCase::getCreateBy, Long.parseLong(userId));
                    queryWrapper.and(wrapper -> {
                        wrapper.eq(CompanyCase::getCaseName, companyCase.getCaseName());
                        wrapper.eq(CompanyCase::getDelFlag,0);
                    });
                    if (Objects.isNull(companyCaseMapper.selectOne(queryWrapper))) {
                        companyCase.setUpdateTime(new Date());
                        companyCase.setUpdateBy(Long.parseLong(userId));

                        int i = companyCaseMapper.updateById(companyCase);
                        if (i > 0) {
                            return new ResponseVo(AppHttpCodeEnum.SUCCESS.getCode(), "修改成功");
                        } else {
                            return new ResponseVo(AppHttpCodeEnum.SUCCESS.getCode(), "修改失败");
                        }
                    } else {
                        return new ResponseVo(AppHttpCodeEnum.SUCCESS.getCode(), "当前公司已存在此案列名称");
                    }
                }
            }
        }
    }

    @Override
    public ResponseVo selectAll(String userId) {
        LambdaQueryWrapper<CompanyCase>queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(CompanyCase::getCreateBy,userId);
        queryWrapper.and(wrapper->{wrapper.eq(CompanyCase::getDelFlag,0);});
        return new ResponseVo(AppHttpCodeEnum.SUCCESS.getCode(), AppHttpCodeEnum.SUCCESS.getMsg(),companyCaseMapper.selectList(queryWrapper));
    }
}
