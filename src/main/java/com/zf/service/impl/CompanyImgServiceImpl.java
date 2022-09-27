package com.zf.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zf.domain.entity.CompanyImg;
import com.zf.domain.entity.SysUser;
import com.zf.domain.vo.ResponseVo;
import com.zf.enums.AppHttpCodeEnum;
import com.zf.mapper.CompanyImgMapper;
import com.zf.mapper.SysUserMapper;
import com.zf.service.CompanyImgService;
import com.zf.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;

/**
* @author Amireux
* @description 针对表【company_img(公司图片表)】的数据库操作Service实现
* @createDate 2022-09-16 08:47:16
*/
@Service
public class CompanyImgServiceImpl extends ServiceImpl<CompanyImgMapper, CompanyImg>
implements CompanyImgService {

    @Autowired
    private CompanyImgMapper companyImgMapper;

    @Autowired
    private SysUserMapper sysUserMapper;

    @Override
    public ResponseVo getcompanyPictures(String token) {
        Integer id = null;
        try {
            id = Integer.valueOf(JwtUtil.parseJWT(token).getSubject());
        } catch (Exception e) {
            e.printStackTrace();
        }
        SysUser sysUser = sysUserMapper.selectById(id);
        Long companyid = sysUser.getCompanyid();

        LambdaQueryWrapper<CompanyImg> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(CompanyImg::getCompanyId,companyid);
        CompanyImg companyImg = companyImgMapper.selectOne(queryWrapper);

        String imgPath = companyImg.getImgPath();

        HashMap<String, String> map = new HashMap<>();
        map.put("imgPath",imgPath);

        return ResponseVo.okResult(map);

    }
}
