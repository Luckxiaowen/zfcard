package com.zf.domain.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * 公司简介表
 * @TableName company_info
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompanyInfoVo implements Serializable {
    /**
     * id 公司简介ID
     */
    @ApiModelProperty(value = "公司简介ID",dataType = "Long")
    private Long id;

    /**
     * 公司简介名称 公司简介名称
     */
    @ApiModelProperty(value = "公司简介名称",dataType = "String")
    private String infoName;

    /**
     * 公司简介内容 公司简介内容
     */
    @ApiModelProperty(value = "公司简介内容",dataType = "String")
    private String infoContent;

    /**
     * 所属公司Id 所属公司Id
     */
    @ApiModelProperty(value = "所属公司Id",dataType = "Long")
    private Long companyId;

    /**
     * 创建时间 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty(value = "创建时间",dataType = "Date")
    private Date createTime;

    /**
     * 更新时间 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty(value = "更新时间",dataType = "Date")
    private Date updateTime;

    /**
     * 创建人 创建人
     */
    @ApiModelProperty(value = "创建人",dataType = "Long")
    private Long createBy;
    @JsonFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty(value = "创建人",dataType = "String")
    private String createName;
    /**
     * 是否删除 删除标志（0代表未删除，1代表已删除）
     */
    @ApiModelProperty(value = "删除标志（0代表未删除，1代表已删除）",dataType = "Integer")
    private Integer delFlag;

    /**
     * 更新人 更新人
     */
    @ApiModelProperty(value = "更新人",dataType = "Long")
    private Long updateBy;

    @ApiModelProperty(value = "排序",dataType = "Integer")
    private Integer orders;

    private static final long serialVersionUID = 1L;

}