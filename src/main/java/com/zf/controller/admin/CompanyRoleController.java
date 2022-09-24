package com.zf.controller.admin;

import com.zf.domain.entity.CompanyRole;
import com.zf.domain.vo.ResponseVo;
import com.zf.service.CompanyRoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * @Author wenqin
 * @Date 2022/9/21 10:38
 */
@Api(value = "公司角色接口管理", tags = "PC公司角色接口管理")
@RestController
@RequestMapping("/admin")
public class CompanyRoleController {
    @Resource
    private CompanyRoleService companyRoleService;

    @ApiOperation(value = "根据token获取公司角色架构")
    @GetMapping("/company-role")
    public ResponseVo getCompanyFramework(@RequestHeader String token){
        return companyRoleService.getCompanyFramework(token);
    }

    @ApiOperation(value = "新增公司职位")
    @PostMapping("/company-role")
    public ResponseVo addCompanyRole(@RequestHeader String token,@Valid @RequestBody CompanyRole companyRole){
        return companyRoleService.addCompanyRole(companyRole);
    }

    @ApiOperation(value = "修改公司角色信息")
    @PutMapping("/company-role")
    public ResponseVo updateCompanyRole(@RequestHeader String token,@Valid @RequestBody CompanyRole companyRole){
        return companyRoleService.updateCompanyRole(companyRole);
    }


}
