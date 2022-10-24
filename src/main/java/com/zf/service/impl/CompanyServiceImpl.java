package com.zf.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zf.domain.entity.Company;
import com.zf.domain.vo.ResponseVo;
import com.zf.enums.AppHttpCodeEnum;
import com.zf.exception.SystemException;
import com.zf.mapper.CompanyImgMapper;
import com.zf.mapper.CompanyMapper;
import com.zf.mapper.SysRoleMapper;
import com.zf.mapper.SysUserMapper;
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


    @Override
    public ResponseVo insert(Company company,String userId) {
        if ("".equals(company.getCompany()) || company.getCompany() == null) {
            return new ResponseVo(AppHttpCodeEnum.FAIL.getCode(), "公司名称不能为空");
        } else {
            if (company.getCompany().length() < 3) {
                return new ResponseVo(AppHttpCodeEnum.FAIL.getCode(), "公司名称长度不能小于三个字符");
            } else {
                if (companyMapper.selectList(null).stream().anyMatch(company1 -> company1.getCompany().equals(company.getCompany()))) {
                    return new ResponseVo(AppHttpCodeEnum.FAIL.getCode(), "当前公司名字重复请核实公司");
                } else {
                    if ("".equals(company.getAddress()) || company.getAddress() == null) {
                        return new ResponseVo(AppHttpCodeEnum.FAIL.getCode(), "公司地址不能为空");
                    } else {
                        if (company.getAddress().length() < 6) {
                            return new ResponseVo(AppHttpCodeEnum.FAIL.getCode(), "公司地址小于6个字符");
                        } else {
                            if (company.getTel().length() < 11) {
                                return new ResponseVo(AppHttpCodeEnum.FAIL.getCode(), "手机号码长度有误");
                            } else {
                                if (Validator.isMobile(company.getTel())) {
                                    company.setCreateTime(new Date());
                                    company.setUpdateTime(new Date());
                                    company.setCreateBy(Long.parseLong(userId));
                                    //TODO 插入数据库
                                    if (companyMapper.insert(company) > 0) {
                                        return new ResponseVo(AppHttpCodeEnum.SUCCESS.getCode(), AppHttpCodeEnum.SUCCESS.getMsg(), null);
                                    } else {
                                        return new ResponseVo(AppHttpCodeEnum.FAIL.getCode(), "公司添加失败");
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
                company.setUpdateBy(Long.parseLong(updateId));
                company.setUpdateTime(new Date());
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

        if ("".equals(company.getCompany()) || company.getCompany() == null) {
            return new ResponseVo(AppHttpCodeEnum.FAIL.getCode(), "公司名称不能为空");
        } else {
            if (company.getCompany().length() < 3) {
                return new ResponseVo(AppHttpCodeEnum.FAIL.getCode(), "公司名称长度不能小于三个字符");
            } else {

                if (companyMapper.selectList(null).stream().filter(company1 -> company1.getCompany().equals(company.getCompany())).count() > 0) {
                    return new ResponseVo(AppHttpCodeEnum.FAIL.getCode(), "当前公司名字重复请核实公司");
                } else {
                    if ("".equals(company.getAddress()) || company.getAddress() == null) {
                        return new ResponseVo(AppHttpCodeEnum.FAIL.getCode(), "公司地址不能为空");
                    } else {
                        if (company.getAddress().length() < 6) {
                            return new ResponseVo(AppHttpCodeEnum.FAIL.getCode(), "公司地址小于6个字符");
                        } else {
                            if (company.getTel().length() < 11) {
                                return new ResponseVo(AppHttpCodeEnum.FAIL.getCode(), "手机号码长度有误");
                            } else {
                                if (Validator.isMobile(company.getTel())) {
                                    company.setUpdateTime(new Date());
                                    company.setUpdateBy(Long.parseLong(updateId));
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
    }

    @Override
    public ResponseVo searchCompany(String conditions) {
        System.out.println("conditions = " + conditions);
        return null;
    }
}
