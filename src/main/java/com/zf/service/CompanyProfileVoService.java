package com.zf.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zf.domain.entity.CompanyInfo;
import com.zf.domain.vo.CompanyProfileVo;
import com.zf.domain.vo.ResponseVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;


public interface CompanyProfileVoService extends IService<CompanyProfileVo> {

    ResponseVo getcompanyProfile(String token);
}
