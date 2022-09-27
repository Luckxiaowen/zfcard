package com.zf.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zf.domain.entity.CaseContent;
import com.zf.domain.entity.Notes;
import com.zf.domain.entity.SysUser;
import com.zf.domain.vo.NotesVo;
import com.zf.domain.vo.ResponseVo;
import com.zf.enums.AppHttpCodeEnum;
import com.zf.mapper.NotesMapper;
import com.zf.mapper.NotesVoMapper;
import com.zf.mapper.SysUserMapper;
import com.zf.service.NotesService;
import com.zf.service.NotesVoService;
import com.zf.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
* @author Amireux
* @description 针对表【notes(用户留言表)】的数据库操作Service实现
* @createDate 2022-09-16 08:47:17
*/
@Service
public class NotesVoServiceImpl extends ServiceImpl<NotesVoMapper, NotesVo>
implements NotesVoService {


    @Autowired
    private NotesVoMapper notesVoMapper;

    @Autowired
    private NotesMapper notesMapper;

    @Autowired
    private SysUserMapper sysUserMapper;

    @Override
    public ResponseVo getNotesAndReplyAll(String token) {

//     获取登录用户id
        Integer id = null;
        try {
            id = Integer.valueOf(JwtUtil.parseJWT(token).getSubject());
        } catch (Exception e) {
            e.printStackTrace();
        }
//        将notes表中用户id与登录id进行查找匹配
        List<NotesVo> publicAll = notesVoMapper.getNotesAndReplyAll(id);


//
//        System.out.println(publicAll.stream().limit(3));
        return ResponseVo.okResult(publicAll.stream().limit(3));
    }




    @Override
    public ResponseVo getAllNotes(Integer id) {


        List<NotesVo> allNotes = notesVoMapper.getAllNotes(id);
        long count = allNotes.stream().count();
        return ResponseVo.okResult(count);


    }

    @Override
    public ResponseVo getAllNoPublicById(String token) {
        Integer id = null;
        try {
            id = Integer.valueOf(JwtUtil.parseJWT(token).getSubject());
        } catch (Exception e) {
            e.printStackTrace();
        }
        List<NotesVo> allNoPublicById = notesVoMapper.getAllNoPublicById(id);


        int countNoPublic = Math.toIntExact(allNoPublicById.stream().count());
        return ResponseVo.okResult(countNoPublic, String.valueOf(allNoPublicById));
    }

    @Override
    public ResponseVo getAllPublicById(String token) {
        Integer id = null;
        try {
            id = Integer.valueOf(JwtUtil.parseJWT(token).getSubject());
        } catch (Exception e) {
            e.printStackTrace();
        }
        List<NotesVo> allPublicById = notesVoMapper.getAllPublicById(id);
        int countPublic = Math.toIntExact(allPublicById.stream().count());



        return ResponseVo.okResult(countPublic, String.valueOf(allPublicById));

    }

    @Override
    public ResponseVo getAllNotesReplayById(String token) {
        Integer id = null;
        try {
            id = Integer.valueOf(JwtUtil.parseJWT(token).getSubject());
        } catch (Exception e) {
            e.printStackTrace();
        }


        List<NotesVo> allNotesReplayById = notesVoMapper.getAllNotesReplayById(id);
        int countReplay = Math.toIntExact(allNotesReplayById.stream().count());

        return ResponseVo.okResult(countReplay, String.valueOf(allNotesReplayById));
    }

    @Override
    public ResponseVo getAllNotesNoReplayById(String token) {
        Integer id = null;
        try {
            id = Integer.valueOf(JwtUtil.parseJWT(token).getSubject());
        } catch (Exception e) {
            e.printStackTrace();
        }

        List<NotesVo> allNotesNoReplayById = notesVoMapper.getAllNotesNoReplayById(id);
       int countNoReplay = Math.toIntExact(allNotesNoReplayById.stream().count());





        return ResponseVo.okResult(countNoReplay, String.valueOf(allNotesNoReplayById));
    }

    @Override
    public ResponseVo deleteNotes(String token, Long noteid) {
        LambdaQueryWrapper<Notes> qwrapper = new LambdaQueryWrapper<>();
        qwrapper.eq(Notes::getId,noteid);
        qwrapper.and(wrapper->{wrapper.eq(Notes::getDelFlag,0);});
        if (Objects.isNull(notesMapper.selectOne(qwrapper))){
            return new ResponseVo(AppHttpCodeEnum.SUCCESS.getCode(), "当前留言已被删除,请刷新页面!");
        }else{
            Notes notes = notesMapper.selectById(noteid);

            notes.setDelFlag("1");
            if (notesMapper.updateById(notes)>0){
                return new ResponseVo(AppHttpCodeEnum.SUCCESS.getCode(), "删除成功");
            }else{
                return new ResponseVo(AppHttpCodeEnum.SUCCESS.getCode(), "删除失败");
            }
        }

    }

    @Override
    public ResponseVo getOneNotes(Long noteid) {

        Notes notes = notesMapper.selectById(noteid);
        String avatar = notes.getAvatar();
        String name = notes.getName();
        Date createTime = notes.getCreateTime();
        String notesContent = notes.getNotesContent();


        HashMap<String, Object> map = new HashMap<>();
        map.put("avatar",avatar);
        map.put("notesContent",notesContent);
        map.put("name",name);
        map.put("createTime",createTime);

        return ResponseVo.okResult(map);
    }

    @Override
    public ResponseVo replayNote(String token, String replayNotes,Long replayNoteId) {

        Long id = null;
        try {
             id = Long.valueOf(JwtUtil.parseJWT(token).getSubject());
        } catch (Exception e) {
            e.printStackTrace();
        }

        Date date = new Date();
        Notes notes = new Notes(null,null,null,id,replayNoteId,replayNotes,"0",0,null,date);

        notesMapper.insert(notes);


        return ResponseVo.okResult();
    }


}
