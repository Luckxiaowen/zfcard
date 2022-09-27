package com.zf.controller;

import com.zf.domain.vo.ResponseVo;
import com.zf.service.NotesService;
import com.zf.utils.JwtUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@Api(tags = "留言管理")
@RequestMapping("/notes_manage")
public class NotesManageController {


    @Autowired
    private NotesService notesService;

    @ApiOperation(value = "回复留言接口")
    @GetMapping("/notes")
    public ResponseVo getNotes(@RequestHeader("token")String token) throws Exception {
        return notesService.getAllNoteById(token);
    }

}
