package com.zf.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zf.domain.entity.Notes;
import com.zf.domain.vo.ResponseVo;
import org.apache.ibatis.annotations.Param;

/**
* @author Amireux
* @description 针对表【notes(用户留言表)】的数据库操作Service
* @createDate 2022-09-16 08:47:17
*/
public interface NotesService extends IService<Notes> {

    ResponseVo getAllNoteInfo(String subject);

    ResponseVo getAllNoteById(String token) throws Exception;
}
