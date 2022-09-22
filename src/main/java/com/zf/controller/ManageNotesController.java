package com.zf.controller;

import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(value = "管理个人留言简介模块", tags = "个人留言管理接口")
@RequestMapping("/user")
public class ManageNotesController {

}
