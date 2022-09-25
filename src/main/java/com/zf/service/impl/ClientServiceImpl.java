package com.zf.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zf.domain.entity.Client;
import com.zf.domain.vo.ResponseVo;
import com.zf.enums.AppHttpCodeEnum;
import com.zf.mapper.ClientMapper;
import com.zf.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
* @author Amireux
* @description 针对表【client(客户表)】的数据库操作Service实现
* @createDate 2022-09-16 08:47:16
*/
@Service
public class ClientServiceImpl extends ServiceImpl<ClientMapper, Client> implements ClientService {

    @Autowired
    private ClientMapper clientMapper;

    @Override
    public ResponseVo addClient(Client client) {
        int insert = clientMapper.insert(client);
        if (insert>0){
            return ResponseVo.okResult(AppHttpCodeEnum.SUCCESS.getCode(),"添加成功");
        }else{
            return ResponseVo.okResult(AppHttpCodeEnum.SUCCESS.getCode(),"添加失败");
        }
    }
}
