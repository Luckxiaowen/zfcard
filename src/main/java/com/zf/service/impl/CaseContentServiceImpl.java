package com.zf.service.impl;


import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
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
        return ResponseVo.okResult(caseContent);
    }

    @Override
    public ResponseVo getsaveCard(Integer caseId) {

       CaseContent caseContentOne = caseContentMapper.selectById(caseId);

        System.out.println(caseContentOne);

        Integer visitorNum = caseContentOne.getVisitorNum();

        Integer visitornum = visitorNum+1;

        LambdaUpdateWrapper<CaseContent> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(CaseContent::getId,caseId);
        updateWrapper.set(CaseContent::getVisitorNum,visitornum);
        caseContentMapper.update(null,updateWrapper);

        return ResponseVo.okResult();
    }
}
