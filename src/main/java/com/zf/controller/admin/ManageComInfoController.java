package com.zf.controller.admin;

import com.zf.domain.entity.CompanyInfo;
import com.zf.domain.vo.ResponseVo;
import com.zf.service.CompanyInfoService;
import com.zf.utils.JwtUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@Api(value = "管理公司个性化简介模块", tags = "PC分公司个性化管理接口")
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

    @ApiOperation(value = "分公司简介分页查询")
    @GetMapping("/page-companyinfo")
    private ResponseVo selectPageCompanyInfo(@RequestHeader("token")String token,@RequestParam Integer pageNum ,@RequestParam Integer pageSize) throws Exception{
        return companyInfoService.selectPage(JwtUtil.parseJWT(token).getSubject(),pageNum,pageSize);
    }

    @ApiOperation(value = "分公司简介移动接口")
    @GetMapping("/updateSort")
    @ApiImplicitParams({
            @ApiImplicitParam(dataType = "string", name = "sortStr", value = "上下移动标识符（up 为上移动; down 为下移动）", required = true),
            @ApiImplicitParam(dataType = "string", name = "companyInfoId", value = "分公司简介id", required = true)
    })
    private ResponseVo companyInfoMove(@RequestHeader("token")String token,@RequestParam(value = "sortStr") String sortStr ,@RequestParam(value ="companyInfoId") Integer companyInfoId) throws Exception{
        return companyInfoService.companyOrderByOrders(JwtUtil.parseJWT(token).getSubject(),sortStr,companyInfoId);
    }
}
