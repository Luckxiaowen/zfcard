package com.zf.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zf.domain.entity.SysRole;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
* @author Amireux
* @description 针对表【sys_role(角色表)】的数据库操作Mapper
* @createDate 2022-09-16 08:47:17
* @Entity com.zf.domain.SysRole
*/
@Repository
public interface SysRoleMapper extends BaseMapper<SysRole> {


    List<SysRole> getAllRole();
}
