package com.zf.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.zf.domain.entity.CaseContent;
import com.zf.domain.entity.CompanyCase;
import com.zf.domain.entity.CompanyImg;
import com.zf.domain.entity.SysUser;
import com.zf.domain.vo.ResponseVo;
import com.zf.enums.AppHttpCodeEnum;
import com.zf.mapper.CompanyCaseMapper;
import com.zf.mapper.CompanyImgMapper;
import com.zf.mapper.CompanyInfoMapper;
import com.zf.mapper.SysUserMapper;
import com.zf.service.CaseContentService;
import com.zf.utils.JwtUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/companycontent")
@Api(tags = "个性化内容")
public class CompanyContentController {

    @Autowired
    private CompanyImgMapper companyImgMapper;

    @Autowired
    private CompanyCaseMapper companyCaseMapper;

    @Autowired
    private SysUserMapper sysUserMapper;
    @Autowired
    private CaseContentService caseContentService;

    @ApiOperation(value = "顶部图片接口")
    @GetMapping("/company_pictures")
    public ResponseVo companyPictures(@RequestHeader("token") String token) throws Exception {

        Integer id = Integer.valueOf(JwtUtil.parseJWT(token).getSubject());
        SysUser sysUser = sysUserMapper.selectById(id);
        Long companyid = sysUser.getCompanyid();

        LambdaQueryWrapper<CompanyImg> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(CompanyImg::getCompanyId,companyid);
        CompanyImg companyImg = companyImgMapper.selectOne(queryWrapper);

        String imgPath = companyImg.getImgPath();

        HashMap<String, String> map = new HashMap<>();
        map.put("imgPath",imgPath);

        return new ResponseVo(AppHttpCodeEnum.SUCCESS.getCode(),AppHttpCodeEnum.SUCCESS.getMsg(),map);

    }

    @ApiOperation(value = "案例分类名称接口")
    @GetMapping("/company_case_name")
    public ResponseVo caseName(@RequestHeader("token")String token) throws Exception {
//        获取用户id
        Integer id = Integer.valueOf(JwtUtil.parseJWT(token).getSubject());
//        通过用户id获取用户全部信息
        SysUser user = sysUserMapper.selectById(id);
//        获取用户信息中的公司id
        Long companyid = user.getCompanyid();
//        将用户信息中的公司id与案例名称中的公司id比对查找
        LambdaQueryWrapper<CompanyCase> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(CompanyCase::getCompanyId,companyid);

        List<CompanyCase> companyCases = companyCaseMapper.selectList(wrapper);
//        获取一列数据
//        companyCases.stream().map(CompanyCase::getCaseName).collect(Collectors.toList());

//        获取两列数据

        Map<Long, String> collect = companyCases.stream().collect(Collectors.toMap(CompanyCase::getId, CompanyCase::getCaseName));
        for (Map.Entry<Long, String> map:collect.entrySet()) {
            System.out.println(map.getKey() + "," + map.getValue());
        }
        System.out.println();
        return new ResponseVo(AppHttpCodeEnum.SUCCESS.getCode(),AppHttpCodeEnum.SUCCESS.getMsg(),collect);


    }
    @ApiOperation(value = "案例内容接口")
    @GetMapping("/company_case_content")
    public ResponseVo caseContent(@RequestHeader("token") String token) throws Exception {
        Integer userid = Integer.valueOf(JwtUtil.parseJWT(token).getSubject());
        SysUser sysUser = sysUserMapper.selectById(userid);
        Integer companyid = Math.toIntExact(sysUser.getCompanyid());
        ResponseVo caseContent = caseContentService.getCaseContent(companyid);

        return new ResponseVo(AppHttpCodeEnum.SUCCESS.getCode(),AppHttpCodeEnum.SUCCESS.getMsg(),caseContent);

    }


}
