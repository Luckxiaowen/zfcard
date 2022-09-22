package com.zf.controller;

import com.zf.domain.entity.Company;
import com.zf.domain.vo.ResponseVo;
import com.zf.enums.AppHttpCodeEnum;
import com.zf.service.CompanyService;
import com.zf.utils.JwtUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@Api(value = "提供超级管理员对公司的增删改查", tags = "公司管理")
@RequestMapping("/superadmin")
public class SuperAdminController {

    /**
     * 1.Get是查询请求,用来获取资源
     * 2.Post是用来新建资源的,也可以用来更新
     * 3.Put用来更新
     * 4.Delete用来删除*/


    @Autowired
    private CompanyService companyService;


    @ApiOperation(value = "增加公司接口")
    @PostMapping("/add-company")
    public ResponseVo add(@RequestHeader("token") String token, @Valid @RequestBody Company company ) throws Exception {

        return companyService.insert(company,JwtUtil.parseJWT(token).getSubject());
    }

    @ApiOperation(value = "删除公司接口")
    @DeleteMapping("/delete-company/{companyid}")
    public ResponseVo delete(@RequestHeader("token") String token,@PathVariable("companyid") Long companyid) throws Exception {
        return companyService.delete(companyid,JwtUtil.parseJWT(token).getSubject());
    }

    @ApiOperation(value = "修改公司接口")
    @PutMapping("/modify-company")
    public ResponseVo modify(@RequestHeader("token") String token,@RequestBody Company company) throws Exception {
        return companyService.modify(company,JwtUtil.parseJWT(token).getSubject());
    }

    @ApiOperation(value = "公司列表查询接口")
    @GetMapping("/list-company")
    public ResponseVo list(@RequestHeader("token")String token){
       return new ResponseVo(AppHttpCodeEnum.SUCCESS.getCode(), AppHttpCodeEnum.SUCCESS.getMsg(),companyService.list());
    }

}
