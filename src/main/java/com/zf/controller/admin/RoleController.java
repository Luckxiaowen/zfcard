package com.zf.controller.admin;

import com.zf.domain.dto.RoleDto;
import com.zf.domain.vo.ResponseVo;
import com.zf.service.SysRoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * @Author wenqin
 * @Date 2022/9/17 15:36
 */

@Api(value = "权限角色接口管理", tags = "PC权限角色接口管理")
@RestController
@RequestMapping("/admin")
public class RoleController {
    @Resource
    private SysRoleService roleService;

    @ApiOperation(value = "新增权限角色")
    @PostMapping("/role")
    public ResponseVo<?> addRole(@RequestHeader("token") String token,@Valid @RequestBody RoleDto roleDto){
        return roleService.addRole(roleDto);
    }

    @ApiOperation(value = "根据token获取公司所有角色")
    @GetMapping("/role")
    public ResponseVo getAllRole(@RequestHeader("token") String token){
        return roleService.getAllRole();
    }

    @ApiOperation(value = "更新角色")
    @PutMapping("/role")
    public ResponseVo updateRole(@RequestHeader("token") String token,@Validated @RequestBody RoleDto roleDto){
        return roleService.updateRole(roleDto);
    }


    @ApiOperation(value = "根据角色ID删除角色")
    @DeleteMapping("/role/{id}")
    public ResponseVo updateRole(@RequestHeader("token") String token,@PathVariable Long id){
        return roleService.delRole(id);
    }

}
