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
    @ApiImplicitParams({
            @ApiImplicitParam(dataType = "string", name = "caseName", value = "案列分类名名称", required = true)
    })
    public ResponseVo addCompanyCase(@RequestHeader("token")String token, @RequestParam("caseName") String caseName) throws Exception {
        return companyCaseService.addCompanyCase(JwtUtil.parseJWT(token).getSubject(),caseName);
    }

    @ApiOperation(value = "删除分公司案列分类接口")
    @DeleteMapping("/delete-companycase/{comcaseid}")
    public ResponseVo deleteCompanyCase(@RequestHeader("token")String token, @PathVariable("comcaseid") Long comcaseid) throws Exception {
        return companyCaseService.deleteCompanyCase(JwtUtil.parseJWT(token).getSubject(),comcaseid);
    }

    @ApiOperation(value = "修改分公司案列分类接口")
    @PutMapping("/update-companycase")
    public ResponseVo updateCompanyCase(@RequestHeader("token")String token,@RequestBody CompanyCase companyCase) throws Exception {
        return companyCaseService.updateCompanyCase(JwtUtil.parseJWT(token).getSubject(),companyCase);
    }


    @ApiOperation(value = "查询分公司案列分类接口")
    @GetMapping("/list-companycase")
    public ResponseVo selectCompanyCase(@RequestHeader("token")String token) throws Exception {
        return companyCaseService.selectAll(JwtUtil.parseJWT(token).getSubject());
    }

    @ApiOperation(value = "公司案列分类分页查询")
    @GetMapping("/page-companycase")
    @ApiImplicitParams({
            @ApiImplicitParam(dataType = "string", name = "pageNum", value = "显示条数", required = true),
            @ApiImplicitParam(dataType = "string", name = "pageSize", value = "页码数", required = true)
    })
    public ResponseVo selectPageCompanyCase(@RequestHeader("token")String token,@RequestParam("pageNum") Integer pageNum ,@RequestParam("pageSize") Integer pageSize) throws Exception {
        return companyCaseService.selectPage(JwtUtil.parseJWT(token).getSubject(),pageNum,pageSize);
    }

    @ApiOperation(value = "公司案列移动接口")
    @GetMapping("/companycase-updateSort")
    @ApiImplicitParams({
            @ApiImplicitParam(dataType = "string", name = "sortStr", value = "上下移动标识符（up 为上移动; down 为下移动）", required = true),
            @ApiImplicitParam(dataType = "string", name = "companyCaseId", value = "分公司简介id", required = true)
    })
    private ResponseVo companyCaseMove(@RequestHeader("token")String token,@RequestParam(value = "sortStr") String sortStr ,@RequestParam(value ="companyCaseId") Integer companyCaseId) throws Exception{
        return companyCaseService.companyCaseOrderByOrders(JwtUtil.parseJWT(token).getSubject(),sortStr,companyCaseId);
    }

}
