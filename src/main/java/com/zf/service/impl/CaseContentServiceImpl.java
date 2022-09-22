package com.zf.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zf.domain.entity.CaseContent;
import com.zf.domain.entity.SysUser;
import com.zf.domain.vo.ResponseVo;
import com.zf.enums.AppHttpCodeEnum;
import com.zf.mapper.CaseContentMapper;
import com.zf.mapper.SysUserMapper;
import com.zf.service.CaseContentService;
import com.zf.utils.JwtUtil;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author Amireux
* @description 针对表【case_content(公司案列内容表)】的数据库操作Service实现
* @createDate 2022-09-16 08:47:16
*/
@Service
public class CaseContentServiceImpl extends ServiceImpl<CaseContentMapper, CaseContent>
implements CaseContentService {

    @Autowired
    private CaseContentMapper caseContentMapper;
    @Autowired
    private SysUserMapper sysUserMapper;
    @Override
    public ResponseVo getCaseContent(@Param("token") String token) {
        Integer userid = null;
        try {
            userid = Integer.valueOf(JwtUtil.parseJWT(token).getSubject());
        } catch (Exception e) {
            e.printStackTrace();
        }
        SysUser sysUser = sysUserMapper.selectById(userid);
        Integer companyid = Math.toIntExact(sysUser.getCompanyid());
        List<CaseContent> caseContent = caseContentMapper.getCaseContent(companyid);
//        ResponseVo caseContent = caseContentMapper.getCaseContent(companyid);

//        return new ResponseVo(AppHttpCodeEnum.SUCCESS.getCode(),AppHttpCodeEnum.SUCCESS.getMsg(),caseContent);


//        List<CaseContent> caseContent = caseContentMapper.getCaseContent(companyid);
        return ResponseVo.okResult(caseContent);
    }
}
