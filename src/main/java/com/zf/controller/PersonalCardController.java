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

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
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
  @ApiImplicitParams({
          @ApiImplicitParam(dataType = "string", name = "userId", value = "员工id或者员工token", required = true),
  })
  public ResponseVo personalCard(@RequestParam("userId")String userId) throws Exception {
    return personalCardService.selectPersonalCard(userId);
  }

  @ApiOperation("保存名片")
  @PostMapping("/save-card")
  @ApiImplicitParams({
          @ApiImplicitParam(dataType = "string", name = "userId", value = "员工id或者员工token", required = true),
          @ApiImplicitParam(dataType = "string", name = "phoneNum", value = "游客电话号码", required = true),
          @ApiImplicitParam(dataType = "string", name = "name", value = "游客称呼", required = true),

  })
  public ResponseVo saveCard(@RequestParam("userId")String userId,@RequestParam("phoneNum")String phoneNum,@RequestParam("name")String name){
   return personalCardService.savePersonalCard(userId,phoneNum,name);
  }

  @ApiOperation("转发名片")
  @PostMapping("/forward-card")
  @ApiImplicitParams({
          @ApiImplicitParam(dataType = "string", name = "userId", value = "员工id或者员工token", required = true),
          @ApiImplicitParam(dataType = "string", name = "phoneNum", value = "游客电话号码", required = true),
          @ApiImplicitParam(dataType = "string", name = "name", value = "游客称呼", required = true),

  })
  public ResponseVo forwardCard(@RequestParam("userId")String userId,@RequestParam("phoneNum")String phoneNum,@RequestParam("name")String name){

   return personalCardService.forwardPersonalCard(userId,phoneNum,name);
  }

  @ApiOperation("保存电话")
  @PostMapping("/save-num")
  @ApiImplicitParams({
          @ApiImplicitParam(dataType = "string", name = "userId", value = "员工id或者员工token", required = true),
          @ApiImplicitParam(dataType = "string", name = "phoneNum", value = "游客电话号码", required = true),
          @ApiImplicitParam(dataType = "string", name = "name", value = "游客称呼", required = true),
  })
  public ResponseVo saveNum(@RequestParam("userId")String userId,@RequestParam("phoneNum")String phoneNum,@RequestParam("name")String name){

    return personalCardService.savePhoneNum(userId,phoneNum,name);
  }

  @ApiOperation("公开留言展示")
  @GetMapping("/public-message")
  public ResponseVo publicMessage(@RequestHeader("token") String token) throws Exception {
    Integer id = Integer.valueOf(JwtUtil.parseJWT(token).getSubject());
    return ResponseVo.okResult();
  }

}
