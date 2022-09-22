package com.zf.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zf.domain.entity.Company;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
* @author Amireux
* @description 针对表【company(公司表)】的数据库操作Mapper
* @createDate 2022-09-16 08:47:16
* @Entity com.zf.domain.Company
*/
@Repository
public interface CompanyMapper extends BaseMapper<Company> {


    List<Company> selectByConditions(List<String> conList);
}
