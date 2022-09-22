package com.zf.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.zf.domain.entity.SysUser;
import com.zf.domain.vo.CompanyProfileVo;
import com.zf.domain.vo.ResponseVo;
import com.zf.enums.AppHttpCodeEnum;
import com.zf.mapper.CompanyCaseMapper;
import com.zf.mapper.CompanyProfileVoMapper;
import com.zf.mapper.SysUserMapper;
import com.zf.service.CompanyCaseService;
import com.zf.service.CompanyProfileVoService;
import com.zf.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class CompanyProfileVoServiceImpl extends ServiceImpl<CompanyProfileVoMapper, CompanyProfileVo>
        implements CompanyProfileVoService {

    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private CompanyProfileVoMapper companyProfileVoMapper;

    @Override
    public ResponseVo getcompanyProfile(String token) {
        Integer id = null;
        try {
            id = Integer.valueOf(JwtUtil.parseJWT(token).getSubject());
        } catch (Exception e) {
            e.printStackTrace();
        }

        LambdaQueryWrapper<SysUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(SysUser::getId,id);
        SysUser user = sysUserMapper.selectOne(queryWrapper);
        Integer companyid = Math.toIntExact(user.getCompanyid());

        List<CompanyProfileVo> companyProfileVoList = companyProfileVoMapper.companyProfile(companyid);

        return ResponseVo.okResult(companyProfileVoList);
    }


}
