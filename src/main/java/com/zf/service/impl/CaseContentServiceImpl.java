package com.zf.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zf.domain.entity.CaseContent;
import com.zf.mapper.CaseContentMapper;
import com.zf.service.CaseContentService;
import org.springframework.stereotype.Service;

/**
* @author Amireux
* @description 针对表【case_content(公司案列内容表)】的数据库操作Service实现
* @createDate 2022-09-16 08:47:16
*/
@Service
public class CaseContentServiceImpl extends ServiceImpl<CaseContentMapper, CaseContent>
implements CaseContentService {

}
