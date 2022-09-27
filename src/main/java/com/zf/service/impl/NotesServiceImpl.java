package com.zf.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zf.domain.entity.Notes;

import com.zf.domain.entity.SysUser;
import com.zf.domain.vo.ResponseVo;
import com.zf.mapper.NotesMapper;
import com.zf.mapper.SysUserMapper;
import com.zf.service.NotesService;
import com.zf.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author Amireux
* @description 针对表【notes(用户留言表)】的数据库操作Service实现
* @createDate 2022-09-16 08:47:17
*/
@Service
public class NotesServiceImpl extends ServiceImpl<NotesMapper, Notes>
implements NotesService {


    @Autowired
    private NotesMapper notesMapper;



}
