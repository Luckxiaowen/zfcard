package com.zf.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zf.domain.entity.CompanyCase;
import com.zf.domain.entity.SysUser;
import com.zf.domain.vo.CompanyCaseVo;
import com.zf.domain.vo.ResponseVo;
import com.zf.enums.AppHttpCodeEnum;
import com.zf.domain.entity.CompanyInfo;
import com.zf.domain.entity.SysUser;
import com.zf.domain.vo.ResponseVo;
import com.zf.enums.AppHttpCodeEnum;
import com.zf.exception.SystemException;
import com.zf.mapper.CompanyCaseMapper;
import com.zf.mapper.SysUserMapper;
import com.zf.service.CompanyCaseService;
import com.zf.utils.JwtUtil;
import com.zf.utils.PageUtils;
import com.zf.utils.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import java.util.Date;
import java.util.Objects;

/**
 * @author Amireux
 * @description 针对表【company_case(公司案列分类表)】的数据库操作Service实现
 * @createDate 2022-09-16 08:47:16
 */
@Service
public class CompanyCaseServiceImpl extends ServiceImpl<CompanyCaseMapper, CompanyCase> implements CompanyCaseService {

    @Autowired
    private SysUserMapper sysUserMapper;
    @Autowired
    private CompanyCaseMapper companyCaseMapper;

