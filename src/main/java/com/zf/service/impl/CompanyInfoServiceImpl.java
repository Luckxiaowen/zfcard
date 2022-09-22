package com.zf.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zf.domain.entity.CompanyInfo;
import com.zf.domain.vo.ResponseVo;
import com.zf.enums.AppHttpCodeEnum;
import com.zf.mapper.CompanyInfoMapper;
import com.zf.mapper.SysUserMapper;
import com.zf.service.CompanyInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Objects;

/**
* @author Amireux
* @description 针对表【company_info(公司简介)】的数据库操作Service实现
* @createDate 2022-09-16 08:47:16
*/
@Service
public class CompanyInfoServiceImpl extends ServiceImpl<CompanyInfoMapper, CompanyInfo> implements CompanyInfoService {

    @Autowired
    private CompanyInfoMapper companyInfoMapper;
    @Autowired
    private SysUserMapper sysUserMapper;

    @Override
    public ResponseVo insert(String userId, CompanyInfo companyInfo) {
        if (companyInfo.getInfoName().trim()==null||"".equals(companyInfo.getInfoName().trim())){
            return new ResponseVo(AppHttpCodeEnum.FAIL.getCode(),"分公司名称不能为空");
        }else{
            if (companyInfo.getInfoName().trim().length()<2 ){
                return new ResponseVo(AppHttpCodeEnum.FAIL.getCode(),"分公司简介名称不能小于三个字符");
            }else{
                if (companyInfo.getInfoContent().trim()==null||"".equals(companyInfo.getInfoContent().trim())){
                    return new ResponseVo(AppHttpCodeEnum.FAIL.getCode(),"分公司简介内容不能为空");
                }else{
                    if (companyInfo.getInfoContent().trim().length()<5){
                        return new ResponseVo(AppHttpCodeEnum.FAIL.getCode(),"分公司简介内容小于五个字符！");
                    }else{
                       if (Objects.isNull( sysUserMapper.selectById(userId))){
                           return new ResponseVo(AppHttpCodeEnum.FAIL.getCode(),"当前管理员不属于任何公司添加失败！");
                       }else{
                           companyInfo.setCreateBy(Long.parseLong(userId));
                           companyInfo.setUpdateBy(Long.parseLong(userId));
                           companyInfo.setCreateTime(new Date());
                           companyInfo.setUpdateTime(new Date());
                           companyInfo.setCompanyId(sysUserMapper.selectById(userId).getCompanyid());
                           companyInfoMapper.insert(companyInfo);
                           return new ResponseVo(AppHttpCodeEnum.SUCCESS.getCode(),"分公司分简介添加成功！");
                       }
                    }
                }
            }
        }
    }

    @Override
    public ResponseVo delete(String userId, Long cominfoid) {
        if (Objects.isNull(companyInfoMapper.selectById(cominfoid))){
            return new ResponseVo(AppHttpCodeEnum.FAIL.getCode(),"未添加此分公司简介或者已删除当前分公司简介内容！");
        }else{
            if (companyInfoMapper.selectById(cominfoid).getDelFlag()==1){
                return new ResponseVo(AppHttpCodeEnum.FAIL.getCode(),"未添加此分公司简介或者已删除当前分公司简介内容！请刷新页面！");
            }else{
                CompanyInfo companyInfo = companyInfoMapper.selectById(cominfoid);
                companyInfo.setUpdateBy(Long.parseLong(userId));
                companyInfo.setDelFlag(1);
                companyInfo.setUpdateTime(new Date());
                companyInfoMapper.updateById(companyInfo);
                return new ResponseVo(AppHttpCodeEnum.SUCCESS.getCode(),"分公司简介删除成功！");
            }
        }
    }

    @Override
    public ResponseVo modify(String userId, CompanyInfo companyInfo) {

        if (companyInfo.getInfoName().trim()==null||"".equals(companyInfo.getInfoName().trim())){
            return new ResponseVo(AppHttpCodeEnum.FAIL.getCode(),"分公司名称不能为空");
        }else{
            if (companyInfo.getInfoName().trim().length()<2 ){
                return new ResponseVo(AppHttpCodeEnum.FAIL.getCode(),"分公司简介名称不能小于三个字符");
            }else{
                if (companyInfo.getInfoContent().trim()==null||"".equals(companyInfo.getInfoContent().trim())){
                    return new ResponseVo(AppHttpCodeEnum.FAIL.getCode(),"分公司简介内容不能为空");
                }else{
                    if (companyInfo.getInfoContent().trim().length()<5){
                        return new ResponseVo(AppHttpCodeEnum.FAIL.getCode(),"分公司简介内容不能小于5个字符！");
                    }else{
                        if (Objects.isNull( sysUserMapper.selectById(userId))){
                            return new ResponseVo(AppHttpCodeEnum.FAIL.getCode(),"当前管理员不属于任何公司添加失败！");
                        }else{
                            companyInfo.setUpdateTime(new Date());
                            companyInfo.setUpdateBy(Long.parseLong(userId));
                            companyInfoMapper.updateById(companyInfo);
                            return new ResponseVo(AppHttpCodeEnum.SUCCESS.getCode(),"分公司分简介修改成功！");
                        }
                    }
                }
            }
        }
    }

    @Override
    public ResponseVo selectAll(String userId) {
        LambdaQueryWrapper<CompanyInfo>queryWrapper=new LambdaQueryWrapper<>();

        queryWrapper.eq(CompanyInfo::getDelFlag,0);
        queryWrapper.and(wrapper->{
            wrapper.eq(CompanyInfo::getCreateBy,Long.parseLong(userId));
        });
        return new ResponseVo(AppHttpCodeEnum.SUCCESS.getCode(),AppHttpCodeEnum.SUCCESS.getMsg(),companyInfoMapper.selectList(queryWrapper));
    }


}
