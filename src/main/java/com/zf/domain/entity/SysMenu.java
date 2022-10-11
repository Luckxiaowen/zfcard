package com.zf.domain.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 菜单表
 * @TableName sys_menu
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SysMenu implements Serializable {
    /**
     * 菜单Id
     */
    @ApiModelProperty(value = "菜单Id",dataType = "long")
    @TableId
    private Long id;

    /**
     * 菜单名 菜单名
     */
    @ApiModelProperty(value = "菜单名",dataType = "String")
    private String menuName;

    /**
     * 路由地址 路由地址
     */
    @ApiModelProperty(value = "路由地址",dataType = "String")
    private String path;

    /**
     * 组件路径 组件路径
     */
    @ApiModelProperty(value = "组件路径",dataType = "String")
    private String component;

    /**
     * 菜单状态 菜单状态（0显示 1隐藏）
     */
    @ApiModelProperty(value = "菜单状态（0显示 1隐藏）",dataType = "Integer")
    private Integer visible;

    /**
     * 菜单隐藏 菜单状态（0正常 1停用）
     */
    @ApiModelProperty(value = "菜单状态（0正常 1停用）",dataType = "Integer")
    private Integer status;


    /**
     * 权限标识 权限标识
     */
    @ApiModelProperty(value = "权限标识",dataType = "String")
    private String perms;

    /**
     * 菜单图标 菜单图标
     */
    @ApiModelProperty(value = "菜单图标",dataType = "String")
    private String icon;

    /**
     * 创建人
     */
    @ApiModelProperty(value = "创建人",dataType = "Long")
    private Long createBy;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间",dataType = "Date")
    private Date createTime;

    /**
     * 更新人
     */
    @ApiModelProperty(value = "更新人",dataType = "Long")
    private Long updateBy;


    @ApiModelProperty(value = "父级id",dataType = "Long")
    private Long parentId;

    /**
     * 更新时间
     */
    @ApiModelProperty(value = "公司Id",dataType = "long")
    private Date updateTime;

    /**
     * 是否删除 删除标志（0代表未删除，1代表已删除）
     */
    @ApiModelProperty(value = "公司Id",dataType = "long")
    @TableLogic
    private Integer delFlag;

    @ApiModelProperty(value = "公司Id",dataType = "long")
    @TableField(exist = false)
    private List<SysMenu> childrenMenu;

    /**
     * 备注
     */
    @ApiModelProperty(value = "公司Id",dataType = "long")
    private String remark;


}