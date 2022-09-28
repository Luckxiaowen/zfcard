package com.zf.mapper;

import com.zf.domain.entity.CompanyFrame;
import com.zf.domain.entity.Notes;
import com.zf.domain.vo.NoteVo;
import com.zf.service.NotesService;
import com.zf.utils.BeanCopyUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @Author wenqin
 * @Date 2022/9/27 16:51
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class NotesTest {
    @Resource
    private NotesService notesService;


    @Test
    public void notesTest(){
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
        System.out.println(notesList);

    }

    public NoteVo getChildrenNote(NoteVo noteVo,List<NoteVo> allList){
        for (NoteVo item : allList) {
            if (Objects.equals(item.getReplyId(),noteVo.getId()))
                return item;
        }
        return null;
    }




}
