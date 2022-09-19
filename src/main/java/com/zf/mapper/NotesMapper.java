package com.zf.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zf.domain.entity.Notes;
import org.springframework.stereotype.Repository;

/**
* @author Amireux
* @description 针对表【notes(用户留言表)】的数据库操作Mapper
* @createDate 2022-09-16 08:47:17
* @Entity com.zf.domain.Notes
*/
@Repository
public interface NotesMapper extends BaseMapper<Notes> {


}
