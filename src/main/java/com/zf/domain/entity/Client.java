package com.zf.domain.entity;

import java.io.Serializable;
import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 客户表
 * @TableName client
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@ApiModel(value = "客户表",description = "封装接口返回给前端的数据")
public class Client implements Serializable {
    /**
     * id 客户id
     */
    @ApiModelProperty(value = "客户id",dataType = "long")
    private Long id;

    /**
     * 客户头像
     */
    @ApiModelProperty(value = "客户头像",dataType = "String")
    private String avatar;



    /**
     * 客户姓名 客户姓名
     */
    @ApiModelProperty(value = "客户姓名",dataType = "String")
    private String name;

    /**
     * 客户电话 客户电话
     */
    @ApiModelProperty(value = "客户电话",dataType = "String")
    private String tel;

    /**
     * 客户性别 客户性别
     */
    @ApiModelProperty(value = "客户性别",dataType = "String")
    private String sex;

    /**
     * 客户地址 客户地址
     */
    @ApiModelProperty(value = "客户地址",dataType = "String")
    private String address;

    /**
     * 是否删除 是否删除
     */
    @ApiModelProperty(value = "删除标志（0代表未删除，1代表已删)",dataType = "Integer")
    private Integer delFlag;

    /**
     * 创建人 创建人
     */
    @ApiModelProperty(value = "创建人",dataType = "Long")
    private Long createdBy;

    /**
     * 创建时间 创建时间
     */
    @ApiModelProperty(value = "创建时间",dataType = "Date")
    private Date createdTime;

    /**
     * 更新人 更新人
     */
    @ApiModelProperty(value = "更新人",dataType = "Long")
    private Long updatedBy;

    /**
     * 更新时间 更新时间
     */
    @ApiModelProperty(value = "更新时间",dataType = "Date")
    private Date updatedTime;


}