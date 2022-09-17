package com.zf.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zf.domain.entity.Company;
import com.zf.domain.vo.ResponseVo;
import com.zf.enums.AppHttpCodeEnum;
import com.zf.mapper.CompanyMapper;
import com.zf.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
* @author Amireux
* @description 针对表【company(公司表)】的数据库操作Service实现
* @createDate 2022-09-16 08:47:16
*/
@Service
public class CompanyServiceImpl extends ServiceImpl<CompanyMapper, Company>implements CompanyService {

    @Autowired
    private CompanyMapper companyMapper;

    @Override
    public ResponseVo insert(Company company) {
        int insert = companyMapper.insert(company);
        if (insert>0){
            return new ResponseVo(AppHttpCodeEnum.SUCCESS.getCode(), AppHttpCodeEnum.SUCCESS.getMsg(),null);
        }else{
            return new ResponseVo(AppHttpCodeEnum.FAIL.getCode(), AppHttpCodeEnum.FAIL.getMsg(),company);
        }
    }

    @Override
    public ResponseVo delete(Long companyid) {
        Company company = companyMapper.selectById(companyid);
        if (Objects.isNull(company)){
            return new ResponseVo(AppHttpCodeEnum.FAIL.getCode(),AppHttpCodeEnum.FAIL.getMsg(),null);
        }else {
            company.setDelFlag(1);
            Wrapper<Company> wrapper=new UpdateWrapper<>();
            companyMapper.update(company,wrapper);
            return new ResponseVo(AppHttpCodeEnum.SUCCESS.getCode(),AppHttpCodeEnum.SUCCESS.getMsg(),null);
        }


    }
}
