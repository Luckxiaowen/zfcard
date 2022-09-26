package com.zf.controller.admin;

import com.zf.domain.entity.CompanyFrame;
import com.zf.domain.vo.ResponseVo;
import com.zf.service.CompanyFrameService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * @Author wenqin
 * @Date 2022/9/21 10:38
 */
@Api(value = "公司架构接口管理", tags = "PC公司架构接口管理")
@RestController
@RequestMapping("/admin")
public class CompanyFrameController {
    @Resource
    private CompanyFrameService companyFrameService;

    @ApiOperation(value = "根据token获取公司架构")
    @GetMapping("/company-frame")
    public ResponseVo getCompanyFramework(@RequestHeader String token){
        return companyFrameService.getCompanyFramework(token);
    }

    @ApiOperation(value = "新增公司部门")
    @PostMapping("/company-frame")
    public ResponseVo addCompanyFramework(@RequestHeader String token, @Valid @RequestBody CompanyFrame companyFrame){
        return companyFrameService.addCompanyRole(companyFrame);
    }

    @ApiOperation(value = "修改公司部门信息")
    @PutMapping("/company-frame")
    public ResponseVo updateCompanyFramework(@RequestHeader String token, @Valid @RequestBody CompanyFrame companyFrame){
        return companyFrameService.updateCompanyRole(companyFrame);
    }

    @ApiOperation(value = "删除公司部门")
    @DeleteMapping("/company-frame/{id}")
    public ResponseVo<?> delCompanyFramework(@RequestHeader String token,@PathVariable Long id){
        return companyFrameService.delCompanyFrameworkById(id);
    }



}
