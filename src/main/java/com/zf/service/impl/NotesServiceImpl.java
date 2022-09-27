package com.zf.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zf.domain.entity.Notes;

import com.zf.domain.entity.SysUser;
import com.zf.domain.vo.NVo;
import com.zf.domain.vo.NotesVo;
import com.zf.domain.vo.ResponseVo;
import com.zf.enums.AppHttpCodeEnum;
import com.zf.mapper.NotesMapper;
import com.zf.mapper.SysUserMapper;
import com.zf.service.NotesService;
import com.zf.utils.BeanCopyUtils;
import com.zf.utils.JwtUtil;
import com.zf.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
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
    public ResponseVo getAllNoteInfo(String subject) {
        SysUser sysUser = UserUtils.getLoginUser().getSysUser();
        List<NVo>HaveReplayList=notesMapper.selectNotesHaveReplyBid(sysUser.getId());
        return ResponseVo.okResult(HaveReplayList);
    }

    @Override
    public ResponseVo getAllNoteById(String token) throws Exception {
        List<NotesVo> notesVos = this.HaveReplyNotes(token);
        return ResponseVo.okResult(notesVos);
    }

    public List<NotesVo> HaveReplyNotes(String token) throws Exception {
        String subject = JwtUtil.parseJWT(token).getSubject();
        LambdaQueryWrapper<Notes> queryWrapper=new LambdaQueryWrapper();
        queryWrapper.eq(Notes::getCreateBy,Long.valueOf(subject));
        List<Notes> list =this.list(queryWrapper);
        List<NotesVo> noteVoList = BeanCopyUtils.copyBeanList(list, NotesVo.class);
        System.out.println(noteVoList);
        List<NotesVo> notesList = noteVoList.stream()
                .filter(item -> item.getReplyId() == 0)
                .map(m->{
                    m.setChildrenNote(getChildrenNote(m,noteVoList));
                    return m;
                })
                .collect(Collectors.toList());
        return notesList;

    }

    public NotesVo getChildrenNote(NotesVo noteVo,List<NotesVo> allList){
        for (NotesVo item : allList) {
            if (Objects.equals(item.getReplyId(),noteVo.getId()))
                return item;
        }
        return null;
    }
}
