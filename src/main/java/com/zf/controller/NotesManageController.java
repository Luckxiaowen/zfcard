package com.zf.controller;

import com.zf.domain.entity.Notes;
import com.zf.domain.vo.ResponseVo;
import com.zf.service.NotesService;
import com.zf.utils.JwtUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
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
    @ApiImplicitParams({
            @ApiImplicitParam(dataType = "string", name = "userId", value = "员工id或者员工token", required = true),
    })
    public ResponseVo getNotes(@RequestParam("userId")String userId) throws Exception {
        return notesService.getAllNoteById(userId);
    }
    @ApiOperation(value = "游客留言接口")
    @PostMapping("/add-notes")
    @ApiImplicitParams({
            @ApiImplicitParam(dataType = "string", name = "userId", value = "员工id或者员工token", required = true),
    })
    public ResponseVo addNotes(String userId, @RequestBody Notes notes) {
        return notesService.addNotes(userId,notes);
    }
    @ApiOperation(value = "员工回复留言接口")
    @PostMapping("/reply-notes")
    public ResponseVo replyNotes(@RequestHeader("token")String token,@RequestParam("noteid")String noteid,@RequestParam("rcontent") String rcontent) throws Exception {
        return notesService.replyNotes(JwtUtil.parseJWT(token).getSubject(),noteid,rcontent);
    }
    @ApiOperation(value = "员工删除留言接口")
    @DeleteMapping("/delete-notes/{noteid}")
    public ResponseVo deleteNotes(@RequestHeader("token")String token ,@PathVariable("noteid")Long noteid) throws Exception {
        return notesService.deleteNoteById(JwtUtil.parseJWT(token).getSubject(),noteid);
    }
}
