package com.zf.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.zf.domain.entity.CaseContent;
import com.zf.domain.vo.ResponseVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.PathVariable;
/**
* @author Amireux
* @description 针对表【case_content(公司案列内容表)】的数据库操作Service
* @createDate 2022-09-16 08:47:16
*/
public interface CaseContentService extends IService<CaseContent> {

    ResponseVo addCaseContent(String userId, CaseContent caseContent);

    ResponseVo deleteCaseContent(Long userId, Long casecontentid);

    ResponseVo updateCaseContent(long parseLong, CaseContent caseContent);

    ResponseVo getCaseContent(String userId);

    ResponseVo selectAll(String subject);

    ResponseVo SelectPage(String subject, Integer pageNUm, Integer pageSize);

    ResponseVo addCaseContentVisitorNumByWu(String cid);

    ResponseVo selectByConditions(String token, String numOrStr, String caseType);
}
