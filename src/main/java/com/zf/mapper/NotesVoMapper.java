package com.zf.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zf.domain.entity.Notes;
import com.zf.domain.vo.NotesVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotesVoMapper extends BaseMapper<NotesVo> {
    List<NotesVo> getNotesAndReplyAll(@Param("id") Integer id);



    List<NotesVo> getAllNotes(@Param("id") Integer id);
    List<NotesVo> getAllNoPublicById(@Param("id") Integer id);
    List<NotesVo> getAllPublicById(@Param("id") Integer id);
    List<NotesVo> getAllNotesReplayById(@Param("id") Integer id);
    List<NotesVo> getAllNotesNoReplayById(@Param("id") Integer id);


}
