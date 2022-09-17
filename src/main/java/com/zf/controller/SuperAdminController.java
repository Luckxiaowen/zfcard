package com.zf.controller;

import com.zf.domain.entity.Company;
import com.zf.domain.entity.SysUser;
import com.zf.domain.vo.ResponseVo;
import com.zf.enums.AppHttpCodeEnum;
import com.zf.service.CompanyService;
import com.zf.service.LoginService;
import com.zf.service.SysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@Api(value = "提供超级管理员对公司的增删改查", tags = "公司管理")
@RequestMapping("/admin")
public class SuperAdminController {

    /**
     * 1.Get是查询请求,用来获取资源
     * 2.Post是用来新建资源的,也可以用来更新
     * 3.Put用来更新
     * 4.Delete用来删除*/

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private CompanyService companyService;


    @ApiOperation(value = "增加公司接口")
    @PostMapping("/add-company")
    public ResponseVo add(@RequestHeader("token") String token, @RequestBody Company company ){
        return companyService.insert(company);
    }

    @DeleteMapping("/delete-company/{companyid}")
    public ResponseVo delete(@RequestHeader("token") String token,@PathVariable("companyid") Long companyid){
        return companyService.delete(companyid);
    }


    @ApiOperation(value = "公司列表查询接口")
    @GetMapping("/list-company")
    public ResponseVo list(@RequestHeader("token")String token){
       return new ResponseVo(AppHttpCodeEnum.SUCCESS.getCode(), AppHttpCodeEnum.SUCCESS.getMsg(),companyService.list());
    }






}
