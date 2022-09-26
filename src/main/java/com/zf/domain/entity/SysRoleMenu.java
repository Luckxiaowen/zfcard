package com.zf.domain.entity;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 角色菜单表
 * @TableName sys_role_menu
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SysRoleMenu implements Serializable {
    /**
     * 角色Id
     */
    @ApiModelProperty(value = "角色Id",dataType = "long")
    private Long roleId;

    /**
     * 菜单Id
     */
    @ApiModelProperty(value = "菜单id",dataType = "long")
    private Long menuId;

    private static final long serialVersionUID = 1L;


}