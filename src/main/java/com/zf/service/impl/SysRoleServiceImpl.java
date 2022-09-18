package com.zf.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zf.domain.entity.SysRole;
import com.zf.domain.vo.LoginUser;
import com.zf.domain.vo.ResponseVo;
import com.zf.enums.AppHttpCodeEnum;
import com.zf.mapper.SysRoleMapper;
import com.zf.service.SysRoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

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

    /**
     * @author wenqin
     * @return ResponseVo
     * @Date 2022-09-17 17:00:15
     */
    @Override
    public ResponseVo addRole(SysRole role) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        role.setCreateBy(loginUser.getSysUser().getId());
        role.setUpdateBy(loginUser.getSysUser().getId());
        int res = roleMapper.insert(role);
        log.info("add Role res:" + res);
        return ResponseVo.okResult(AppHttpCodeEnum.SUCCESS.getCode(),"新增成功!");
    }

    @Override
    public ResponseVo getAllRole() {
        List<SysRole> roleList = roleMapper.getAllRole();
        return ResponseVo.okResult(roleList);
    }
}
