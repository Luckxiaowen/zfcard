package com.zf.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.zf.domain.entity.Client;
import com.zf.domain.vo.ResponseVo;

import java.text.ParseException;

/**
* @author Amireux
* @description 针对表【client(客户表)】的数据库操作Service
* @createDate 2022-09-16 08:47:16
*/
public interface ClientService extends IService<Client> {

    ResponseVo addClient(Client client);

    ResponseVo clientSummary(String token);

    ResponseVo sevenClientTrend(String token) throws ParseException;

    ResponseVo searchAll(String userId);

    ResponseVo<?> clientVisitor(Integer staffId,Integer time);
}
