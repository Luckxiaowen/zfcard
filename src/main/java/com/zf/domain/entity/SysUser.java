package com.zf.domain.entity;

import java.io.Serializable;
import java.util.Date;

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
    private Long id;

    /**
     * 用户名 用户名
     */
    private String username;

    /**
     * 昵称 昵称
     */
    private String nickName;

    /**
     * 密码 密码
     */
    private String password;

    /**
     * 账号状态（0正常 1停用） 账号状态（0正常 1停用）
     */
    private Integer status;

    /**
     * 邮箱 邮箱
     */
    private String email;

    /**
     * 手机号 手机号
     */
    private String phonenumber;

    /**
     * 用户性别（0男，1女，2未知） 用户性别（0男，1女，2未知）
     */
    private Integer sex;

    /**
     * 头像 头像
     */
    private String avatar;

    /**
     * 用户类型（0管理员，1普通用户） 用户类型（0管理员，1普通用户）
     */
    private Integer userType;

    /**
     * 创建人的用户id 创建人的用户id
     */
    private Long createBy;

    /**
     * 创建时间 创建时间
     */
    private Date createTime;

    /**
     * 更新人 更新人
     */
    private Long updateBy;

    /**
     * 更新时间 更新时间
     */
    private Date updateTime;

    /**
     * 删除标志（0代表未删除，1代表已删除） 删除标志（0代表未删除，1代表已删除）
     */
    private Integer delFlag;

    /**
     * 微信号 微信号
     */
    private String telWeixin;

    /**
     * 个人简介 个人简介
     */
    private String info;

    /**
     * 微信二维码 微信二维码
     */
    private String weixinCode;

    /**
     * 所属公司id 所属公司id
     */
    private Long companyid;


}