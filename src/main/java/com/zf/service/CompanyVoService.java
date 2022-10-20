package com.zf.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zf.domain.vo.CompanyVo;
import com.zf.domain.vo.ResponseVo;


public interface CompanyVoService  extends IService<CompanyVo> {
    ResponseVo selectByCreatBy( Integer pageNum, Integer pageSize);
}
