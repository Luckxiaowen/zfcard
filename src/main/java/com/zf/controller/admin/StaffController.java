package com.zf.controller.admin;

import com.zf.domain.dto.RoleDto;
import com.zf.domain.dto.StaffDto;
import com.zf.domain.vo.ResponseVo;
import com.zf.service.SysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * @Author wenqin
 * @Date 2022/10/3 15:01
 */
@Api(value = "员工管理接口管理", tags = "PC员工管理接口管理")
@RestController
@RequestMapping("/admin")
public class StaffController {
    @Resource
    private SysUserService userService;

    @ApiOperation(value = "新增公司员工")
    @PostMapping("/staff")
    public ResponseVo addStaff(@RequestHeader String token, @RequestBody @Valid StaffDto staff){
        return userService.addStaff(staff);
    }

    @ApiOperation(value = "更新公司员工")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "token", required = true),
    })
    @PutMapping("/staff")
    public ResponseVo updateStaff(@RequestBody @Validated(RoleDto.Update.class) StaffDto staff){
        return userService.updateStaff(staff);
    }

    @ApiOperation(value = "删除司员工")
    @DeleteMapping("/staff/{id}")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "token", required = true),
    })
    public ResponseVo delStaffById(@PathVariable("id") Integer id){
        return userService.delStaffById(id);
    }
}
