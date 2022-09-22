package com.zf.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.zf.domain.entity.Company;
import com.zf.domain.entity.ExposureTotal;
import com.zf.domain.entity.SysRole;
import com.zf.domain.entity.SysUser;
import com.zf.domain.vo.LoginUser;
import com.zf.domain.vo.PersonalCardVo;
import com.zf.domain.vo.ResponseVo;
import com.zf.enums.AppHttpCodeEnum;
import com.zf.mapper.*;
import com.zf.service.PersonalCardService;
import com.zf.service.SysUserService;
import com.zf.utils.JwtUtil;
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
  private PersonalCardService personalCardService;


  @ApiOperation(value = "名片接口")
  @GetMapping("/personal-card")
  public ResponseVo pCard(@RequestHeader("token") String token){
    return personalCardService.selectPersonalCard(token);
  }

  @ApiOperation("保存名片")
  @PostMapping("/save-card")
  public ResponseVo saveCard(@RequestHeader("token") String token,@RequestParam("phoneNum") Long phoneNum){

   return personalCardService.savePersonalCard(token,phoneNum);
  }

  @ApiOperation("转发名片")
  @PostMapping("/forward-card")
  public ResponseVo forwardCard(@RequestHeader("token") String token,@RequestParam("phoneNum") Long phoneNum){

   return personalCardService.forwardPersonalCard(token,phoneNum);
  }

  @ApiOperation("保存电话")
  @PostMapping("/save-num")
  public ResponseVo saveNum(@RequestHeader("token") String token,@RequestParam("phoneNum") Long phoneNum){
    return personalCardService.savePhoneNum(token,phoneNum);
  }

  @ApiOperation("公开留言展示")
  @GetMapping("/public-message")
  public ResponseVo publicMessage(@RequestHeader("token") String token) throws Exception {

    Integer id = Integer.valueOf(JwtUtil.parseJWT(token).getSubject());

    return ResponseVo.okResult();
  }


}
