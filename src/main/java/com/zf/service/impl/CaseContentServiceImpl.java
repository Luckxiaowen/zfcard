package com.zf.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zf.domain.entity.CaseContent;
import com.zf.domain.vo.CaseContentVo;
import com.zf.domain.vo.ResponseVo;
import com.zf.enums.AppHttpCodeEnum;
import com.zf.domain.entity.SysUser;
import com.zf.domain.vo.ResponseVo;
import com.zf.enums.AppHttpCodeEnum;
import com.zf.exception.SystemException;
import com.zf.mapper.CaseContentMapper;
import com.zf.mapper.SysUserMapper;
import com.zf.service.CaseContentService;
import com.zf.utils.JwtUtil;
import com.zf.utils.PageUtils;
import com.zf.utils.Validator;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

import com.zf.mapper.SysUserMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

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
        if (Objects.isNull(caseContentMapper.selectById(casecontentid))) {
            return new ResponseVo(AppHttpCodeEnum.SUCCESS.getCode(), "当前未发布此篇文章");
        } else {
            LambdaQueryWrapper<CaseContent> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(CaseContent::getId, casecontentid);
            queryWrapper.and(wrapper -> {
                wrapper.eq(CaseContent::getDelFlag, 0);
            });
            if (Objects.isNull(caseContentMapper.selectOne(queryWrapper))) {
                return new ResponseVo(AppHttpCodeEnum.SUCCESS.getCode(), "当前未发布此文章或此文章已被删除,请刷新页面!");
            } else {
                CaseContent caseContent = caseContentMapper.selectById(casecontentid);
                caseContent.setUpdateBy(userId);
                caseContent.setUpdateTime(new Date());
                caseContent.setDelFlag(1);
                if (caseContentMapper.updateById(caseContent) > 0) {
                    return new ResponseVo(AppHttpCodeEnum.SUCCESS.getCode(), "删除成功");
                } else {
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
    public ResponseVo getCaseContent(String id) {
        if (id == null || "".equals(id)) {
            return new ResponseVo(AppHttpCodeEnum.FAIL.getCode(), "获取案例内容失败：id输入为空");
        } else {
            Integer userId = getInteger(id);
            if (Objects.isNull(sysUserMapper.selectById(userId))) {
                return new ResponseVo(AppHttpCodeEnum.FAIL.getCode(), "获取案例内容失败：不存在此员工");
            } else {
                Long companyid = sysUserMapper.selectById(userId).getCompanyid();
                if (companyid == null || "".equals(companyid)) {
                    return new ResponseVo(AppHttpCodeEnum.FAIL.getCode(), "获取案例内容失败：当前员工不属于任何公司");
                } else {
                    List<CaseContent> caseContent = caseContentMapper.getCaseContent(Math.toIntExact(companyid));
                    return ResponseVo.okResult(caseContent);
                }
            }
        }
    }

    @Override
    public ResponseVo selectAll(String userId) {
        if (userId == null || "".equals(userId)) {
            return new ResponseVo(AppHttpCodeEnum.FAIL.getCode(), "查询案列失败：id输入为空");
        } else {
            if (Objects.isNull(sysUserMapper.selectById(userId))) {
                return new ResponseVo(AppHttpCodeEnum.FAIL.getCode(), "查询案列失败：不存在此员工");
            } else {
                List<CaseContentVo> contentVoList = caseContentMapper.selectByCreateBy(userId);
                return new ResponseVo(AppHttpCodeEnum.SUCCESS.getCode(), "查询案列成功", contentVoList);
            }
        }
    }

    @Override
    public ResponseVo SelectPage(String userId, Integer pageNum, Integer pageSize) {
        LambdaQueryWrapper<CaseContent> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(CaseContent::getCreateBy, userId)
                .eq(CaseContent::getDelFlag, 0);
        long count = caseContentMapper.selectList(queryWrapper).stream().count();
        pageNum = (pageNum - 1) * pageSize;
        List<CaseContentVo> caseContentList = caseContentMapper.selectMyPage(userId, pageNum, pageSize);
        PageUtils pageUtils = new PageUtils();
        pageUtils.setTotal((int) count);
        pageUtils.setData(caseContentList);
        return new ResponseVo(AppHttpCodeEnum.SUCCESS.getCode(), "查询成功", pageUtils);
    }

    //TODO 已修改
    @Override
    public ResponseVo addCaseContentVisitorNumByWu(String cid) {
        if (cid == null || "".equals(cid)) {
            return new ResponseVo(AppHttpCodeEnum.FAIL.getCode(), "更新案列浏览量失败：用户id或者案列类容Id输入为空");
        } else {
            if (!Validator.isNumeric(cid)) {
                return new ResponseVo(AppHttpCodeEnum.FAIL.getCode(), "更新案列浏览量失败：参数异常输入的不是纯数字");
            } else {
                if (Objects.isNull(caseContentMapper.selectById(cid))) {
                    return new ResponseVo(AppHttpCodeEnum.FAIL.getCode(), "更新案列浏览量失败：当前案列不存在");
                } else {
                    CaseContent caseContent = caseContentMapper.selectById(cid);
                    caseContent.setVisitorNum(caseContent.getVisitorNum() + 1);
                    int i = caseContentMapper.updateById(caseContent);
                    if (i > 0) {
                        return new ResponseVo(AppHttpCodeEnum.SUCCESS.getCode(), "更新案列浏览量成功");
                    } else {
                        return new ResponseVo(AppHttpCodeEnum.FAIL.getCode(), "更新案列浏览量失败：未知错误");
                    }
                }
            }
        }
    }

    @Override
    public ResponseVo selectByConditions(String userId, String numOrStr, String caseType) {

        List<CaseContentVo> contentVoList = new ArrayList<>();
        if (userId == null || "".equals(userId)) {
            return new ResponseVo(AppHttpCodeEnum.FAIL.getCode(), "条件查询失败：用户ID输入为空");
        } else {
            if ("".equals(numOrStr)) {
                numOrStr = null;
            }
            if ("".equals(caseType)) {
                caseType = null;
            }
            if (numOrStr==null){
                contentVoList = caseContentMapper.selectByConditionsWithCid(userId, numOrStr, caseType);
            }else{
                if (Validator.isNumeric(numOrStr)) {
                    contentVoList = caseContentMapper.selectByConditionsWithCid(userId, numOrStr, caseType);
                } else {
                    contentVoList = caseContentMapper.selectByConditionsWithCName(userId, numOrStr, caseType);
                }
            }



          /*
            contentVoList = caseContentMapper.selectByConditionsWithCid(userId, param, caseType);
            contentVoList = caseContentMapper.selectByConditionsWithCName(userId, param, caseType);*/
        }
        return new ResponseVo(AppHttpCodeEnum.SUCCESS.getCode(), "条件查询成功", contentVoList);
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

    public Object getParam(String param) {
        if ("".equals(param)) {
            return null;
        } else {
            int id;
            if (Validator.isNumeric(param)) {
                id = Integer.parseInt(param);
                return id;
            } else {
                return param;
            }
        }
    }
}
