package com.zf.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zf.domain.entity.CaseContent;
import org.springframework.stereotype.Repository;

/**
* @author Amireux
* @description 针对表【case_content(公司案列内容表)】的数据库操作Mapper
* @createDate 2022-09-16 08:47:16
* @Entity com.zf.domain.CaseContent
*/
@Repository
public interface CaseContentMapper extends BaseMapper<CaseContent> {


}
