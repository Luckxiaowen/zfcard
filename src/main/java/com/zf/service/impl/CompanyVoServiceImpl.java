package com.zf.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zf.domain.entity.CompanyClient;
import com.zf.domain.vo.CompanyClientVo;
import com.zf.domain.vo.CompanyVo;
import com.zf.domain.vo.ResponseVo;
import com.zf.enums.AppHttpCodeEnum;
import com.zf.mapper.CompanyClientMapper;
import com.zf.mapper.CompanyVoMapper;
import com.zf.service.CompanyClientService;
import com.zf.service.CompanyVoService;
import com.zf.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CompanyVoServiceImpl extends ServiceImpl<CompanyVoMapper, CompanyVo> implements CompanyVoService {

    @Autowired
    private CompanyVoMapper companyVoMapper;
    @Override
    public ResponseVo selectByCreatBy(Integer pageNum, Integer pageSize) {

        Page<CompanyVo> page = new Page<>(pageNum,pageSize);
        Page<CompanyVo> companyVoPage = companyVoMapper.selectByCreatBy(page);
        page.getTotal();//总记录数
        page.getPages();//总页数
        page.hasNext();//上一页
        page.hasPrevious();//下一页

        if (companyVoPage == null){
            return ResponseVo.okResult("暂无任何平台信息！");
        }

        return ResponseVo.okResult(companyVoPage);
    }
}
