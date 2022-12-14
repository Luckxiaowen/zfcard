package com.zf.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zf.domain.dto.RoleDto;
import com.zf.domain.entity.SysRole;
import com.zf.domain.vo.ResponseVo;

import java.util.List;

/**
* @author Amireux
* @description 针对表【sys_role(角色表)】的数据库操作Service
* @createDate 2022-09-16 08:47:17
*/
public interface SysRoleService extends IService<SysRole> {

    ResponseVo<?> addRole(RoleDto roleDto);

    ResponseVo<?> getAllRole();

    ResponseVo<?> updateRole(RoleDto roleDto);

    ResponseVo<?> delRole(Long id);

    ResponseVo<?> getRoleMenuById(Long id);
}
