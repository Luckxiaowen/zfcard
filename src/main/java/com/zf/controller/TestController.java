package com.zf.controller;


import com.zf.domain.entity.Notes;
import com.zf.domain.vo.NoteVo;
import com.zf.domain.vo.ResponseVo;
import com.zf.service.NotesService;
import com.zf.utils.BeanCopyUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
@Api(value = "测试", tags = "测试")
public class TestController {

    @ApiOperation(value = "测试")
    @GetMapping("/test")
    public ResponseVo listCategory(){
        List<NoteVo> noteVoList = notesTest();
        return ResponseVo.okResult(noteVoList);
    }

    @Resource
    private NotesService notesService;


    public List<NoteVo> notesTest(){
        List<Notes> list = notesService.list();
        List<NoteVo> noteVoList = BeanCopyUtils.copyBeanList(list, NoteVo.class);
        System.out.println(noteVoList);
        List<NoteVo> notesList = noteVoList.stream()
                .filter(item -> item.getReplyId() == 0)
                .map(m->{
                    m.setChildrenNote(getChildrenNote(m,noteVoList));
                    return m;
                })
                .collect(Collectors.toList());
        return notesList;

    }

    public NoteVo getChildrenNote(NoteVo noteVo,List<NoteVo> allList){
        for (NoteVo item : allList) {
            if (Objects.equals(item.getReplyId(),noteVo.getId()))
                return item;
        }
        return null;
    }
}
