package com.zf.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.zf.domain.entity.Notes;
import com.zf.domain.entity.SysUser;
import com.zf.domain.vo.ResponseVo;
import com.zf.enums.AppHttpCodeEnum;
import com.zf.mapper.NotesMapper;
import com.zf.mapper.SysUserMapper;
import com.zf.service.NotesService;
import com.zf.service.NotesVoService;
import com.zf.utils.JwtUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@Api(tags = "留言管理")
@RequestMapping("/notes_manage")
public class NotesManageController {

    @Autowired
    private NotesVoService notesVoService;

    @ApiOperation(value = "留言总数接口")
    @GetMapping("/notes_total")
    public ResponseVo notesTotal(@RequestHeader("token") String token) throws Exception {

        Integer id = Integer.valueOf(JwtUtil.parseJWT(token).getSubject());


        ResponseVo allNotes = notesVoService.getAllNotes(id);

        return ResponseVo.okResult(allNotes.getData());


    }

    @ApiOperation(value = "留言不公开接口")
    @GetMapping("/notes_on_public")
    public ResponseVo notesNoPublic(@RequestHeader("token") String token){


        ResponseVo allNoPublicById = notesVoService.getAllNoPublicById(token);

        return ResponseVo.okResult(allNoPublicById);
//        return new ResponseVo(AppHttpCodeEnum.SUCCESS.getCode(), AppHttpCodeEnum.SUCCESS.getMsg(),allNoPublicById.getData(),allNoPublicById);

    }
    @ApiOperation(value = "留言公开接口")
    @GetMapping("/notes_public")
    public ResponseVo notesPublic(@RequestHeader("token") String token) {

        ResponseVo allPublicById = notesVoService.getAllPublicById(token);

        return ResponseVo.okResult(allPublicById);

    }
    @ApiOperation(value = "留言已回复接口")
    @GetMapping("/notes_replay")
    public ResponseVo notesReplay(@RequestHeader("token") String token) {
        ResponseVo allReplay = notesVoService.getAllNotesReplayById(token);

        return ResponseVo.okResult(allReplay);

    }
    @ApiOperation(value = "留言未回复接口")
    @GetMapping("/notes_no_replay")
    public ResponseVo notesNoReplay(@RequestHeader("token") String token){
        ResponseVo allNoReplay = notesVoService.getAllNotesNoReplayById(token);

        return ResponseVo.okResult(allNoReplay);

    }

    @ApiOperation(value = "删除留言接口")
    @DeleteMapping("/delete-note/{noteid}")
    public ResponseVo deleteNote(@RequestHeader("token")String token, @PathVariable("noteid") Long noteid) throws Exception {
        return notesVoService.deleteNotes(JwtUtil.parseJWT(token).getSubject(),noteid);
    }

    @ApiOperation(value = "显示回复留言接口")
    @GetMapping("/look_replay_note/{noteid}")
    public ResponseVo lookReplayNote(@PathVariable("noteid") Long noteid) throws Exception {
        return notesVoService.getOneNotes(noteid);

    }

    @ApiOperation(value = "回复留言接口")
    @PostMapping("/replay_note")
    public ResponseVo replayNote(@RequestHeader("token") String token,@RequestParam("replayNotes") String replayNotes,@RequestParam("replayNoteId") Long replayNoteId)  throws Exception {
        return notesVoService.replayNote(token, replayNotes, replayNoteId);

    }

}
