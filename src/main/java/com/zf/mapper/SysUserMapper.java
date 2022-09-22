package com.zf.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zf.domain.entity.SysUser;
import com.zf.domain.vo.SearchUserVo;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
* @author Amireux
* @description 针对表【sys_user(用户表)】的数据库操作Mapper
* @createDate 2022-09-16 08:47:17
* @Entity com.zf.domain.SysUser
*/
@Repository
public interface SysUserMapper extends BaseMapper<SysUser> {


    List<SysUser> selectByConditions(SearchUserVo searchUserVo);
}
