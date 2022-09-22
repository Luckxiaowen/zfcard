package com.zf.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zf.domain.entity.Notes;
import com.zf.domain.vo.NotesVo;
import com.zf.domain.vo.ResponseVo;
import com.zf.mapper.NotesMapper;
import com.zf.service.NotesService;
import com.zf.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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

    @Override
    public ResponseVo selectnotesListPublicAll(String token) {
//        获取登录用户id
        Integer id = null;
        try {
            id = Integer.valueOf(JwtUtil.parseJWT(token).getSubject());
        } catch (Exception e) {
            e.printStackTrace();
        }
//        将notes表中用户id与登录id进行查找匹配
        List<NotesVo> publicAll = notesMapper.getUserAndNotesPublicAll(id);

        List<NotesVo> notesList = publicAll.stream().filter(v -> v.getIsPublic().equals(0)).collect(Collectors.toList());
//
        System.out.println(notesList);
        return ResponseVo.okResult(notesList);
    }
}
