package com.zf.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zf.domain.entity.SysMenu;
import com.zf.domain.vo.MenuVo;
import com.zf.mapper.SysMenuMapper;
import com.zf.service.SysMenuService;
import com.zf.utils.BeanCopyUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
* @author Amireux
* @description 针对表【sys_menu(菜单表)】的数据库操作Service实现
* @createDate 2022-09-16 08:47:17
*/
@Service
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu>
implements SysMenuService {
    @Resource
    private SysMenuMapper menuMapper;


    @Override
    public List<MenuVo> getSysMenuByUserId(Long id) {
        List<SysMenu> menuList = menuMapper.getSysMenuByUserId(id, (long) -1,null);
        for (SysMenu menu : menuList) {
            List<SysMenu> childrenMenu = getChildrenMenu(menu.getId());
            menu.setChildrenMenu(childrenMenu);
        }
        List<MenuVo> menuVoList = new ArrayList<>();
        for (SysMenu sysMenu : menuList) {
            MenuVo menuVo = BeanCopyUtils.copyBean(sysMenu, MenuVo.class);
            List<MenuVo> childrenMenuVo = BeanCopyUtils.copyBeanList(sysMenu.getChildrenMenu(), MenuVo.class);
            menuVo.setChildrenMenu(childrenMenuVo);
            menuVoList.add(menuVo);
        }
        return menuVoList;
    }

    private List<SysMenu> getChildrenMenu(Long id) {
        return menuMapper.getSysMenuByUserId(null,null,id);
    }
}
