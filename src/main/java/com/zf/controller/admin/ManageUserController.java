package com.zf.controller.admin;

import com.zf.domain.dto.AccountDto;
import com.zf.domain.dto.RoleDto;
import com.zf.domain.dto.StaffDto;
import com.zf.domain.vo.ResponseVo;
import com.zf.service.SysUserService;
import com.zf.utils.JwtUtil;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@RestController
@Api(value = "提供超级管理员以及管理员对公司员工信息的增删改查", tags = "PC员工信息管理接口")
@RequestMapping("/admin")
@ApiModel(value = "公司案列内容表",description = "封装接口返回给前端的数据")
public class ManageUserController {

    @Autowired
    private SysUserService sysUserService;

    /**
     * 1.Get是查询请求,用来获取资源
     * 2.Post是用来新建资源的,也可以用来更新
     * 3.Put用来更新
     * 4.Delete用来删除*/


    @ApiOperation(value = "根据员工id获取员工信息")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "token", required = true),
    })
    @GetMapping("/staff/{id}")
    public ResponseVo getStaffById(@PathVariable("id") Integer id){
        return sysUserService.getStaffById(id);
    }


    @ApiOperation(value = "新增公司员工")
    @PostMapping("/staff")
    public ResponseVo addStaff(@RequestHeader String token, @RequestBody @Valid StaffDto staff){
        return sysUserService.addStaff(staff);
    }

    @ApiOperation(value = "更新公司员工")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "token", required = true),
    })
    @PutMapping("/staff")
    public ResponseVo updateStaff(@RequestBody @Validated(RoleDto.Update.class) StaffDto staff){
        return sysUserService.updateStaff(staff);
    }

    @ApiOperation(value = "删除公司司员工")
    @DeleteMapping("/staff/{id}")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "token", required = true),
    })
    public ResponseVo delStaffById(@PathVariable("id") Integer id){
        return sysUserService.delStaffById(id);
    }

    @ApiOperation(value = "查询公司员工")
    @GetMapping("/list-staff")
    public ResponseVo selectAllByCreateBy(@RequestHeader("token")String token) throws Exception {
        return sysUserService.selectAll(JwtUtil.parseJWT(token).getSubject());
    }


    @ApiOperation(value = "分页查询员工接口")
    @GetMapping("/page-staff")
    @ApiImplicitParams({
            @ApiImplicitParam(dataType = "Integer", name = "pageNum", value = "显示条数", required = true),
            @ApiImplicitParam(dataType = "Integer", name = "pageSize", value = "页码数", required = true)
    })
    public ResponseVo PageCaseContent(@RequestHeader("token") String token,@RequestParam("pageNum")Integer pageNum,@RequestParam("pageSize")Integer pageSize) throws Exception {
        return sysUserService.SelectPage(JwtUtil.parseJWT(token).getSubject(),pageNum,pageSize);
    }

    @ApiOperation(value = "添加员工微信二维码")
    @PostMapping("/upload/file")
    public ResponseVo uploadUserWxCode(@RequestHeader("token")String token,HttpServletRequest request,@RequestParam("file")MultipartFile file){
        return sysUserService.updateUserWxCode(token,request,file);
    }

    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "token", required = true),
    })
    @ApiOperation(value = "获取公司管理员账号")
    @GetMapping("/account")
    public ResponseVo getAllAccount(){
        return sysUserService.getAllAccount();
    }

    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "token", required = true),
    })
    @ApiOperation(value = "删除公司管理员账号")
    @DeleteMapping("/account/{id}")
    public ResponseVo delAccount(@PathVariable("id") Integer id){
        return sysUserService.delAccountById(id);
    }

    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "token", required = true),
    })

    @ApiOperation(value = "重置公司管理员账号密码(重置密码为手机号后6位)")
    @PostMapping("/account/reset/{id}")
    public ResponseVo resetAccountPassword(@PathVariable("id") Integer id){
        return sysUserService.resetAccountPassword(id);
    }

    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "token", required = true),
    })
    @ApiOperation(value = "更新公司管理员信息")
    @PutMapping("/account")
    public ResponseVo updateAccount(@RequestBody @Validated AccountDto accountDto){
        return sysUserService.updateAccount(accountDto);
    }


    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "token", required = true),
    })
    @ApiOperation(value = "新增公司管理员信息")
    @PostMapping("/account")
    public ResponseVo addAccount(@RequestBody @Validated AccountDto accountDto){
        return sysUserService.addAccount(accountDto);
    }

    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "token", required = true),
    })
    @ApiOperation(value = "根据id获取公司管理员信息")
    @GetMapping("/account/{id}")
    public ResponseVo getAccountById(@PathVariable("id") Integer id){
        return sysUserService.getAccountById(id);
    }

    @PostMapping("add-listUser")
    @ApiOperation(value = "批量添加员工信息")
    public ResponseVo addUserList(@RequestHeader("token")String token,@RequestBody List<StaffDto>userList) throws Exception {
        return sysUserService.addUserList(JwtUtil.parseJWT(token).getSubject(),userList);
    }



}
