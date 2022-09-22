package com.zf.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.zf.domain.entity.Company;
import com.zf.domain.entity.ExposureTotal;
import com.zf.domain.entity.SysRole;
import com.zf.domain.entity.SysUser;
import com.zf.domain.vo.LoginUser;
import com.zf.domain.vo.ResponseVo;
import com.zf.enums.AppHttpCodeEnum;
import com.zf.mapper.CompanyMapper;
import com.zf.mapper.ExposureTotalMapper;
import com.zf.mapper.SysRoleMapper;
import com.zf.mapper.SysUserMapper;
import com.zf.service.PersonalCardService;
import com.zf.service.SysUserService;
import com.zf.utils.PdUtils;
import io.swagger.annotations.Api;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author pd
 * DateTime: 2022/9/17 13:16
 */
@RestController
@Api(value = "个人名片", tags = "名片模块")
@RequestMapping("/user")
public class PersonalCardController {

  @Autowired
  private SysUserService sysUserService;

  @Autowired
  private PersonalCardService personalCardService;

  @Autowired
  private CompanyMapper companyMapper;

  @Autowired
  private SysRoleMapper sysRoleMapper;

  @Autowired
  private SysUserMapper sysUserMapper;

  @Autowired
  private ExposureTotalMapper exposureTotalMapper;

  PdUtils pdUtils;

  @ApiOperation(value = "名片接口")
  @GetMapping("/personal-card")
  public ResponseVo PCard(@RequestHeader("token") String token){

    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    LoginUser loginUser = (LoginUser) authentication.getPrincipal();
    Integer id = Math.toIntExact(loginUser.getSysUser().getId());
    com.zf.domain.entity.PersonalCard personalCard = personalCardService.personalCardById(id);
    int roleId = personalCard.getRoleId();
    int companyId = personalCard.getCompanyId();

    LambdaQueryWrapper<ExposureTotal> query = new LambdaQueryWrapper<>();
    query.like(ExposureTotal::getCreateBy,id);
    ExposureTotal total = exposureTotalMapper.selectOne(query);

    if (total==null){

      Date date = new Date();
      ExposureTotal exposure = new ExposureTotal(null, id.longValue(), date, date, 0L, 0L, 0L,
        0L, 0L, 0L, 0L, 0L, 0L,0L);

      exposureTotalMapper.insert(exposure);
    }


    LambdaQueryWrapper<SysRole> queryWrapper = new LambdaQueryWrapper<>();
    queryWrapper.like(SysRole::getId,roleId);
    SysRole sysRole = sysRoleMapper.selectOne(queryWrapper);
    String roleName = sysRole.getName();

    LambdaQueryWrapper<Company> companyQueryWrapper = new LambdaQueryWrapper<>();
    companyQueryWrapper.like(Company::getId,companyId);
    Company company = companyMapper.selectOne(companyQueryWrapper);
    String companyName = company.getCompany();
    String address = company.getAddress();

    HashMap<String, Object> map = new HashMap<>();
    String email = personalCard.getEmail();
    String username = personalCard.getUsername();
    BigInteger phoneNumber = personalCard.getPhoneNumber();

    map.put("roleName",roleName);
    map.put("companyName",companyName);
    map.put("email",email);
    map.put("username",username);
    map.put("address",address);
    map.put("phoneNumber",phoneNumber);

    return new ResponseVo(AppHttpCodeEnum.SUCCESS.getCode(),AppHttpCodeEnum.SUCCESS.getMsg(),map);
  }

  @ApiOperation("保存名片")
  @GetMapping("/save-card")
  public ResponseVo saveCard(@RequestHeader("token") String token){

    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    LoginUser loginUser = (LoginUser) authentication.getPrincipal();
    Integer id = Math.toIntExact(loginUser.getSysUser().getId());

    LambdaQueryWrapper<ExposureTotal> queryWrapper = new LambdaQueryWrapper<>();
    queryWrapper.like(ExposureTotal::getCreateBy,id);
    ExposureTotal total = exposureTotalMapper.selectOne(queryWrapper);

    Long dayDownloadNum = total.getDayDownloadNum();
    Long dayDownload = dayDownloadNum+1;

    LambdaUpdateWrapper<ExposureTotal> updateWrapper = new LambdaUpdateWrapper<>();
    updateWrapper.set(ExposureTotal::getDayDownloadNum,dayDownload);
    exposureTotalMapper.update(total,updateWrapper);

    return new ResponseVo(AppHttpCodeEnum.SUCCESS.getCode(), AppHttpCodeEnum.SUCCESS.getMsg());
  }

  @ApiOperation("转发名片")
  @GetMapping("/forward-card")
  public ResponseVo forwardCard(@RequestHeader("token") String token){

    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    LoginUser loginUser = (LoginUser) authentication.getPrincipal();
    Integer id = Math.toIntExact(loginUser.getSysUser().getId());

    LambdaQueryWrapper<ExposureTotal> queryWrapper = new LambdaQueryWrapper<>();
    queryWrapper.like(ExposureTotal::getCreateBy,id);
    ExposureTotal total = exposureTotalMapper.selectOne(queryWrapper);

    Long dayForwardNum = total.getDayForwardNum();
    Long forwardNum = dayForwardNum+1;

    LambdaUpdateWrapper<ExposureTotal> updateWrapper = new LambdaUpdateWrapper<>();
    updateWrapper.set(ExposureTotal::getDayForwardNum,forwardNum);
    exposureTotalMapper.update(total,updateWrapper);

    return new ResponseVo(AppHttpCodeEnum.SUCCESS.getCode(), AppHttpCodeEnum.SUCCESS.getMsg());
  }

  @ApiOperation("保存电话")
  @GetMapping("/save-num")
  public ResponseVo saveNum(@RequestHeader("token") String token){

    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    LoginUser loginUser = (LoginUser) authentication.getPrincipal();
    Integer id = Math.toIntExact(loginUser.getSysUser().getId());

    LambdaQueryWrapper<ExposureTotal> queryWrapper = new LambdaQueryWrapper<>();
    queryWrapper.like(ExposureTotal::getCreateBy,id);
    ExposureTotal total = exposureTotalMapper.selectOne(queryWrapper);

    Long dayAddContact = total.getDayAddContact();
    long addContact = dayAddContact + 1;

    LambdaUpdateWrapper<ExposureTotal> updateWrapper = new LambdaUpdateWrapper<>();
    updateWrapper.set(ExposureTotal::getDayAddContact,addContact);
    exposureTotalMapper.update(total,updateWrapper);

    return ResponseVo.okResult();
  }



}
