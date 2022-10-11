package com.zf.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zf.domain.dto.RoleDto;
import com.zf.domain.entity.SysRole;
import com.zf.domain.entity.SysRoleMenu;
import com.zf.domain.vo.LoginUser;
import com.zf.domain.vo.ResponseVo;
import com.zf.enums.AppHttpCodeEnum;
import com.zf.exception.SystemException;
import com.zf.mapper.SysRoleMapper;
import com.zf.service.SysRoleMenuService;
import com.zf.service.SysRoleService;
import com.zf.utils.UserUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
* @author Amireux
* @description 针对表【sys_role(角色表)】的数据库操作Service实现
* @createDate 2022-09-16 08:47:17
*/
@Service
@Slf4j
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole>
implements SysRoleService {

    @Resource
    private SysRoleMapper roleMapper;

    @Resource
    private SysRoleMenuService roleMenuService;

    /**
     * @author wenqin
     * @return ResponseVo
     * @Date 2022-09-17 17:00:15
     */
    @Override
    @Transactional
    public ResponseVo<?> addRole(RoleDto roleDto) {
        LoginUser loginUser = UserUtils.getLoginUser();
        SysRole tempRole = getTempRole(roleDto.getRoleName(),loginUser.getSysUser().getCompanyid());
        if (!Objects.isNull(tempRole))
            throw new SystemException(AppHttpCodeEnum.ROLE_EXIST);

        SysRole role = new SysRole();
        role
                .setName(roleDto.getRoleName())
                .setCompanyId(loginUser.getSysUser().getCompanyid())
                .setCreateBy(loginUser.getSysUser().getId())
                .setUpdateBy(loginUser.getSysUser().getId());
        save(role);
        List<Long> menuId = roleDto.getMenuId();
        for (Long id : menuId) {
            SysRoleMenu roleMenu = new SysRoleMenu(role.getId(), id);
            roleMenuService.save(roleMenu);
        }

        return ResponseVo.okResult(AppHttpCodeEnum.SUCCESS.getCode(),"添加成功!");
    }

    @Override
    public ResponseVo<?> getAllRole() {
        LoginUser loginUser = UserUtils.getLoginUser();

        List<SysRole> roleList = roleMapper.getAllRole(loginUser.getSysUser().getCompanyid());

        return ResponseVo.okResult(roleList);
    }

    @Override
    @Transactional
    public ResponseVo<?> updateRole(RoleDto roleDto) {
        LoginUser loginUser = UserUtils.getLoginUser();

        SysRole tempRole = getTempRole(roleDto.getRoleName(), loginUser.getSysUser().getCompanyid());
        if (Objects.isNull(tempRole))
            throw new SystemException(AppHttpCodeEnum.ROLE_NOT_EXIST);
        tempRole.setName(roleDto.getRoleName());
        tempRole.setUpdateBy(loginUser.getSysUser().getId());
        updateById(tempRole);

        LambdaQueryWrapper<SysRoleMenu> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysRoleMenu::getRoleId,tempRole.getId());
        roleMenuService.remove(queryWrapper);

        roleDto.getMenuId().forEach(item -> {
            SysRoleMenu roleMenu = new SysRoleMenu(tempRole.getId(), item);
            roleMenuService.save(roleMenu);
        });

        return ResponseVo.okResult();
    }

    @Override
    @Transactional
    public ResponseVo<?> delRole(Long id) {
        LoginUser loginUser = UserUtils.getLoginUser();
        SysRole role = getById(id);
        if (Objects.isNull(role))
            throw new SystemException(AppHttpCodeEnum.ROLE_NOT_EXIST);
        if ( !loginUser.getSysUser().getCompanyid().equals(role.getCompanyId()))
            throw new SystemException(AppHttpCodeEnum.NO_OPERATOR_AUTH);
        removeById(id);

        LambdaQueryWrapper<SysRoleMenu> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysRoleMenu::getRoleId,id);
        roleMenuService.remove(queryWrapper);

        return ResponseVo.okResult();
    }

    @Override
    public ResponseVo<?> getRoleMenuById(Long id) {
        LambdaQueryWrapper<SysRoleMenu> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysRoleMenu::getRoleId,id);
        List<Long> menuId = roleMenuService.list(queryWrapper)
                .stream()
                .map(SysRoleMenu::getMenuId)
                .collect(Collectors.toList());
        return ResponseVo.okResult(menuId);
    }

    /**
     *
     * @param roleName 角色名
     * @param companyId 公司id
     * @return SysRole
     */
    private SysRole getTempRole(String roleName,Long companyId){
        LambdaQueryWrapper<SysRole> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .eq(SysRole::getName,roleName)
                .eq(SysRole::getCompanyId,companyId);
        return getOne(queryWrapper);
    }

}
