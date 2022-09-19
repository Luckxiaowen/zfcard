package com.zf.controller.admin;

import com.zf.domain.entity.SysRole;
import com.zf.domain.vo.ResponseVo;
import com.zf.service.SysRoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @Author wenqin
 * @Date 2022/9/17 15:36
 */

@Api(value = "权限角色接口管理", tags = "权限角色接口管理")
@RestController
@RequestMapping("/admin")
public class RoleController {
    @Resource
    private SysRoleService roleService;

    @ApiOperation(value = "新增权限角色")
    @PostMapping("/role")
    @PreAuthorize("hasAnyAuthority('sys:role:add')")
    public ResponseVo addRole(@RequestHeader("token") String token,@RequestBody SysRole role){
        return roleService.addRole(role);
    }

    @ApiOperation(value = "查看所有角色")
    @GetMapping("/role")
    @PreAuthorize("hasAnyAuthority('sys:role:select')")
    public ResponseVo getAllRole(@RequestHeader("token") String token){
        return roleService.getAllRole();
    }

}