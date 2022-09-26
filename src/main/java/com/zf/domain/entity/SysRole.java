package com.zf.domain.entity;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;


/**
 * 角色表
 * @TableName sys_role
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class SysRole implements Serializable {
    /**
     * 角色Id 角色Id
     */
    @ApiModelProperty(value = "角色Id",dataType = "long")
    private Long id;
    /**
     * 角色名称 角色名称
     */
    @NotBlank(message = "角色名称不能为空!")
    @ApiModelProperty(value = "角色名称",dataType = "String")
    private String name;

    /**
     * 角色权限字符串 角色权限字符串
     */
    @ApiModelProperty(value = "角色权限字符串",dataType = "String")
    @NotBlank(message = "角色路由不能为空!")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String roleKey;

    /**
     * 角色状态（0正常 1停用） 角色状态（0正常 1停用）
     */
    @ApiModelProperty(value = "角色状态（0正常 1停用）",dataType = "Integer")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer status;

    /**
     * 是否删除 删除标志（0代表未删除，1代表已删除）
     */
    @ApiModelProperty(value = "删除标志（0代表未删除，1代表已删除）",dataType = "Integer")
    @TableLogic
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer delFlag;

    /**
     * 
     */
    @ApiModelProperty(value = "创建人Id",dataType = "long")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Long createBy;

    /**
     * 
     */
    @ApiModelProperty(value = "创建时间",dataType = "date")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private Date createTime;

    /**
     * 
     */
    @ApiModelProperty(value = "更新人id",dataType = "long")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Long updateBy;

    /**
     * 
     */
    @ApiModelProperty(value = "更新时间",dataType = "date")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Date updateTime;

    @ApiModelProperty(value = "归属公司id",dataType = "date")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Long companyId;

    /**
     * 
     */

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String remark;

    @ApiModelProperty(value = "创建人",dataType = "long")
    @TableField(exist = false)
    private String createUser;

    private static final long serialVersionUID = 1L;


}