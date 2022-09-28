package com.zf.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.zf.domain.entity.*;
import com.zf.domain.vo.LoginUser;
import com.zf.domain.vo.ResponseVo;
import com.zf.enums.AppHttpCodeEnum;
import com.zf.mapper.*;
import com.zf.service.CaseContentService;
import com.zf.service.CompanyCaseService;
import com.zf.service.CompanyImgService;
import com.zf.utils.JwtUtil;
import io.lettuce.core.dynamic.annotation.Param;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/companycontent")
@Api(tags = "个性化内容")
public class CompanyContentController {

    @Autowired
    private CompanyImgService companyImgService;

    @Autowired
    private CompanyCaseService companyCaseService;

    @Autowired
    private SysUserMapper sysUserMapper;
    @Autowired
    private CaseContentService caseContentService;

    @Autowired
    private CaseContentMapper caseContentMapper;

    @ApiOperation(value = "顶部图片接口")
    @GetMapping("/company_pictures")
    public ResponseVo companyPictures(@RequestHeader("token") String token){
        return companyImgService.getcompanyPictures(token);

    }


    @ApiOperation(value = "案例分类名称接口")
    @GetMapping("/company_case_name")
    public ResponseVo caseName(@RequestHeader("token")String token){
        return companyCaseService.getcaseNames(token);

    }
    @ApiOperation(value = "案例内容接口")
    @GetMapping("/company_case_content")
    public ResponseVo caseContent(@RequestHeader("token") String token){
        return caseContentService.getCaseContent(token);
    }

    @ApiOperation("案例内容浏览量接口")
    @PutMapping("/company_case_views/{id}")
    public ResponseVo saveCard(@RequestHeader("token") String token, @PathVariable("id") Integer id){
        LambdaQueryWrapper<CaseContent> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(CaseContent::getId,id);
        CaseContent selectOne = caseContentMapper.selectOne(lambdaQueryWrapper);
        selectOne.setVisitorNum(selectOne.getVisitorNum()+1);
        caseContentMapper.updateById(selectOne);

        return new ResponseVo(AppHttpCodeEnum.SUCCESS.getCode(), AppHttpCodeEnum.SUCCESS.getMsg());
    }


}
