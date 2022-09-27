package com.zf.mapper;

import com.zf.domain.entity.CaseContent;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.Assert.*;

public class CaseContentMapperTest {

    @Autowired
    private CaseContentMapper caseContentMapper;
    @Test
    public void getCaseContent() {
        List<CaseContent> caseContent = caseContentMapper.getCaseContent(1);
        System.out.println(caseContent);
    }
}