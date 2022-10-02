package com.zf.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.zf.domain.entity.SysUser;
import com.zf.domain.vo.CompanyProfileVo;
import com.zf.domain.vo.ResponseVo;
import com.zf.enums.AppHttpCodeEnum;
import com.zf.exception.SystemException;
import com.zf.mapper.CompanyCaseMapper;
import com.zf.mapper.CompanyProfileVoMapper;
import com.zf.mapper.SysUserMapper;
import com.zf.service.CompanyCaseService;
import com.zf.service.CompanyProfileVoService;
import com.zf.utils.JwtUtil;
import com.zf.utils.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;


@Service
public class CompanyProfileVoServiceImpl extends ServiceImpl<CompanyProfileVoMapper, CompanyProfileVo> implements CompanyProfileVoService {

    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private CompanyProfileVoMapper companyProfileVoMapper;
    //TODO 使用id或者token获取数据
    @Override
    public ResponseVo getcompanyProfile(String id) {
        if (id==null||"".equals(id)){
            return new ResponseVo(AppHttpCodeEnum.FAIL.getCode(), "获取分公司简介失败：用户id输入为空");
        }else{
            Integer userId = getInteger(id);
            if (Objects.isNull(sysUserMapper.selectById(userId))){
                return new ResponseVo(AppHttpCodeEnum.FAIL.getCode(), "获取分公司简介失败：不存在此员工");
            }else{
                Long companyid =sysUserMapper.selectById(userId).getCompanyid();
                if (companyid==null||"".equals(companyid)){
                    return new ResponseVo(AppHttpCodeEnum.FAIL.getCode(), "获取分公司简介失败：当前员工不属于任何公司");
                }else{
                    List<CompanyProfileVo> companyProfileVoList = companyProfileVoMapper.companyProfile(Math.toIntExact(companyid));
                    return ResponseVo.okResult(companyProfileVoList);
                }
            }
        }
    }

    public Integer getInteger(String userId) {
        int id;
        if (Validator.isNumeric(userId)) {
            id = Integer.parseInt(userId);
        } else {
            //TODO 员工token获取
            try {
                String subject = JwtUtil.parseJWT(userId).getSubject();
                id = Integer.parseInt(subject);
            } catch (Exception e) {
                throw new SystemException(AppHttpCodeEnum.PARAMETER_ERROR);
            }
        }
        return id;
    }

}
