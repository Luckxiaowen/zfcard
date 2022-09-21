package com.zf.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.zf.domain.entity.CompanyImg;
import com.zf.domain.entity.CompanyInfo;
import com.zf.domain.entity.SysUser;
import com.zf.domain.vo.CompanyProfileVo;
import com.zf.domain.vo.ResponseVo;
import com.zf.enums.AppHttpCodeEnum;
import com.zf.mapper.CompanyImgMapper;
import com.zf.mapper.CompanyInfoMapper;
import com.zf.mapper.CompanyProfileVoMapper;
import com.zf.mapper.SysUserMapper;
import com.zf.service.CompanyImgService;
import com.zf.service.CompanyInfoService;
import com.zf.utils.JwtUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Stream;

@RestController
@RequestMapping("/companyprofile")
@Api(tags = "个性化简介")
public class CompanyProfileController {

    @Autowired
    private CompanyImgMapper companyImgMapper;

    @Autowired
    private CompanyInfoMapper companyInfoMapper;

    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private CompanyProfileVoMapper companyProfileVoMapper;

    @Autowired
    private CompanyImgService companyImgService;
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
    @ApiOperation(value = "个性化简介名称及内容接口")
    @GetMapping("/company_profile")
    public ResponseVo companyProfile(@RequestHeader("token") String token) throws Exception {
        Integer id = Integer.valueOf(JwtUtil.parseJWT(token).getSubject());

        LambdaQueryWrapper<SysUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(SysUser::getId,id);
        SysUser user = sysUserMapper.selectOne(queryWrapper);
        Integer companyid = Math.toIntExact(user.getCompanyid());

        List<CompanyProfileVo> companyProfileVoList = companyProfileVoMapper.companyProfile(companyid);

        return new ResponseVo(AppHttpCodeEnum.SUCCESS.getCode(),AppHttpCodeEnum.SUCCESS.getMsg(),companyProfileVoList);
    }
}
