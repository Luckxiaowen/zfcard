package com.zf.controller.admin;

import com.zf.domain.entity.CompanyCase;
import com.zf.domain.vo.ResponseVo;
import com.zf.service.CompanyCaseService;
import com.zf.utils.JwtUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@Api(value = "管理公司个性化简介模块", tags = "PC分公司案列分类管理接口")
@RequestMapping("/admin")
public class ManageComCaseController {



    @Autowired
    private CompanyCaseService companyCaseService;


    /**
     * 1.Get是查询请求,用来获取资源
     * 2.Post是用来新建资源的,也可以用来更新
     * 3.Put用来更新
     * 4.Delete用来删除*/

    @ApiOperation(value = "添加分公司案列分类接口")
    @PostMapping("/add-companycase")
    public ResponseVo addCompanyCase(@RequestHeader("token")String token, @RequestParam String caseName) throws Exception {
        return companyCaseService.addCompanyCase(JwtUtil.parseJWT(token).getSubject(),caseName);
    }

    @ApiOperation(value = "删除分公司案列分类接口")
    @DeleteMapping("/delete-companycase/{comcaseid}")
    public ResponseVo deleteCompanyCase(@RequestHeader("token")String token, @PathVariable("comcaseid") Long comcaseid) throws Exception {
        return companyCaseService.deleteCompanyCase(JwtUtil.parseJWT(token).getSubject(),comcaseid);
    }

    @ApiOperation(value = "修改分公司案列分类接口")
    @PutMapping("/update-companycase")
    @ApiImplicitParams({
            @ApiImplicitParam(dataType = "string", name = "casename", value = "公司案列名称", required = true),
    })
    public ResponseVo updateCompanyCase(@RequestHeader("token")String token,@RequestBody CompanyCase companyCase) throws Exception {
        return companyCaseService.updateCompanyCase(JwtUtil.parseJWT(token).getSubject(),companyCase);
    }


    @ApiOperation(value = "查询分公司案列分类接口")
    @GetMapping("/list-companycase")
    public ResponseVo selectCompanyCase(@RequestHeader("token")String token) throws Exception {
        return companyCaseService.selectAll(JwtUtil.parseJWT(token).getSubject());
    }


}
