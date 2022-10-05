package com.zf.controller.admin;

import com.zf.domain.entity.CaseContent;
import com.zf.domain.vo.ResponseVo;

import com.zf.service.CaseContentService;
import com.zf.utils.JwtUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
@Api(value = "管理公司个性化简介模块", tags = "PC个公司案列详情接口")
public class ManageCaseContentController {

    @Autowired
    private CaseContentService caseContentService;

    /**
     * 1.Get是查询请求,用来获取资源
     * 2.Post新建资源,更新
     * 3.Put 更新
     * 4.Delete 删除*/

    @ApiOperation(value = "添加公司案列内容接口")
    @PostMapping("/add-casecontent")
    public ResponseVo addCaseContent(@RequestHeader("token")String token, @RequestBody CaseContent caseContent) throws Exception {
        return caseContentService.addCaseContent(JwtUtil.parseJWT(token).getSubject(),caseContent);
    }

    @ApiOperation(value = "删除公司案列内容接口")
    @DeleteMapping("/delete-casecontent/{casecontentid}")
    public ResponseVo deleteCaseContent(@RequestHeader("token")String token, @PathVariable("casecontentid") String casecontentid) throws Exception {
        return caseContentService.deleteCaseContent(Long.parseLong(JwtUtil.parseJWT(token).getSubject()),Long.parseLong(casecontentid));
    }

    @ApiOperation(value = "修改公司案列内容接口")
    @PutMapping("/delete-casecontent/")
    public ResponseVo updateCaseContent(@RequestHeader("token")String token,@RequestBody CaseContent caseContent) throws Exception {
        return caseContentService.updateCaseContent(Long.parseLong(JwtUtil.parseJWT(token).getSubject()),caseContent);
    }

    @ApiOperation(value = "查询公司案列内容接口")
    @GetMapping("/list-casecontent")
    public ResponseVo listCaseContent(@RequestHeader("token") String token) throws Exception {
        return caseContentService.selectAll(JwtUtil.parseJWT(token).getSubject());
    }

    @ApiOperation(value = "分页查询公司案列内容接口")
    @GetMapping("/page-casecontent")
    @ApiImplicitParams({
            @ApiImplicitParam(dataType = "Integer", name = "pageNum", value = "显示条数", required = true),
            @ApiImplicitParam(dataType = "Integer", name = "pageSize", value = "页码数", required = true)
    })
    public ResponseVo PageCaseContent(@RequestHeader("token") String token,@RequestParam("pageNum")Integer pageNum,@RequestParam("pageSize")Integer pageSize) throws Exception {
        return caseContentService.SelectPage(JwtUtil.parseJWT(token).getSubject(),pageNum,pageSize);
    }

    @ApiOperation(value = "条件查询公司案列移动接口")
    @GetMapping("/casecontent-conditions")
    @ApiImplicitParams({
            @ApiImplicitParam(dataType = "string", name = "NumOrStr", value = "文章编号/名称(如果不使用什么也不要传)", required = false),
            @ApiImplicitParam(dataType = "String", name = "caseType", value = "案列分类id(如果不使用什么也不要传)", required = false)
    })
    private ResponseVo companyCaseConditions(@RequestHeader("token")String token,@RequestParam("NumOrStr") String NumOrStr ,@RequestParam("caseType") String caseType) throws Exception{
        return caseContentService.selectByConditions(JwtUtil.parseJWT(token).getSubject(),NumOrStr,caseType);
    }
}
