package com.zf.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zf.domain.entity.CompanyClient;
import com.zf.domain.vo.CompanyClientVo;
import com.zf.domain.vo.ResponseVo;

public interface CompanyClientService extends IService<CompanyClient> {
    ResponseVo selectByCreatBy(String token,Integer pageNum, Integer pageSize);

    ResponseVo selectByLike(String token,Integer pageNum, Integer pageSize,String query);

}
