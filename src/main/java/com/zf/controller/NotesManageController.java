package com.zf.controller;

import com.zf.domain.entity.Notes;
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

    @ApiOperation(value = "留言概况及全部留言")
    @GetMapping("/notes")
    public ResponseVo getNotes(@RequestHeader("token")String token) throws Exception {
        return notesService.getAllNoteById(token);
    }
    @ApiOperation(value = "游客留言接口")
    @PostMapping("/add-notes")
    public ResponseVo addNotes(@RequestHeader("token")String token, @RequestBody Notes notes) throws Exception {
        return notesService.addNotes(JwtUtil.parseJWT(token).getSubject(),notes);
    }
    @ApiOperation(value = "员工回复留言接口")
    @PostMapping("/reply-notes")
    public ResponseVo replyNotes(@RequestHeader("token")String token,@RequestParam("noteid")String noteid,@RequestParam("rcontent") String rcontent) throws Exception {
        return notesService.replyNotes(JwtUtil.parseJWT(token).getSubject(),noteid,rcontent);
    }
}
