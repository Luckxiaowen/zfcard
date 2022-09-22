package com.zf.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.zf.domain.entity.Notes;
import com.zf.domain.entity.SysUser;
import com.zf.domain.vo.ResponseVo;
import com.zf.mapper.NotesMapper;
import com.zf.mapper.SysUserMapper;
import com.zf.utils.JwtUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Api(tags = "留言管理")
@RequestMapping("/notes_manage")
public class NotesManageController {

    @Autowired
    private NotesMapper notesMapper;

    @ApiOperation(value = "留言内容接口")
    @GetMapping("/notes_manage_content")
    public ResponseVo notesContent(@RequestHeader("token") String token) throws Exception {
//        获取登录用户id
        Integer id = Integer.valueOf(JwtUtil.parseJWT(token).getSubject());
//        将notes表中用户id与登录id进行查找匹配
        LambdaQueryWrapper<Notes> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(Notes::getUserId,id);
//        获取当前用户的所以留言记录
        List<Notes> notesList = notesMapper.selectList(wrapper);

        Integer total = notesMapper.selectCount(wrapper);
        System.out.println(total);





        return null;

    }
}
