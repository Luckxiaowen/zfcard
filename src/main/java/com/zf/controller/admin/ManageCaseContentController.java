package com.zf.controller.admin;

import com.zf.domain.entity.CaseContent;
import com.zf.domain.vo.ResponseVo;

import com.zf.service.CaseContentService;
import com.zf.utils.JwtUtil;
import io.swagger.annotations.Api;
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
     * 2.Post是用来新建资源的,也可以用来更新
     * 3.Put用来更新
     * 4.Delete用来删除*/

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
    public ResponseVo listCaseContent(@RequestHeader String token) throws Exception {
        return null;
    }

}
