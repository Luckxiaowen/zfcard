package com.zf.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zf.domain.entity.CompanyImg;
import com.zf.domain.entity.SysUser;
import com.zf.domain.vo.ResponseVo;
import com.zf.enums.AppHttpCodeEnum;
import com.zf.exception.SystemException;
import com.zf.mapper.CompanyImgMapper;
import com.zf.mapper.SysUserMapper;
import com.zf.service.CompanyImgService;
import com.zf.utils.JwtUtil;
import com.zf.utils.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Objects;

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
    //TODO 使用id或者token获取数据
    @Override
    public ResponseVo getcompanyPictures(String id) {
        if (id==null||"".equals(id)){
            return new ResponseVo(AppHttpCodeEnum.FAIL.getCode(), "获取顶部图片失败：id输入为空");
        }else{
            Integer userId = getInteger(id);
            if (Objects.isNull(sysUserMapper.selectById(userId))){
                return new ResponseVo(AppHttpCodeEnum.FAIL.getCode(), "获取顶部图片失败：不存在此员工");
            }else{
                Long companyid =sysUserMapper.selectById(userId).getCompanyid();
                if (companyid==null||"".equals(companyid)){
                    return new ResponseVo(AppHttpCodeEnum.FAIL.getCode(), "获取顶部图片失败：当前员工不属于任何公司");
                }else{
                    LambdaQueryWrapper<CompanyImg> queryWrapper = new LambdaQueryWrapper<>();
                    queryWrapper.eq(CompanyImg::getCompanyId,companyid);
                    CompanyImg companyImg = companyImgMapper.selectOne(queryWrapper);
                    String imgPath = companyImg.getImgPath();
                    HashMap<String, String> map = new HashMap<>();
                    map.put("imgPath",imgPath);
                    return ResponseVo.okResult(map);
                }
            }
        }
    }

    public Integer getInteger(String userId) {
        int id;
        if (Validator.isNumeric(userId)) {
            id = Integer.parseInt(userId);
        } else {
            //TODO 员工token获取
            try {
                String subject = JwtUtil.parseJWT(userId).getSubject();
                id = Integer.parseInt(subject);
            } catch (Exception e) {
                throw new SystemException(AppHttpCodeEnum.PARAMETER_ERROR);
            }
        }
        return id;
    }
}
