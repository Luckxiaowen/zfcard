package com.zf.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zf.domain.entity.SysMenu;
import com.zf.domain.vo.MenuVo;

import java.util.List;

/**
* @author Amireux
* @description 针对表【sys_menu(菜单表)】的数据库操作Service
* @createDate 2022-09-16 08:47:17
*/
public interface SysMenuService extends IService<SysMenu> {
    List<MenuVo> getSysMenuByUserId(Long id);
}
