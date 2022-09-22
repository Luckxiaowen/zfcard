package com.zf.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zf.domain.entity.CaseContent;
import com.zf.domain.vo.ResponseVo;
import com.zf.enums.AppHttpCodeEnum;
import com.zf.domain.entity.SysUser;
import com.zf.domain.vo.ResponseVo;
import com.zf.enums.AppHttpCodeEnum;
import com.zf.mapper.CaseContentMapper;
import com.zf.mapper.SysUserMapper;
import com.zf.service.CaseContentService;
import com.zf.utils.JwtUtil;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import com.zf.mapper.SysUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * @author Amireux
 * @description 针对表【case_content(公司案列内容表)】的数据库操作Service实现
 * @createDate 2022-09-16 08:47:16
 */
@Service
public class CaseContentServiceImpl extends ServiceImpl<CaseContentMapper, CaseContent> implements CaseContentService {

    @Autowired
    private CaseContentMapper caseContentMapper;
    @Autowired
    private SysUserMapper sysUserMapper;

    @Override
    public ResponseVo addCaseContent(String userId, CaseContent caseContent) {

        if ("".equals(caseContent.getTitle()) || caseContent.getTitle() == null) {
            return new ResponseVo(AppHttpCodeEnum.SUCCESS.getCode(), "标题不能为空");
        } else {
            if (caseContent.getTitle().length() < 2) {
                return new ResponseVo(AppHttpCodeEnum.SUCCESS.getCode(), "标题不能长度不能小于两个字符");
            } else {
                LambdaQueryWrapper<CaseContent> queryWrapper = new LambdaQueryWrapper<>();
                queryWrapper.eq(CaseContent::getTitle, caseContent.getTitle());
                queryWrapper.and(wrapper -> {
                    wrapper.eq(CaseContent::getCreateBy, Long.parseLong(userId));
                });
                System.out.println("caseContentMapper = " + caseContentMapper.selectOne(queryWrapper));
                if (Objects.isNull(caseContentMapper.selectOne(queryWrapper))) {
                    if ("".equals(caseContent.getContent()) || caseContent.getContent() == null) {
                        return new ResponseVo(AppHttpCodeEnum.SUCCESS.getCode(), "文章内容不能为空");
                    } else {
                        if ("".equals(caseContent.getCaseId()) || caseContent.getCaseId() == null) {
                            return new ResponseVo(AppHttpCodeEnum.SUCCESS.getCode(), "文章分类不能为空");
                        } else {
                            //TODO 数据库操作
                            caseContent.setCreateBy(Long.parseLong(userId));
                            caseContent.setUpdateBy(Long.parseLong(userId));
                            caseContent.setCreateTime(new Date());
                            caseContent.setUpdateTime(new Date());
                            caseContent.setDelFlag(0);
                            caseContent.setVisitorNum(0);
                            int insert = caseContentMapper.insert(caseContent);
                            if (insert > 0) {
                                return new ResponseVo(AppHttpCodeEnum.SUCCESS.getCode(), "添加成功");
                            } else {
                                return new ResponseVo(AppHttpCodeEnum.SUCCESS.getCode(), "添加失败");
                            }
                        }
                    }
                } else {
                    return new ResponseVo(AppHttpCodeEnum.SUCCESS.getCode(), "当前公司已有相同文章标题的文章，请修改标题");
                }

            }
        }
    }

    @Override
    public ResponseVo deleteCaseContent(Long userId, Long casecontentid) {
        if (Objects.isNull(caseContentMapper.selectById(casecontentid))){
            return new ResponseVo(AppHttpCodeEnum.SUCCESS.getCode(), "当前未发布此篇文章");
        }else{
            LambdaQueryWrapper<CaseContent> queryWrapper=new LambdaQueryWrapper<>();
            queryWrapper.eq(CaseContent::getId,casecontentid);
            queryWrapper.and(wrapper->{wrapper.eq(CaseContent::getDelFlag,0);});
            if (Objects.isNull(caseContentMapper.selectOne(queryWrapper))){
                return new ResponseVo(AppHttpCodeEnum.SUCCESS.getCode(), "当前未发布此文章或此文章已被删除,请刷新页面!");
            }else{
                CaseContent caseContent = caseContentMapper.selectById(casecontentid);
                caseContent.setUpdateBy(userId);
                caseContent.setUpdateTime(new Date());
                caseContent.setDelFlag(1);
                if (caseContentMapper.updateById(caseContent)>0){
                    return new ResponseVo(AppHttpCodeEnum.SUCCESS.getCode(), "删除成功");
                }else{
                    return new ResponseVo(AppHttpCodeEnum.SUCCESS.getCode(), "删除失败");
                }
            }
        }
    }

    @Override
    public ResponseVo updateCaseContent(long userId, CaseContent caseContent) {

        if ("".equals(caseContent.getTitle()) || caseContent.getTitle() == null) {
            return new ResponseVo(AppHttpCodeEnum.SUCCESS.getCode(), "标题不能为空");
        } else {
            if (caseContent.getTitle().length() < 2) {
                return new ResponseVo(AppHttpCodeEnum.SUCCESS.getCode(), "标题不能长度不能小于两个字符");
            } else {
                LambdaQueryWrapper<CaseContent> queryWrapper = new LambdaQueryWrapper<>();
                queryWrapper.eq(CaseContent::getCreateBy, userId);
                queryWrapper.and(wrapper -> {
                    wrapper.eq(CaseContent::getCreateBy, caseContent.getTitle());
                });
                if (caseContentMapper.selectById(caseContent.getId()).getTitle().equals(caseContent.getTitle())) {
                    return new ResponseVo(AppHttpCodeEnum.SUCCESS.getCode(), "当前公司已有相同文章标题的文章，请修改标题");
                } else {
                    if ("".equals(caseContent.getContent()) || caseContent.getContent() == null) {
                        return new ResponseVo(AppHttpCodeEnum.SUCCESS.getCode(), "文章内容不能为空");
                    } else {
                        if ("".equals(caseContent.getCaseId()) || caseContent.getCaseId() == null) {
                            return new ResponseVo(AppHttpCodeEnum.SUCCESS.getCode(), "文章分类不能为空");
                        } else {
                            //TODO 数据库操作
                            caseContent.setUpdateBy(userId);
                            caseContent.setUpdateTime(new Date());
                            caseContent.setDelFlag(0);
                            int insert = caseContentMapper.updateById(caseContent);
                            if (insert > 0) {
                                return new ResponseVo(AppHttpCodeEnum.SUCCESS.getCode(), "更新成功");
                            } else {
                                return new ResponseVo(AppHttpCodeEnum.SUCCESS.getCode(), "更新失败");
                            }
                        }
                    }
                }
            }
        }
    }


    @Override
    public ResponseVo getCaseContent(@Param("token") String token) {
        Integer userid = null;
        try {
            userid = Integer.valueOf(JwtUtil.parseJWT(token).getSubject());
        } catch (Exception e) {
            e.printStackTrace();
        }
        SysUser sysUser = sysUserMapper.selectById(userid);
        Integer companyid = Math.toIntExact(sysUser.getCompanyid());
        List<CaseContent> caseContent = caseContentMapper.getCaseContent(companyid);
//        ResponseVo caseContent = caseContentMapper.getCaseContent(companyid);

//        return new ResponseVo(AppHttpCodeEnum.SUCCESS.getCode(),AppHttpCodeEnum.SUCCESS.getMsg(),caseContent);


//        List<CaseContent> caseContent = caseContentMapper.getCaseContent(companyid);
        return ResponseVo.okResult(caseContent);
    }
}
