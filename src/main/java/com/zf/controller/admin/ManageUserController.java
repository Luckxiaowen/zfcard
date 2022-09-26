package com.zf.controller.admin;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.zf.domain.entity.SysUser;
import com.zf.domain.vo.ResponseVo;
import com.zf.service.SysUserService;
import com.zf.utils.JwtUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

@RestController
@Api(value = "提供超级管理员以及管理员对公司员工信息的增删改查", tags = "员工信息管理接口")
@RequestMapping("/admin")
public class ManageUserController {

    @Autowired
    private SysUserService sysUserService;

    /**
     * 1.Get是查询请求,用来获取资源
     * 2.Post是用来新建资源的,也可以用来更新
     * 3.Put用来更新
     * 4.Delete用来删除*/

    @ApiOperation(value = "添加员工信息接口")
    @PostMapping("/add-user")
    public ResponseVo addUser(@RequestHeader("token")String token, @RequestBody SysUser sysUser) throws Exception {
        return sysUserService.add(sysUser, JwtUtil.parseJWT(token).getSubject());
    }

    @ApiOperation(value = "删除员工信息接口")
    @DeleteMapping("/delete-user/{userid}")
    public ResponseVo deleteUser(@RequestHeader("token")String token,@PathVariable("userid") long userid) throws Exception {
        return sysUserService.delete(userid,JwtUtil.parseJWT(token).getSubject());
    }

    @ApiOperation(value = "修改员工信息接口")
    @PutMapping("/modify-user")
    public ResponseVo update(@RequestHeader("token")String token,@RequestBody SysUser sysUser) throws Exception {
        return sysUserService.modify(sysUser,JwtUtil.parseJWT(token).getSubject());
    }

    @ApiOperation(value = "查询员工信息接口")
    @GetMapping("/list-user")
    public ResponseVo list(@RequestHeader("token")String token){
        return sysUserService.selectAll();
    }


    @ApiOperation(value = "添加员工微信二维码")
    @PostMapping("/upload/file")
    public ResponseVo uploadUserWxCode(@RequestHeader("token")String token,HttpServletRequest request,@RequestParam("file")MultipartFile file){
        return sysUserService.updateUserWxCode(token,request,file);
}
}
