package com.zf.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.zf.domain.entity.Company;
import com.zf.domain.entity.SysRole;
import com.zf.domain.entity.SysUser;
import com.zf.domain.vo.LoginUser;
import com.zf.domain.vo.ResponseVo;
import com.zf.enums.AppHttpCodeEnum;
import com.zf.mapper.CompanyMapper;
import com.zf.mapper.SysRoleMapper;
import com.zf.mapper.SysUserMapper;
import com.zf.service.PersonalCardService;
import com.zf.service.SysUserService;
import io.swagger.annotations.Api;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
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


  @ApiOperation(value = "名片接口")
  @GetMapping("/personal-card")
  public ResponseVo PCard(@RequestHeader("token") String token){

    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    LoginUser loginUser = (LoginUser) authentication.getPrincipal();
    Integer id = Math.toIntExact(loginUser.getSysUser().getId());
    com.zf.domain.entity.PersonalCard personalCard = personalCardService.personalCardById(id);
    int roleId = personalCard.getRoleId();
    int companyId = personalCard.getCompanyId();

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

    System.out.println("123");


    return new ResponseVo(AppHttpCodeEnum.SUCCESS.getCode(),AppHttpCodeEnum.SUCCESS.getMsg(),map);
  }

  @ApiOperation("职业照")
  @GetMapping("/professional-photo")
  public ResponseVo ProPhoto(@RequestHeader("token") String token){

    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    LoginUser loginUser = (LoginUser) authentication.getPrincipal();
    Integer id = Math.toIntExact(loginUser.getSysUser().getId());

    LambdaQueryWrapper<SysUser> queryWrapper = new LambdaQueryWrapper<>();
    queryWrapper.like(SysUser::getId,id);
    SysUser sysUser = sysUserMapper.selectOne(queryWrapper);
    String avatar = sysUser.getAvatar();

    HashMap<String, String> map = new HashMap<>();
    map.put("photo",avatar);


    return new ResponseVo(AppHttpCodeEnum.SUCCESS.getCode(),AppHttpCodeEnum.SUCCESS.getMsg(),map);
  }

}
