package com.zf.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zf.domain.entity.SysMenu;
import com.zf.mapper.SysMenuMapper;
import com.zf.service.SysMenuService;
import org.springframework.stereotype.Service;

/**
* @author Amireux
* @description 针对表【sys_menu(菜单表)】的数据库操作Service实现
* @createDate 2022-09-16 08:47:17
*/
@Service
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu>
implements SysMenuService {

}
