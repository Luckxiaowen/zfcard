package com.zf.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zf.domain.entity.SysRoleMenu;
import org.springframework.stereotype.Repository;

/**
* @author Amireux
* @description 针对表【sys_role_menu(角色菜单表)】的数据库操作Mapper
* @createDate 2022-09-16 08:47:17
* @Entity com.zf.domain.SysRoleMenu
*/
@Repository
public interface SysRoleMenuMapper extends BaseMapper<SysRoleMenu> {


}
