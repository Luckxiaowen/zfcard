package com.zf.controller.BusinessCardManagement;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.zf.domain.entity.SysUser;
import com.zf.domain.vo.ResponseVo;
import com.zf.enums.AppHttpCodeEnum;
import com.zf.mapper.SysUserMapper;
import com.zf.service.SysUserService;
import com.zf.utils.JwtUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.UUID;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author pd
 * DateTime: 2022/9/19 15:35
 */

@RestController
@Api(value = "个人信息", tags = "名片管理")
@RequestMapping("/personal-info")
public class PersonalInfo {
  @Autowired
  private SysUserService sysUserService;

  @ApiOperation("进入个人信息")
  @GetMapping("/into-PersonalInfo")
  public ResponseVo intoPersonalInfo(@RequestHeader("token") String token){
    return sysUserService.selectUserInfo(token);
  }

  @ApiOperation("职业照上传")
  @PutMapping("/updatePhoto")
  public ResponseVo updatePhoto (@RequestHeader("token") String token,@RequestPart("photo") MultipartFile photo){
    return sysUserService.updateUserPhoton(token, photo);
  }

  @ApiOperation("个人简介修改")
  @PostMapping("/revise-info")
  public ResponseVo reviseInfo(@RequestHeader("token") String token,@RequestParam("info") String info){
   return sysUserService.updateInfo(token, info);
  }
}
