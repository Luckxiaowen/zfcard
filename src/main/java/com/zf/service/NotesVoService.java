package com.zf.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zf.domain.entity.Notes;
import com.zf.domain.vo.NotesVo;
import com.zf.domain.vo.ResponseVo;
import org.apache.ibatis.annotations.Param;

/**
* @author Amireux
* @description 针对表【notes_vo(用户留言表)】的数据库操作Service
* @createDate 2022-09-16 08:47:17
*/
public interface NotesVoService extends IService<NotesVo> {

    //获取当前用户的所有留言与回复记录
    ResponseVo getNotesAndReplyAll(@Param("token") String token);


    //    获取当前用户的所有留言总数
    ResponseVo getAllNotes(@Param("id") Integer id);
    ResponseVo getAllNoPublicById(@Param("token") String token);
    ResponseVo getAllPublicById(@Param("token") String token);
    ResponseVo getAllNotesReplayById(@Param("token") String token);

    ResponseVo getAllNotesNoReplayById(@Param("token") String token);

    ResponseVo deleteNotes(@Param("token") String token, Long noteid);

    ResponseVo getOneNotes(Long noteid);

    ResponseVo replayNote(String token,String replayNotes,Long replayNoteId);
}
