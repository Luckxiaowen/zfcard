package com.zf.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zf.domain.dto.CompanyDto;
import com.zf.domain.entity.Company;
import com.zf.domain.vo.ResponseVo;
import com.zf.enums.AppHttpCodeEnum;
import com.zf.exception.SystemException;
import com.zf.mapper.CompanyMapper;
import com.zf.service.CompanyService;
import com.zf.utils.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author Amireux
 * @description 针对表【company(公司表)】的数据库操作Service实现
 * @createDate 2022-09-16 08:47:16
 */
@Service
public class CompanyServiceImpl extends ServiceImpl<CompanyMapper, Company> implements CompanyService {

    @Autowired
    private CompanyMapper companyMapper;

    /**
     *
     * @param companyDto
     * @return
     */
    @Override
    public ResponseVo insert(CompanyDto companyDto) {

        return null;
    }

    @Override
    public ResponseVo delete(Long companyid,String updateId) {
        Company company = companyMapper.selectById(companyid);
        if (Objects.isNull(company)) {
            return new ResponseVo(AppHttpCodeEnum.FAIL.getCode(), "您的访问有误");
        } else {
            if (company.getDelFlag() == 1) {
                throw new SystemException(AppHttpCodeEnum.COMPANY_NOF_FIND);
//                return new ResponseVo(AppHttpCodeEnum.FAIL.getCode(), "未添加此公司或者此公司已删除，请刷新页面");
            } else {
                company.setDelFlag(1);

                if (companyMapper.updateById(company)>0) {
                    return new ResponseVo(AppHttpCodeEnum.SUCCESS.getCode(), "删除成功");
                } else {
                    return new ResponseVo(AppHttpCodeEnum.FAIL.getCode(), "删除失败");
                }
            }
        }
    }

    @Override
    public ResponseVo modify(Company company,String updateId) {

        if ("".equals(company.getCompanyName()) || company.getCompanyName() == null) {
            return new ResponseVo(AppHttpCodeEnum.FAIL.getCode(), "公司名称不能为空");
        } else {
            if (company.getCompanyName().length() < 3) {
                return new ResponseVo(AppHttpCodeEnum.FAIL.getCode(), "公司名称长度不能小于三个字符");
            } else {

                if (companyMapper.selectList(null).stream().filter(company1 -> company1.getCompanyName().equals(company.getCompanyName())).count() > 0) {
                    return new ResponseVo(AppHttpCodeEnum.FAIL.getCode(), "当前公司名字重复请核实公司");
                } else {
                    if ("".equals(company.getCompanyLogo()) || company.getCompanyLogo() == null) {
                        return new ResponseVo(AppHttpCodeEnum.FAIL.getCode(), "公司LOGO不能为空");
                    } else {
                            if (company.getAdminTel().length() < 11) {
                                return new ResponseVo(AppHttpCodeEnum.FAIL.getCode(), "手机号码长度有误");
                            } else {
                                if (Validator.isMobile(company.getAdminTel())) {
                                    //TODO 插入数据库
                                    if (companyMapper.updateById(company) > 0) {
                                        return new ResponseVo(AppHttpCodeEnum.SUCCESS.getCode(), AppHttpCodeEnum.SUCCESS.getMsg());
                                    } else {
                                        return new ResponseVo(AppHttpCodeEnum.FAIL.getCode(), "修改失败");
                                    }
                                } else {
                                    return new ResponseVo(AppHttpCodeEnum.FAIL.getCode(), "手机号错误请重新输入");
                                }
                            }

                    }
                }
            }
        }
    }

    @Override
    public ResponseVo searchCompany(String conditions) {
        System.out.println("conditions = " + conditions);
        return null;
    }
}
