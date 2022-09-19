package com.zf.controller.admin;

import com.zf.domain.entity.CompanyInfo;
import com.zf.domain.vo.ResponseVo;
import com.zf.service.CompanyInfoService;
import com.zf.utils.JwtUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@Api(value = "管理公司个性化简介模块", tags = "分公司个性化管理接口")
@RequestMapping("/admin")
public class ManageComInfoController {
    /**
     * 1.Get是查询请求,用来获取资源
     * 2.Post是用来新建资源的,也可以用来更新
     * 3.Put用来更新
     * 4.Delete用来删除*/

    @Autowired
    private CompanyInfoService companyInfoService;

    @ApiOperation(value = "添加分公司简介接口")
    @PostMapping("/add-companyinfo")
    private ResponseVo addCompanyInfo(@RequestHeader("token")String token,@RequestBody CompanyInfo companyInfo) throws Exception {
        return companyInfoService.insert(JwtUtil.parseJWT(token).getSubject(),companyInfo);

    }

    @ApiOperation(value = "删除分公司简介接口")
    @PutMapping("/delete-companyinfo/{cominfoid}")
    private ResponseVo deleteCompanyInfo(@RequestHeader("token")String token,@PathVariable("cominfoid")Long cominfoid ) throws Exception {
        return companyInfoService.delete(JwtUtil.parseJWT(token).getSubject(), cominfoid);
    }

    @ApiOperation(value = "修改分公司简介接口")
    @PutMapping("/update-companyinfo")
    private ResponseVo updateCompanyInfo(@RequestHeader("token")String token,@RequestBody CompanyInfo companyInfo) throws Exception {
        return companyInfoService.modify(JwtUtil.parseJWT(token).getSubject(), companyInfo);
    }

    @ApiOperation(value = "查询分公司简介接口")
    @GetMapping("/list-companyinfo")
    private ResponseVo selectCompanyInfo(@RequestHeader("token")String token) throws Exception {
        return companyInfoService.selectAll(JwtUtil.parseJWT(token).getSubject());
    }

}
