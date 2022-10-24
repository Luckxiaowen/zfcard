package com.zf.service;

import com.zf.domain.dto.DingTalkDto;
import com.zf.domain.vo.ResponseVo;

public interface DingTalkService {
    ResponseVo<?> getDep();

    ResponseVo<?>getAssessToken(DingTalkDto dingTalkDto) throws Exception;


}
