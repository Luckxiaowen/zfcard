package com.zf.domain.entity;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用户表
 * @TableName sys_user
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SysUser implements Serializable {
    /**
     * 主键 主键
     */
    @ApiModelProperty(value = "主键",dataType = "long")
    @TableId
    private Long id;

    /**
     * 用户名 用户名
     */
    @ApiModelProperty(value = "用户名",dataType = "String")
    private String username;

    /**
     * 昵称 昵称
     */
    @ApiModelProperty(value = "昵称",dataType = "String")
    private String nickName;

    /**
     * 密码 密码
     */
    @ApiModelProperty(value = "密码",dataType = "String")
    private String password;

    /**
     * 账号状态（0正常 1停用） 账号状态（0正常 1停用）
     */
    @ApiModelProperty(value = "账号状态（0正常 1停用）",dataType = "Integer")
    private Integer status;

    /**
     * 邮箱 邮箱
     */
    @ApiModelProperty(value = "邮箱",dataType = "String")
    private String email;

    /**
     * 手机号 手机号
     */
    @ApiModelProperty(value = "手机号",dataType = "String")
    private String phonenumber;

    /**
     * 用户性别（0男，1女，2未知） 用户性别（0男，1女，2未知）
     */
    @ApiModelProperty(value = "用户性别（0男，1女，2未知）",dataType = "Integer")
    private Integer sex;

    /**
     * 头像 头像
     */
    @ApiModelProperty(value = "头像",dataType = "String")
    private String avatar;

    /**
     * 用户类型（0管理员，1普通用户） 用户类型（0管理员，1普通用户）
     */
    @ApiModelProperty(value = "用户类型（0管理员，1普通用户）",dataType = "Integer")
    private Integer userType;

    /**
     * 创建人的用户id 创建人的用户id
     */
    @ApiModelProperty(value = "创建人的用户id",dataType = "Long")
    private Long createBy;

    /**
     * 创建时间 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    @ApiModelProperty(value = "创建时间",dataType = "Date")
    private Date createTime;

    /**
     * 更新人 更新人
     */
    @ApiModelProperty(value = "更新人",dataType = "Long")
    private Long updateBy;

    /**
     * 更新时间 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    @ApiModelProperty(value = "更新时间",dataType = "Date")
    private Date updateTime;

    /**
     * 删除标志（0代表未删除，1代表已删除） 删除标志（0代表未删除，1代表已删除）
     */
    @ApiModelProperty(value = "删除标志（0代表未删除，1代表已删除）",dataType = "Integer")
    @TableLogic
    private Integer delFlag;

    /**
     * 微信号 微信号
     */
    @ApiModelProperty(value = "微信号",dataType = "String")
    private String telWeixin;

    /**
     * 个人简介 个人简介
     */
    @ApiModelProperty(value = "个人简介",dataType = "String")
    private String info;

    /**
     * 微信二维码 微信二维码
     */
    @ApiModelProperty(value = "微信二维码地址",dataType = "String")
    private String weixinCode;

    /**
     * 所属公司id 所属公司id
     */
    @ApiModelProperty(value = "所属公司id",dataType = "Long")
    private Long companyid;

    /**
     *  微信openedId 微信openedId
     */
    @ApiModelProperty(value = "微信openedId",dataType = "String")

    private String openedId;

    @ApiModelProperty(value = "岗位名称",dataType = "String")
    private String station;

    @ApiModelProperty(value = "所属部门Id",dataType = "Integer")
    private Integer depId;


}