    //TODO 添加已修改
    @Override
    @Transactional
    public ResponseVo addCompanyCase(String userId, String caseName) {
        if ("".equals(caseName) || caseName == null) {
            return new ResponseVo(AppHttpCodeEnum.FAIL.getCode(), "添加公司案列分类失败：公司案列分类名称不能为空");
        } else {
            if (caseName.length() < 2) {
                return new ResponseVo(AppHttpCodeEnum.FAIL.getCode(), "添加公司案列分类失败：公司案列分类名称不能小于两个字符");
            } else {
                if (Objects.isNull(sysUserMapper.selectById(Long.parseLong(userId)).getCompanyid())) {
                    return new ResponseVo(AppHttpCodeEnum.FAIL.getCode(), "添加公司案列分类失败：当前用户未加入任何公司不能创建案列分类");
                } else {
                    LambdaQueryWrapper<CompanyCase> queryWrapper = new LambdaQueryWrapper<>();
                    queryWrapper.eq(CompanyCase::getCompanyId, sysUserMapper.selectById(Long.parseLong(userId)).getCompanyid());
                    queryWrapper.and(wrapper -> {
                        wrapper.eq(CompanyCase::getCaseName, caseName);
                        wrapper.eq(CompanyCase::getDelFlag, 0);
                    });
                    if (Objects.isNull(companyCaseMapper.selectOne(queryWrapper))) {
                        CompanyCase companyCase = new CompanyCase();
                        companyCase.setCompanyId(sysUserMapper.selectById(Long.parseLong(userId)).getCompanyid());
                        companyCase.setCreateBy(Long.parseLong(userId));
                        companyCase.setCreateTime(new Date());
                        companyCase.setUpdateBy(Long.parseLong(userId));
                        companyCase.setUpdateTime(new Date());
                        companyCase.setCaseName(caseName);
                        companyCase.setDelFlag(0);
                        int insert = companyCaseMapper.insert(companyCase);
                        if (insert>0){
                            LambdaQueryWrapper<CompanyCase> queryWrapper1 = new LambdaQueryWrapper<>();
                            queryWrapper1.eq(CompanyCase::getCaseName, companyCase.getCaseName());
                            queryWrapper1.eq(CompanyCase::getDelFlag,0);
                            CompanyCase companyCase1 = companyCaseMapper.selectOne(queryWrapper1);
                            companyCase1.setOrders(Math.toIntExact(companyCase1.getId()));
                            companyCaseMapper.updateById(companyCase1);
                            return new ResponseVo(AppHttpCodeEnum.SUCCESS.getCode(), "案列分类添加成功");
                        }else{
                            return new ResponseVo(AppHttpCodeEnum.FAIL.getCode(), "案列分类添加失败");
                        }
                    } else {
                        return new ResponseVo(AppHttpCodeEnum.FAIL.getCode(), "添加公司案列分类失败：当前公司已存在此案列名称,请修改");
                    }
                }
            }
        }
    }
    //TODO 删除已修改
    @Override
    public ResponseVo deleteCompanyCase(String userId, Long comCaseId) {
        LambdaQueryWrapper<CompanyCase> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(CompanyCase::getCompanyId, sysUserMapper.selectById(Long.parseLong(userId)).getCompanyid());
        queryWrapper.and(wrapper -> {
            wrapper.eq(CompanyCase::getId, comCaseId);
        });
        if (Objects.isNull(companyCaseMapper.selectOne(queryWrapper))) {
            return new ResponseVo(AppHttpCodeEnum.FAIL.getCode(), "当前公司未添加案列分类标题");
        } else {
            queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(CompanyCase::getCompanyId, sysUserMapper.selectById(Long.parseLong(userId)).getCompanyid());
            queryWrapper.and(wrapper -> {
                wrapper.eq(CompanyCase::getId, comCaseId);
                wrapper.eq(CompanyCase::getDelFlag, 1);
            });
            if (Objects.isNull(companyCaseMapper.selectOne(queryWrapper))) {
                //TODO 执行操作
                queryWrapper = new LambdaQueryWrapper<>();
                queryWrapper.eq(CompanyCase::getCompanyId, sysUserMapper.selectById(Long.parseLong(userId)).getCompanyid());
                queryWrapper.and(wrapper -> {
                    wrapper.eq(CompanyCase::getId, comCaseId);
                    wrapper.eq(CompanyCase::getDelFlag, 0);
                });
                CompanyCase companyCase = companyCaseMapper.selectOne(queryWrapper);
                companyCase.setDelFlag(1);
                companyCase.setUpdateBy(Long.parseLong(userId));
                companyCase.setUpdateTime(new Date());
                companyCaseMapper.updateById(companyCase);
                return new ResponseVo(AppHttpCodeEnum.SUCCESS.getCode(), "删除成功");
            } else {
                return new ResponseVo(AppHttpCodeEnum.SUCCESS.getCode(), "当前公司未添加案列分类标题或者已删除，请刷新页面 !");
            }
        }

    }
    //TODO 查询案列分类名称已修改
    @Override
    public ResponseVo getcaseNames(String id) {
        //        获取用户id
        if (id==null||"".equals(id)){
            return new ResponseVo(AppHttpCodeEnum.FAIL.getCode(), "获取案列分类名称失败：id输入为空");
        }else {
            Integer userId = getInteger(id);
            if (Objects.isNull(sysUserMapper.selectById(userId))) {
                return new ResponseVo(AppHttpCodeEnum.FAIL.getCode(), "获取案列分类名称失败：不存在此员工");
            }else {
                Long companyid =sysUserMapper.selectById(userId).getCompanyid();
                if (companyid==null||"".equals(companyid)){
                    return new ResponseVo(AppHttpCodeEnum.FAIL.getCode(), "获取顶部图片失败：当前员工不属于任何公司");
                }else{
                    LambdaQueryWrapper<CompanyCase> wrapper = new LambdaQueryWrapper<>();
                    wrapper.eq(CompanyCase::getCompanyId,companyid);
                    wrapper.eq(CompanyCase::getDelFlag,0);
                    List<CompanyCase> companyCases = companyCaseMapper.selectList(wrapper);
//        获取一列数据
//        companyCases.stream().map(CompanyCase::getCaseName).collect(Collectors.toList());
//        获取两列数据
                    Map<Long, String> collect = companyCases.stream().collect(Collectors.toMap(CompanyCase::getId, CompanyCase::getCaseName));
                    for (Map.Entry<Long, String> map:collect.entrySet()) {
                        System.out.println(map.getKey() + "," + map.getValue());
                    }
                    return ResponseVo.okResult(collect);
                }
            }
        }

    }
    //todo 编辑已修改
    @Override
    public ResponseVo updateCompanyCase(String userId, CompanyCase companyCase) {
        if (Strings.isEmpty(companyCase.getCaseName())) {
            return new ResponseVo(AppHttpCodeEnum.FAIL.getCode(), "案列名称修改失败：请输入公司相关信息");
        } else {
            if ("".equals(companyCase.getCaseName()) || companyCase == null) {
                return new ResponseVo(AppHttpCodeEnum.FAIL.getCode(), "案列名称修改失败：案列分类名称不能为空");
            } else {
                if (companyCase.getCaseName().trim().length() < 2) {
                    return new ResponseVo(AppHttpCodeEnum.FAIL.getCode(), "案列名称修改失败：案列分类名称不能小于两个字符");
                } else {
                    LambdaQueryWrapper<CompanyCase> queryWrapper = new LambdaQueryWrapper<>();
                    queryWrapper.eq(CompanyCase::getCreateBy, Long.parseLong(userId));
                    queryWrapper.and(wrapper -> {
                        wrapper.eq(CompanyCase::getCaseName, companyCase.getCaseName());
                        wrapper.eq(CompanyCase::getDelFlag,0);
                    });
                    if (Objects.isNull(companyCaseMapper.selectOne(queryWrapper))) {
                        companyCase.setUpdateTime(new Date());
                        companyCase.setUpdateBy(Long.parseLong(userId));
                        int i = companyCaseMapper.updateById(companyCase);
                        if (i > 0) {
                            return new ResponseVo(AppHttpCodeEnum.SUCCESS.getCode(), "修改成功");
                        } else {
                            return new ResponseVo(AppHttpCodeEnum.FAIL.getCode(), "修改失败");
                        }
                    } else {
                        return new ResponseVo(AppHttpCodeEnum.FAIL.getCode(), "案列名称修改失败：当前公司已存在此案列名称");
                    }
                }
            }
        }
    }
    //TODO 查询已修改
    @Override
    public ResponseVo selectAll(String userId) {
        if (userId==null||"".equals(userId)){
            return new ResponseVo(AppHttpCodeEnum.FAIL.getCode(), "公司案列分类查询失败：输入的员工账号为空");
        }else{
            if (Objects.isNull(sysUserMapper.selectById(userId))){
                return new ResponseVo(AppHttpCodeEnum.FAIL.getCode(), "公司案列分类查询失败：数据库库中为查询到该员工");
            }else{
                if (sysUserMapper.selectById(userId).getCompanyid()==null||"".equals(sysUserMapper.selectById(userId).getCompanyid())){
                    return new ResponseVo(AppHttpCodeEnum.FAIL.getCode(), "公司案列分类查询失败：该员工不属于任何公司");
                }else {
                    if (sysUserMapper.selectById(userId).getDelFlag()==1){
                        return new ResponseVo(AppHttpCodeEnum.FAIL.getCode(), "公司案列分类查询失败：该员工已被删不能查询");
                    }else{
                        Long companyid = sysUserMapper.selectById(userId).getCompanyid();
                        List<CompanyCaseVo>caseVoList= companyCaseMapper.selectAllByCreateBy(userId,companyid);
                      return new ResponseVo(AppHttpCodeEnum.SUCCESS.getCode(), "查询成功",caseVoList);
                    }
                }
            }
        }
    }
    //TODO 分页查询
    @Override
    public ResponseVo selectPage(String userId, Integer pageNum, Integer pageSize) {

        long count = companyCaseMapper.selectAllByCreateBy(userId,sysUserMapper.selectById(userId).getCompanyid()).stream().count();
        pageNum = (pageNum - 1) * pageSize;
        Long companyid = sysUserMapper.selectById(userId).getCompanyid();
        List<CompanyCaseVo> caseVoList = companyCaseMapper.selectMyPage(Long.valueOf(userId), pageNum, pageSize,companyid);
        PageUtils pageUtils=new PageUtils();
        pageUtils.setTotal((int) count);
        pageUtils.setData(caseVoList);
        return  ResponseVo.okResult(pageUtils);
    }
    //TODO 控制层调用
    @Override
    public ResponseVo companyCaseOrderByOrders(String subject, String sortStr, Integer companyCaseId) {
        LambdaQueryWrapper<CompanyCase>queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(CompanyCase::getId,companyCaseId)
                .eq(CompanyCase::getDelFlag,0);
        if (Objects.isNull(companyCaseMapper.selectOne(queryWrapper))||Objects.isNull(companyCaseMapper.selectById(companyCaseId))){
            return new ResponseVo(AppHttpCodeEnum.FAIL.getCode(), "移动失败: 不存在当前分公司简介!");
        }else {
            if (sortStr.trim().equals("down") || sortStr.trim().equals("up")) {
                if (sortStr.equals("down")) {
                    return this.moveDown(companyCaseId);
                } else if (sortStr.equals("up")) {
                    return this.moveUp(companyCaseId);
                }
            } else {
                return new ResponseVo(AppHttpCodeEnum.FAIL.getCode(), "移动失败: 移动命令错误");
            }
            return new ResponseVo(AppHttpCodeEnum.FAIL.getCode(), "移动失败");
        }
    }
    //TODO 总数
    @Override
    public int insertSelective(CompanyCase companyCase) {
       Integer orders= companyCaseMapper.selectMaxOrders();
       if (orders==0){
           companyCase.setOrders(0);
       }else{
           companyCase.setOrders(orders+1);
       }
        return companyCaseMapper.updateById(companyCase);
    }
    //TODO 上移
    @Override
    public ResponseVo moveUp(Integer companyCaseId) {
        CompanyCase companyCase = companyCaseMapper.selectById(companyCaseId);
        CompanyCase companyCasePrev = companyCaseMapper.moveUp(companyCase.getOrders());
        if (companyCasePrev==null){
            return new ResponseVo(AppHttpCodeEnum.SUCCESS.getCode(), "已是第一条");
        }else{
            Integer temp = companyCase.getOrders();
            companyCase.setOrders(companyCasePrev.getOrders());
            companyCasePrev.setOrders(temp);
            companyCaseMapper.updateById(companyCase);
            companyCaseMapper.updateById(companyCasePrev);
            return new ResponseVo(AppHttpCodeEnum.SUCCESS.getCode(), "上移成功");
        }
    }
    //TODO 下移
    @Override
    public ResponseVo moveDown(Integer companyCaseId) {
        CompanyCase companyCase = companyCaseMapper.selectById(companyCaseId);

        CompanyCase companyCaseNext = companyCaseMapper.moveDown(companyCase.getOrders());
        if (companyCaseNext==null){
            return new ResponseVo(AppHttpCodeEnum.SUCCESS.getCode(), "已是最后一条");
        }else{
            Integer temp = companyCase.getOrders();
            companyCase.setOrders(companyCaseNext.getOrders());
            companyCaseNext.setOrders(temp);
            companyCaseMapper.updateById(companyCase);
            companyCaseMapper.updateById(companyCaseNext);
            return new ResponseVo(AppHttpCodeEnum.SUCCESS.getCode(), "下移成功");
        }

    }
    //TODO 判断是否为token或者为Id
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
