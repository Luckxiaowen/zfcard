package com.zf.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zf.domain.entity.CompanyCase;
import com.zf.mapper.CompanyCaseMapper;
import com.zf.service.CompanyCaseService;
import org.springframework.stereotype.Service;

/**
* @author Amireux
* @description 针对表【company_case(公司案列分类表)】的数据库操作Service实现
* @createDate 2022-09-16 08:47:16
*/
@Service
public class CompanyCaseServiceImpl extends ServiceImpl<CompanyCaseMapper, CompanyCase>
implements CompanyCaseService {

}
