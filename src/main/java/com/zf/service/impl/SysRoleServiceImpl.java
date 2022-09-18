package com.zf.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zf.domain.entity.SysRole;
import com.zf.mapper.SysRoleMapper;
import com.zf.service.SysRoleService;
import org.springframework.stereotype.Service;

/**
* @author Amireux
* @description 针对表【sys_role(角色表)】的数据库操作Service实现
* @createDate 2022-09-16 08:47:17
*/
@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole>
implements SysRoleService {

}
