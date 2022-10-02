package com.zf.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zf.domain.entity.CompanyCase;
import com.zf.domain.entity.CompanyInfo;
import com.zf.domain.vo.CompanyCaseVo;
import com.zf.domain.vo.CompanyInfoVo;
import io.swagger.models.auth.In;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
* @author Amireux
* @description 针对表【company_case(公司案列分类表)】的数据库操作Mapper
* @createDate 2022-09-16 08:47:16
* @Entity com.zf.domain.CompanyCase
*/
@Repository
public interface CompanyCaseMapper extends BaseMapper<CompanyCase> {

    List<CompanyCaseVo> selectAllByCreateBy(String userId);

    List<CompanyCaseVo>selectMyPage(Long userId, Integer pageNum,Integer pageSize);

    Integer selectMaxOrders();

    CompanyCase moveUp(Integer orders);

    CompanyCase moveDown(Integer orders);
}
