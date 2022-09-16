package com.zf.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zf.domain.entity.SysMenu;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
* @author Amireux
* @description 针对表【sys_menu(菜单表)】的数据库操作Mapper
* @createDate 2022-09-16 08:47:17
* @Entity com.zf.domain.SysMenu
*/
@Repository
public interface SysMenuMapper extends BaseMapper<SysMenu> {


    List<String> selectPermsByUserId(Long id);

}
