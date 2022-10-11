package com.zf.domain.entity;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 公司案列分类表
 * @TableName company_case
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompanyCase implements Serializable {
    /**
     * id 案列分类id
     */
    @ApiModelProperty(value = "案列分类id",dataType = "long")
    private Long id;

    /**
     * 公司id 公司Id
     */
    @ApiModelProperty(value = "公司id",dataType = "long")
    private Long companyId;

    /**
     * 分类名称 分类名称
     */
    @ApiModelProperty(value = "分类名称",dataType = "String")
    private String caseName;

    /**
     * 删除标志（0代表未删除，1代表已删除） 删除标志（0代表未删除，1代表已删除）
     */
    @ApiModelProperty(value = "删除标志（0代表未删除，1代表已删除）",dataType = "Integer")
    private Integer delFlag;

    /**
     * 创建人 创建人
     */
    @ApiModelProperty(value = "创建人",dataType = "Long")
    private Long createBy;

    /**
     * 创建时间 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty(value = "创建时间",dataType = "Date")
    private Date createTime;

    /**
     * 更新人 更新人
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty(value = "更新人",dataType = "long")
    private Long updateBy;

    /**
     * 更新时间 更新时间
     */
    @ApiModelProperty(value = "更新时间",dataType = "Date")
    private Date updateTime;

    @ApiModelProperty(value = "排序",dataType = "Integer")
    private Integer orders;


}