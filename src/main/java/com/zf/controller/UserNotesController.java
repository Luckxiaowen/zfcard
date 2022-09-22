package com.zf.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.zf.domain.entity.Notes;
import com.zf.domain.vo.ResponseVo;
import com.zf.mapper.NotesMapper;
import com.zf.service.NotesService;
import com.zf.utils.JwtUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@Api(tags = "用户留言")
@RequestMapping("/user_notes")
public class UserNotesController {
    @Autowired
    private NotesService notesService;

    @ApiOperation(value = "留言板展示接口")
    @GetMapping("/message_board")
    public ResponseVo messageBoard(@RequestHeader("token") String token) {

        return notesService.selectnotesListPublicAll(token);
    }
}
