package com.zf.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zf.domain.entity.CompanyInfo;
import com.zf.domain.vo.CompanyInfoVo;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
* @author Amireux
* @description 针对表【company_info(公司简介)】的数据库操作Mapper
* @createDate 2022-09-16 08:47:16
* @Entity com.zf.domain.CompanyInfo
*/
@Repository
public interface CompanyInfoMapper extends BaseMapper<CompanyInfo> {

    List<CompanyInfoVo> selectAllByCreateBy(String userId);

    List<CompanyInfoVo> selectMyPage(Long userId ,Integer pageNum, Integer pageSize);

    /**
     * @Description:查找当前最大的orders值
     * @return : int
     */
    Integer selectMaxOrders();

    /**
     * 上移
     * @param orders
     * @return
     */
    CompanyInfo moveUp(Integer orders);

    /**
     *  下移
     * @param orders
     * @return
     */
    CompanyInfo moveDown(Integer orders);

}


