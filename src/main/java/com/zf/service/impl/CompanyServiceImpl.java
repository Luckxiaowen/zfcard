package com.zf.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zf.domain.entity.Company;
import com.zf.mapper.CompanyMapper;
import com.zf.service.CompanyService;
import org.springframework.stereotype.Service;

/**
* @author Amireux
* @description 针对表【company(公司表)】的数据库操作Service实现
* @createDate 2022-09-16 08:47:16
*/
@Service
public class CompanyServiceImpl extends ServiceImpl<CompanyMapper, Company>
implements CompanyService {

}
