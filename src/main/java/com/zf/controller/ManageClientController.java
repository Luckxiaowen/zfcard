package com.zf.controller;

import com.zf.domain.entity.Client;
import com.zf.domain.vo.ResponseVo;
import com.zf.service.ClientService;
import com.zf.utils.JwtUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api(value = "客户管理", tags = "客户管理接口")
@RestController
public class ManageClientController {

    @Autowired
    private ClientService clientService;

    @PostMapping("/addclient")
    @ApiOperation(value = "添加客户接口")
    public ResponseVo addClient(@RequestBody Client client){
        return clientService.addClient(client);
    }

    @GetMapping("/client-outline")
    @ApiOperation(value = "客户概要接口")
    public ResponseVo clientSummary(@RequestHeader("token")String token) throws Exception {
        return clientService.clientSummary(JwtUtil.parseJWT(token).getSubject());
    }

    @GetMapping("/seven-client-trend")
    @ApiOperation(value = "近七日客户趋势")
    public ResponseVo sevenClientTrend(@RequestHeader("token")String token) throws Exception {
        return clientService.sevenClientTrend(JwtUtil.parseJWT(token).getSubject());
    }

    @GetMapping("/client-list")
    @ApiOperation(value = "全部客户")
    public ResponseVo clientList(@RequestHeader("token")String token) throws Exception {
        return clientService.searchAll(JwtUtil.parseJWT(token).getSubject());
    }

    @GetMapping("/client-visitor")
    @ApiOperation(value = "客服访问次数")
    public ResponseVo<?> clientVisitor(@RequestParam(value = "staffId") Integer staffId,
                                       @RequestParam(value = "time") Integer time){
        return clientService.clientVisitor(staffId,time);
    }

}
