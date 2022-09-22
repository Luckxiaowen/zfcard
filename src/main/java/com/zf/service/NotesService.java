package com.zf.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zf.domain.entity.Notes;
import com.zf.domain.vo.ResponseVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author Amireux
* @description 针对表【notes(用户留言表)】的数据库操作Service
* @createDate 2022-09-16 08:47:17
*/
public interface NotesService extends IService<Notes> {

//获取当前用户的所有留言记录
    ResponseVo selectnotesListPublicAll(@Param("token") String token);
}
