package com.zf.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zf.domain.entity.SysUserRole;
import org.springframework.stereotype.Repository;

/**
* @author Amireux
* @description 针对表【sys_user_role(用户角色表)】的数据库操作Mapper
* @createDate 2022-09-16 08:47:17
* @Entity com.zf.domain.SysUserRole
*/

@Repository
public interface SysUserRoleMapper extends BaseMapper<SysUserRole> {


}
