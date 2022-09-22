package com.zf.domain.entity;

import java.io.Serializable;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 公司简介表
 * @TableName company_info
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompanyInfo implements Serializable {
    /**
     * id 公司简介ID
     */
    private Long id;

    /**
     * 公司简介名称 公司简介名称
     */
    private String infoName;

    /**
     * 公司简介内容 公司简介内容
     */
    private String infoContent;

    /**
     * 所属公司Id 所属公司Id
     */
    private Long companyId;

    /**
     * 创建时间 创建时间
     */
    private Date createTime;

    /**
     * 更新时间 更新时间
     */
    private Date updateTime;

    /**
     * 创建人 创建人
     */
    private Long createBy;

    /**
     * 是否删除 删除标志（0代表未删除，1代表已删除）
     */
    private Integer delFlag;

    /**
     * 更新人 更新人
     */
    private Long updateBy;

    private static final long serialVersionUID = 1L;

}