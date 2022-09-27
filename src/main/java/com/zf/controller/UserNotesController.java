package com.zf.controller;

import com.zf.domain.vo.ResponseVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(tags = "用户留言")
@RequestMapping("/user_notes")
public class UserNotesController {

    @ApiOperation(value = "留言板留言及回复接口")
    @GetMapping("/message_board_notes")
    public ResponseVo messageBoardNotes(@RequestHeader("token") String token) {

        return null;
    }

}
