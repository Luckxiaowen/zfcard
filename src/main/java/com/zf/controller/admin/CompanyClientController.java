package com.zf.controller.admin;

import com.zf.domain.vo.ResponseVo;
import com.zf.service.CompanyClientService;

import io.swagger.annotations.Api;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api(value = "公司人员管理", tags = "PC公司客户管理")
@RestController
@RequestMapping("/admin")
public class CompanyClientController {
    @Autowired
    private CompanyClientService companyClientService;

    @ApiOperation(value = "根据token获取公司客户信息")
    @GetMapping("/company_client_info")
    public ResponseVo selectByCreateBy(@RequestHeader String token,@RequestParam("pageNum")Integer pageNum,@RequestParam("pageSize")Integer pageSize){
        return companyClientService.selectByCreatBy(token,pageNum,pageSize);
    }


    @ApiOperation(value = "查询公司客户信息")
    @GetMapping("/company_client_like")
    public ResponseVo selectByLike(@RequestHeader String token,@RequestParam("pageNum")Integer pageNum,@RequestParam("pageSize")Integer pageSize,@RequestParam("query")String query){
        return companyClientService.selectByLike(token,pageNum,pageSize,query);
    }



}
