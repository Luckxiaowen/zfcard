package com.zf.controller.admin;

import com.zf.domain.entity.CompanyCase;
import com.zf.domain.vo.ResponseVo;
import com.zf.service.CompanyCaseService;
import com.zf.utils.JwtUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@Api(value = "管理公司个性化简介模块", tags = "分公司案列分类管理接口")
@RequestMapping("/admin")
public class ManageComCaseController {



    @Autowired
    private CompanyCaseService companyCaseService;


    /**
     * 1.Get是查询请求,用来获取资源
     * 2.Post是用来新建资源的,也可以用来更新
     * 3.Put用来更新
     * 4.Delete用来删除*/

    @ApiOperation(value = "添加分公司案列分类")
    @PostMapping("/add-companycase")
    public ResponseVo addCompanyCase(@RequestHeader("token")String token, @RequestParam String caseName) throws Exception {
        return companyCaseService.addCompanyCase(JwtUtil.parseJWT(token).getSubject(),caseName);
    }

    @ApiOperation(value = "删除分公司案列分类")
    @PostMapping("/delete-companycase/{comcaseid}")
    public ResponseVo deleteCompanyCase(@RequestHeader("token")String token, @PathVariable("comcaseid") Long comcaseid) throws Exception {
        return companyCaseService.deleteCompanyCase(JwtUtil.parseJWT(token).getSubject(),comcaseid);
    }

}
