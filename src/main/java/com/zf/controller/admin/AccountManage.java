package com.zf.controller.admin;


import com.zf.domain.vo.PswVo;
import com.zf.domain.vo.ResponseVo;
import com.zf.service.SysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author pd
 * DateTime: 2022/10/19 20:29
 */

@Api(value = "账号管理", tags = "PC账号管理")
@RestController
public class AccountManage {

  @Autowired
  private SysUserService sysUserService;

  @ApiOperation(value = "修改密码")
  @PostMapping("/change_password")
  public ResponseVo selectByCreateBy(@RequestHeader String token, @RequestBody PswVo psw) throws Exception {
    return sysUserService.changePassword(token,psw);
  }

}
