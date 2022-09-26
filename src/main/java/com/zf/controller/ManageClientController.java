package com.zf.controller;

import com.zf.domain.entity.Client;
import com.zf.domain.vo.ResponseVo;
import com.zf.service.ClientService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "客户管理", tags = "客户管理客户管理接口")
@RestController
public class ManageClientController {

    @Autowired
    private ClientService clientService;

    @PostMapping("/addclient")
    @ApiOperation(value = "添加客户接口")
    public ResponseVo addClient(@RequestBody Client client){
        return clientService.addClient(client);
    }
}
