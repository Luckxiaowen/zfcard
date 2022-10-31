package com.zf.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zf.domain.entity.Company;
import com.zf.domain.entity.CompanyInfo;
import com.zf.domain.vo.CompanyInfoVo;
import com.zf.domain.vo.ResponseVo;
import com.zf.enums.AppHttpCodeEnum;
import com.zf.mapper.CompanyInfoMapper;
import com.zf.mapper.CompanyMapper;
import com.zf.mapper.SysUserMapper;
import com.zf.service.CompanyInfoService;
import com.zf.utils.PageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

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
    @Autowired
    private CompanyMapper companyMapper;

    @Transactional
    @Override
    public ResponseVo insert(String userId, CompanyInfo companyInfo) {
        if (companyInfo.getInfoName().trim() == null || "".equals(companyInfo.getInfoName().trim())) {
            return new ResponseVo(AppHttpCodeEnum.FAIL.getCode(), "分公司名称不能为空");
        } else {
            if (companyInfo.getInfoName().trim().length() < 2) {
                return new ResponseVo(AppHttpCodeEnum.FAIL.getCode(), "分公司简介名称不能小于三个字符");
            } else {
                if (companyInfo.getInfoContent().trim() == null || "".equals(companyInfo.getInfoContent().trim())) {
                    return new ResponseVo(AppHttpCodeEnum.FAIL.getCode(), "分公司简介内容不能为空");
                } else {
                    if (companyInfo.getInfoContent().trim().length() < 5) {
                        return new ResponseVo(AppHttpCodeEnum.FAIL.getCode(), "分公司简介内容小于五个字符！");
                    } else {
                        if (Objects.isNull(sysUserMapper.selectById(userId))) {
                            return new ResponseVo(AppHttpCodeEnum.FAIL.getCode(), "当前管理员不属于任何公司添加失败！");
                        } else {
                            LambdaQueryWrapper<CompanyInfo> queryWrapper = new LambdaQueryWrapper<>();
                            queryWrapper.eq(CompanyInfo::getInfoName, companyInfo.getInfoName());
                            queryWrapper.eq(CompanyInfo::getDelFlag,0);
                            if (Objects.isNull(companyInfoMapper.selectOne(queryWrapper))) {
                                companyInfo.setCreateBy(Long.parseLong(userId));
                                companyInfo.setUpdateBy(Long.parseLong(userId));
                                companyInfo.setCreateTime(new Date());
                                companyInfo.setUpdateTime(new Date());
                                companyInfo.setCompanyId(sysUserMapper.selectById(userId).getCompanyid());
                                companyInfoMapper.insert(companyInfo);
                                CompanyInfo companyInfo1 = companyInfoMapper.selectOne(queryWrapper);
                                companyInfo1.setOrders(Math.toIntExact(companyInfo1.getId()));
                                companyInfoMapper.updateById(companyInfo1);
                                return new ResponseVo(AppHttpCodeEnum.SUCCESS.getCode(), "分公司分简介添加成功！");
                            } else {
                                return new ResponseVo(AppHttpCodeEnum.FAIL.getCode(), "当前分公司简介已存在请检查！");
                            }
                        }
                    }
                }
            }
        }
    }

    @Override
    public ResponseVo delete(String userId, Long cominfoid) {
        if (Objects.isNull(companyInfoMapper.selectById(cominfoid))) {
            return new ResponseVo(AppHttpCodeEnum.FAIL.getCode(), "未添加此分公司简介或者已删除当前分公司简介内容！");
        } else {
            if (companyInfoMapper.selectById(cominfoid).getDelFlag() == 1) {
                return new ResponseVo(AppHttpCodeEnum.FAIL.getCode(), "未添加此分公司简介或者已删除当前分公司简介内容！请刷新页面！");
            } else {
                CompanyInfo companyInfo = companyInfoMapper.selectById(cominfoid);
                companyInfo.setUpdateBy(Long.parseLong(userId));
                removeById(companyInfo.getId());
                companyInfo.setUpdateTime(new Date());
                companyInfoMapper.updateById(companyInfo);
                return new ResponseVo(AppHttpCodeEnum.SUCCESS.getCode(), "分公司简介删除成功！");
            }
        }
    }

    @Override
    public ResponseVo modify(String userId, CompanyInfo companyInfo) {
        if ("".equals(userId)||userId==null){
            return new ResponseVo(AppHttpCodeEnum.FAIL.getCode(), "修改失败：传入的用户id为空");
        }else{
            if (Objects.isNull(sysUserMapper.selectById(userId))){
                return new ResponseVo(AppHttpCodeEnum.FAIL.getCode(), "修改失败：不存在此用户");
            }else {
                LambdaQueryWrapper<Company>queryWrapper1=new LambdaQueryWrapper<>();
                queryWrapper1.eq(Company::getId,sysUserMapper.selectById(userId).getCompanyid());
                if (Objects.isNull( companyMapper.selectOne(queryWrapper1))){
                    return new ResponseVo(AppHttpCodeEnum.FAIL.getCode(), "修改失败：当前公司不存在此管理员");
               }else{
                    LambdaQueryWrapper<CompanyInfo>queryWrapper=new LambdaQueryWrapper<>();
                    queryWrapper.eq(CompanyInfo::getCompanyId,sysUserMapper.selectById(userId).getCompanyid());
                    queryWrapper.eq(CompanyInfo::getId,companyInfo.getId());
                    if (Objects.isNull(companyInfoMapper.selectOne(queryWrapper))){
                        return new ResponseVo(AppHttpCodeEnum.FAIL.getCode(), "修改失败：当前公司下不存在此公司简介");
                    }else{
                        if (companyInfo.getInfoName().trim() == null || "".equals(companyInfo.getInfoName().trim())){
                            return new ResponseVo(AppHttpCodeEnum.FAIL.getCode(), "修改失败：分公司名称不能为空");
                        }else{
                            if (companyInfo.getInfoName().trim().length() < 2){
                                return new ResponseVo(AppHttpCodeEnum.FAIL.getCode(), "修改失败：分公司简介名称不能小于三个字符");
                            }else {
                                //TODO 名称已经输入好了 但是数据库中的名称未变，如果简介名称未变动
                                    LambdaQueryWrapper<CompanyInfo>queryWrapper2=new LambdaQueryWrapper<>();
                                    queryWrapper2.eq(CompanyInfo::getInfoName,companyInfo.getInfoName())
                                            .eq(CompanyInfo::getId,companyInfo.getId())
                                            .eq(CompanyInfo::getCompanyId,companyInfo.getCompanyId());
                                if (!Objects.isNull(companyInfoMapper.selectOne(queryWrapper2))){
                                    if (companyInfo.getInfoContent().trim() == null || "".equals(companyInfo.getInfoContent().trim())){
                                        return new ResponseVo(AppHttpCodeEnum.FAIL.getCode(), "修改失败：分公司简介内容不能为空");
                                    }else{
                                        if (companyInfo.getInfoContent().trim().length() < 5) {
                                            return new ResponseVo(AppHttpCodeEnum.FAIL.getCode(), "修改失败：分公司简介内容不能小于5个字符！");
                                        }else{
                                            companyInfo.setDelFlag(0);
                                            companyInfo.setUpdateTime(new Date());
                                            companyInfo.setUpdateBy(Long.parseLong(userId));
                                            int i = companyInfoMapper.updateById(companyInfo);
                                            if (i>0){
                                                return new ResponseVo(AppHttpCodeEnum.SUCCESS.getCode(), "分公司分简介修改成功！");
                                            }else {
                                                return new ResponseVo(AppHttpCodeEnum.SUCCESS.getCode(), "分公司分简介修改失败！");
                                            }
                                        }
                                    }
                                    //TODO 修改名字后
                                }else{
                                    queryWrapper2=new LambdaQueryWrapper<>();
                                    queryWrapper2.eq(CompanyInfo::getInfoName,companyInfo.getInfoName());
                                    queryWrapper2.eq(CompanyInfo::getCompanyId,companyInfo.getCompanyId());
                                    if (!Objects.isNull(companyInfoMapper.selectOne(queryWrapper2))){
                                        return new ResponseVo(AppHttpCodeEnum.FAIL.getCode(), "修改失败：当前分公司简介已存在！");
                                    }else{
                                        companyInfo.setDelFlag(0);
                                        companyInfo.setUpdateTime(new Date());
                                        companyInfo.setUpdateBy(Long.parseLong(userId));
                                        int i = companyInfoMapper.updateById(companyInfo);
                                        if (i>0){
                                            return new ResponseVo(AppHttpCodeEnum.SUCCESS.getCode(), "分公司分简介修改成功！");
                                        }else {
                                            return new ResponseVo(AppHttpCodeEnum.SUCCESS.getCode(), "分公司分简介修改失败！");
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    @Override
    public ResponseVo selectAll(String userId) {
        List<CompanyInfoVo> companyInfoVoList = companyInfoMapper.selectAllByCreateBy(userId);
        return new ResponseVo(AppHttpCodeEnum.SUCCESS.getCode(), AppHttpCodeEnum.SUCCESS.getMsg(), companyInfoVoList);
    }

    @Override
    public ResponseVo selectPage(String userId, Integer pageNum, Integer pageSize) {
        long count = companyInfoMapper.selectAllByCreateBy(userId).stream().count();
        pageNum = (pageNum - 1) * pageSize;
        List<CompanyInfoVo> companyInfoVoList = companyInfoMapper.selectMyPage(Long.parseLong(userId), pageNum, pageSize);
        PageUtils pageUtils = new PageUtils();
        pageUtils.setData(companyInfoVoList);
        pageUtils.setTotal(Integer.parseInt(String.valueOf(count)));
        return ResponseVo.okResult(pageUtils);
    }


    @Override
    public ResponseVo companyOrderByOrders(String subject, String sortStr, Integer companyInfoId) {
        LambdaQueryWrapper<CompanyInfo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(CompanyInfo::getId, companyInfoId)
                .eq(CompanyInfo::getDelFlag, 0);
        if (Objects.isNull(companyInfoMapper.selectOne(queryWrapper)) || Objects.isNull(companyInfoMapper.selectById(companyInfoId))) {
            return new ResponseVo(AppHttpCodeEnum.FAIL.getCode(), "移动失败: 不存在当前分公司简介!");
        } else {
            if (sortStr.trim().equals("down") || sortStr.trim().equals("up")) {
                if (sortStr.equals("down")) {
                    return this.moveDown(companyInfoId);
                } else if (sortStr.equals("up")) {
                    return this.moveUp(companyInfoId);
                }
            } else {
                return new ResponseVo(AppHttpCodeEnum.FAIL.getCode(), "移动失败: 移动命令错误");
            }
            return new ResponseVo(AppHttpCodeEnum.FAIL.getCode(), "移动失败");
        }
    }

    @Override
    public int insertSelective(CompanyInfo companyInfo) {

        Integer orders = companyInfoMapper.selectMaxOrders();
        if (orders == 0) {
            companyInfo.setOrders(0);
        } else {
            companyInfo.setOrders(orders + 1);
        }
        return companyInfoMapper.updateById(companyInfo);
    }


    //todo 上移
    @Override
    public ResponseVo moveUp(Integer companyId) {
        //获取要上移的那条数据的信息
        CompanyInfo companyInfo = companyInfoMapper.selectById(companyId);
        //查询上一条记录
        CompanyInfo companyInfoPrev = companyInfoMapper.moveUp(companyInfo.getOrders());
        //最上面的记录不能上移
        if (companyInfoPrev == null) {
            return new ResponseVo(AppHttpCodeEnum.SUCCESS.getCode(), "已是第一条");
        } else {
            //交换两条记录的orders值
            Integer temp = companyInfo.getOrders();
            companyInfo.setOrders(companyInfoPrev.getOrders());
            companyInfoPrev.setOrders(temp);
            companyInfoMapper.updateById(companyInfo);
            companyInfoMapper.updateById(companyInfoPrev);
            return new ResponseVo(AppHttpCodeEnum.SUCCESS.getCode(), "上移成功");
        }
    }

    //todo 下移
    @Override
    public ResponseVo moveDown(Integer companyId) {

        //获取要上移的那条数据的信息
        CompanyInfo companyInfo = companyInfoMapper.selectById(companyId);
        //查询上一条记录
        CompanyInfo companyInfoNext = companyInfoMapper.moveDown(companyInfo.getOrders());
        //最上面的记录不能上移
        if (companyInfoNext == null) {
            return new ResponseVo(AppHttpCodeEnum.SUCCESS.getCode(), "已是最后一条");
        } else {
            //交换两条记录的orders值
            Integer temp = companyInfo.getOrders();
            companyInfo.setOrders(companyInfoNext.getOrders());
            companyInfoNext.setOrders(temp);
            companyInfoMapper.updateById(companyInfo);
            companyInfoMapper.updateById(companyInfoNext);
            return new ResponseVo(AppHttpCodeEnum.SUCCESS.getCode(), "下移成功");
        }
    }

}
