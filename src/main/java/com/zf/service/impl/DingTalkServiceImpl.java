package com.zf.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.dingtalk.api.response.OapiV2DepartmentListsubResponse;
import com.zf.domain.dto.DingTalkDto;
import com.zf.domain.entity.Company;
import com.zf.domain.entity.CompanyDing;
import com.zf.domain.entity.CompanyFrame;
import com.zf.domain.entity.SysUser;
import com.zf.domain.vo.ResponseVo;
import com.zf.enums.AppHttpCodeEnum;
import com.zf.mapper.CompanyDingMapper;
import com.zf.mapper.CompanyMapper;
import com.zf.mapper.SysUserMapper;
import com.zf.service.DingTalkService;
import com.zf.service.SysUserService;
import com.zf.utils.dingtalkutil.DingTalkUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

@Service
public class DingTalkServiceImpl implements DingTalkService {

    @Resource
    private SysUserMapper sysUserMapper;

    @Resource
    private CompanyMapper companyMapper;

    @Resource
    private CompanyDingMapper companyDingMapper;


    @Override
    public ResponseVo<?> getDep() {
        List<String>stringList=new ArrayList<>();
        List<Long>idList=new ArrayList<>();
        HashMap<String,Object>depMap=new HashMap<>();
        DingTalkUtils dingTalkUtils=new DingTalkUtils();
        OapiV2DepartmentListsubResponse department = dingTalkUtils.getDepartment();
        List<OapiV2DepartmentListsubResponse.DeptBaseResponse> result = department.getResult();
        if (result.size()==0){
            return new ResponseVo<>(AppHttpCodeEnum.FAIL.getCode(), "同步失败: 当前组织架构为空");
        }else {
            for (OapiV2DepartmentListsubResponse.DeptBaseResponse deptBaseResponse : result) {
                stringList.add(deptBaseResponse.getName());
                idList.add(deptBaseResponse.getDeptId());
            }
            depMap.put("depName",stringList);
            depMap.put("depId",idList);
        }
        return new ResponseVo<>(AppHttpCodeEnum.SUCCESS.getCode(), "操作成功",depMap);
    }

    @Override
    public ResponseVo<?> getAssessToken(DingTalkDto dingTalkDto) throws Exception {

        return null;
    }


    @Override
    public ResponseVo<?> bindDingDing(String userId, DingTalkDto dingTalkDto) throws Exception {
        if (userId==null||"".equals(userId)){
            return new ResponseVo<>(AppHttpCodeEnum.FAIL.getCode(), "绑定失败：用户id为空");
        }else {
            LambdaQueryWrapper<SysUser>queryWrapper=new LambdaQueryWrapper<>();
            queryWrapper.eq(SysUser::getDelFlag,0)
                    .eq(SysUser::getId,userId);
            SysUser sysUser = sysUserMapper.selectOne(queryWrapper);
            if (Objects.isNull(sysUser)){
                return new ResponseVo<>(AppHttpCodeEnum.FAIL.getCode(), "绑定失败：用户为空");
            }else {
                Long companyid = sysUser.getCompanyid();
                if (companyid==null||"".equals(companyid)){
                    return new ResponseVo<>(AppHttpCodeEnum.FAIL.getCode(), "绑定失败：当前用户未加入公司");
                }else {
                    Company company = companyMapper.selectById(companyid);
                    if (Objects.isNull(company)){
                        return new ResponseVo<>(AppHttpCodeEnum.FAIL.getCode(), "绑定失败：当前公司不存在请检查");
                    }else {
                        LambdaQueryWrapper<CompanyDing>queryWrapper1=new LambdaQueryWrapper<>();
                        queryWrapper1.eq(CompanyDing::getCompanyId,companyid);
                        CompanyDing companyDing = companyDingMapper.selectOne(queryWrapper1);
                        DingTalkUtils dingTalkUtils=new DingTalkUtils();
                        Map<String, Object> assessToken = dingTalkUtils.getAssessToken(dingTalkDto);
                        return new ResponseVo<>(AppHttpCodeEnum.SUCCESS.getCode(), "操作成功",assessToken);
                    }
                }
            }
        }
    }
}
