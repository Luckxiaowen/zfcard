package com.zf.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zf.domain.entity.Client;
import com.zf.domain.entity.CompanyClient;
import com.zf.domain.vo.CompanyClientVo;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CompanyClientMapper extends BaseMapper<CompanyClient> {

}
