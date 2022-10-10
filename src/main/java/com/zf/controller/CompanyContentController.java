package com.zf.controller;

import com.zf.domain.vo.ResponseVo;

import com.zf.mapper.*;
import com.zf.service.CaseContentService;
import com.zf.service.CompanyCaseService;
import com.zf.service.CompanyImgService;

import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("/companycontent")
@Api(tags = "个性化内容")
public class CompanyContentController {

    @Autowired
    private CompanyImgService companyImgService;

    @Autowired
    private CompanyCaseService companyCaseService;

    @Autowired
    private CaseContentService caseContentService;

    @Autowired
    private CaseContentMapper caseContentMapper;
    //TODO 顶部图片接口已修改
    @ApiOperation(value = "顶部图片接口")
    @GetMapping("/company_pictures")
    @ApiImplicitParams({
            @ApiImplicitParam(dataType = "string", name = "userId", value = "员工id或者员工token", required = true),
    })
    public ResponseVo companyPictures(@RequestParam("userId") String userId){
        return companyImgService.getcompanyPictures(userId);

    }
    //TODO 案例分类名称接口已修改
    @ApiOperation(value = "案例分类名称接口")
    @GetMapping("/company_case_name")
    @ApiImplicitParams({
            @ApiImplicitParam(dataType = "string", name = "userId", value = "员工id或者员工token", required = true),
    })
    public ResponseVo caseName(@RequestParam("userId") String userId){
        return companyCaseService.getcaseNames(userId);

    }
    @ApiOperation(value = "案例内容接口")
    @GetMapping("/company_case_content")
    @ApiImplicitParams({
            @ApiImplicitParam(dataType = "string", name = "userId", value = "员工id或者员工token", required = true),
    })
    public ResponseVo caseContent(@RequestParam("userId") String userId){
        return caseContentService.getCaseContent(userId);
    }


    @ApiOperation("案例内容浏览量接口")
    @PutMapping("/company_case_views/{cid}")
    public ResponseVo saveCard(@ApiParam(name = "cid",value = "案列内容Id")@PathVariable("cid") String cid){
        return caseContentService.addCaseContentVisitorNumByWu(cid);
    }

}
