package com.zf.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zf.domain.entity.CompanyCase;
import com.zf.domain.entity.SysUser;
import com.zf.domain.vo.ResponseVo;
import com.zf.enums.AppHttpCodeEnum;
import com.zf.mapper.CompanyCaseMapper;
import com.zf.mapper.SysUserMapper;
import com.zf.service.CompanyCaseService;
import com.zf.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
* @author Amireux
* @description 针对表【company_case(公司案列分类表)】的数据库操作Service实现
* @createDate 2022-09-16 08:47:16
*/
@Service
public class CompanyCaseServiceImpl extends ServiceImpl<CompanyCaseMapper, CompanyCase>
implements CompanyCaseService {

    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private CompanyCaseMapper companyCaseMapper;

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
}
