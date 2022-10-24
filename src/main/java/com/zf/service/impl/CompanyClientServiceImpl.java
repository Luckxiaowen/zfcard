package com.zf.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zf.domain.entity.CompanyClient;
import com.zf.domain.vo.CompanyClientVo;
import com.zf.domain.vo.ResponseVo;
import com.zf.enums.AppHttpCodeEnum;
import com.zf.mapper.CompanyClientMapper;
import com.zf.mapper.CompanyClientVoMapper;
import com.zf.service.CompanyClientService;
import com.zf.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CompanyClientServiceImpl extends ServiceImpl<CompanyClientMapper, CompanyClient> implements CompanyClientService {


    @Autowired
    private CompanyClientVoMapper companyClientVoMapper;

    @Override
    public ResponseVo selectByCreatBy(String token,Integer pageNum, Integer pageSize) {
        if (token == null){
            return ResponseVo.errorResult(AppHttpCodeEnum.PARAMETER_ERROR);
        }
        String userId = null;
        try {
            userId = JwtUtil.parseJWT(token).getSubject();
        } catch (Exception e) {
            e.printStackTrace();
        }

        Page<CompanyClientVo> page = new Page<>(pageNum,pageSize);
        Page<CompanyClientVo> clientVoPage = companyClientVoMapper.selectByCreatBy(page, userId);
        page.getTotal();//总记录数
        page.getPages();//总页数
        page.hasNext();//上一页
        page.hasPrevious();//下一页

        if (clientVoPage == null){
            return ResponseVo.okResult("该员工目前暂无客户！");
        }

        return ResponseVo.okResult(clientVoPage);
    }

    @Override
    public ResponseVo selectByLike(String token, Integer pageNum, Integer pageSize, String query) {
        if (token == null){
            return ResponseVo.errorResult(AppHttpCodeEnum.PARAMETER_ERROR);
        }
        String userId = null;
        try {
            userId = JwtUtil.parseJWT(token).getSubject();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if("".equals(pageNum) && "".equals(pageSize)){
            return ResponseVo.errorResult(AppHttpCodeEnum.PARAMETER_ERROR);

        }
        if(query == null){
            Page<CompanyClientVo> page = new Page<>(pageNum,pageSize);
            Page<CompanyClientVo> noQuery = companyClientVoMapper.selectByCreatBy(page,userId);

            return ResponseVo.okResult(noQuery);

        }
        Page<CompanyClientVo> page = new Page<>(pageNum,pageSize);
        Page<CompanyClientVo> byLike = companyClientVoMapper.selectByLike(page, userId, query);
        page.getTotal();//总记录数
        page.getPages();//总页数
        page.hasNext();//上一页
        page.hasPrevious();//下一页
        return ResponseVo.okResult(byLike);
    }


}
