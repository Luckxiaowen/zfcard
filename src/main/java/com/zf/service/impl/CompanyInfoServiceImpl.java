package com.zf.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zf.domain.entity.CompanyInfo;
import com.zf.mapper.CompanyInfoMapper;
import com.zf.service.CompanyInfoService;
import org.springframework.stereotype.Service;

/**
* @author Amireux
* @description 针对表【company_info(公司简介)】的数据库操作Service实现
* @createDate 2022-09-16 08:47:16
*/
@Service
public class CompanyInfoServiceImpl extends ServiceImpl<CompanyInfoMapper, CompanyInfo>
implements CompanyInfoService {

}
