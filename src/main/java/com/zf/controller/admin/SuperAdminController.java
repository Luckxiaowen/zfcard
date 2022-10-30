package com.zf.controller.admin;

import com.zf.domain.dto.CompanyDto;
import com.zf.domain.entity.Company;
import com.zf.domain.vo.ResponseVo;
import com.zf.enums.AppHttpCodeEnum;
import com.zf.service.CompanyService;
import com.zf.service.CompanyVoService;
import com.zf.utils.JwtUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
@Api(value = "提供超级管理员对公司的增删改查", tags = "PC公司管理")
@RequestMapping("/superadmin")
public class SuperAdminController {

    /**
     * 1.Get是查询请求,用来获取资源
     * 2.Post是用来新建资源的,也可以用来更新
     * 3.Put用来更新
     * 4.Delete用来删除*/


    @Autowired
    private CompanyService companyService;

    @Autowired
    private CompanyVoService companyVoService;

    @ApiOperation(value = "增加公司接口")
    @PostMapping("/add-company")
    public ResponseVo add(@RequestHeader("token") String token,@Valid @RequestBody CompanyDto companyDto ) throws Exception {
        return companyService.insert(companyDto);
    }

    @ApiOperation(value = "删除公司接口")
    @DeleteMapping("/delete-company/{companyid}")
    public ResponseVo delete(@RequestHeader("token") String token,@PathVariable("companyid") Long companyid) throws Exception {

        return companyService.delete(companyid,JwtUtil.parseJWT(token).getSubject());
    }

    @ApiOperation(value = "修改公司接口")
    @PutMapping("/modify-company")
    public ResponseVo modify(@RequestHeader("token") String token,@RequestBody CompanyDto CompanyDto) throws Exception {
        return companyService.modify(CompanyDto);
    }

    @ApiOperation(value = "单个公司查询接口")
    @GetMapping("/search-company")
    @ApiImplicitParams({
            @ApiImplicitParam(dataType = "int", name = "companyId", value = "公司id", required = true),
    })
    public ResponseVo list(@RequestParam("companyId")Integer companyId){

       return companyService.selectOneCompany(companyId);
    }

    @ApiOperation(value = "条件查询公司查询接口")
    @GetMapping("/list-company")
    @ApiImplicitParams({
            @ApiImplicitParam(dataType = "int", name = "conditions", value = "条件：编号，手机号，姓名", required = false),
            @ApiImplicitParam(dataType = "int", name = "status", value = "状态", required = false ),
    })
    public ResponseVo search(@RequestHeader("token")String token,@RequestParam("conditions") String conditions,@RequestParam("status")String status) {
        return companyService.searchCompany(conditions,status);
    }


    @ApiOperation(value = "公司启用停用接口")
    @GetMapping("/switch-company-status")
    public ResponseVo switchCompanyStatus(@RequestHeader("token")String token,@RequestParam("companyId") String companyId) {
        return companyService.switchCompanyStatus(companyId);
    }
}
