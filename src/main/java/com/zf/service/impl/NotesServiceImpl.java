package com.zf.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zf.domain.entity.Notes;
import com.zf.mapper.NotesMapper;
import com.zf.service.NotesService;
import org.springframework.stereotype.Service;

/**
* @author Amireux
* @description 针对表【notes(用户留言表)】的数据库操作Service实现
* @createDate 2022-09-16 08:47:17
*/
@Service
public class NotesServiceImpl extends ServiceImpl<NotesMapper, Notes>
implements NotesService {

}
