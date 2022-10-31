package com.zf.service;

import com.zf.domain.vo.ResponseVo;

public interface PushService {

    ResponseVo<?>getAllOpenId();

    String getAssessToken();

    String sendMsg();

}
