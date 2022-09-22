package com.zf.domain.entity;

import java.io.Serializable;
import java.util.Date;

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
    private Long id;

    /**
     * 公司id 公司Id
     */
    private Long companyId;

    /**
     * 分类名称 分类名称
     */
    private String caseName;

    /**
     * 删除标志（0代表未删除，1代表已删除） 删除标志（0代表未删除，1代表已删除）
     */
    private Integer delFlag;

    /**
     * 创建人 创建人
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


}