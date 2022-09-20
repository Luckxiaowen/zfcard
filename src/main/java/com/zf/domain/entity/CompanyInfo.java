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
    private Integer companyId;

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

    private static final long serialVersionUID = 1L;

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        CompanyInfo other = (CompanyInfo) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getInfoName() == null ? other.getInfoName() == null : this.getInfoName().equals(other.getInfoName()))
            && (this.getInfoContent() == null ? other.getInfoContent() == null : this.getInfoContent().equals(other.getInfoContent()))
            && (this.getCompanyId() == null ? other.getCompanyId() == null : this.getCompanyId().equals(other.getCompanyId()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
            && (this.getUpdateTime() == null ? other.getUpdateTime() == null : this.getUpdateTime().equals(other.getUpdateTime()))
            && (this.getCreateBy() == null ? other.getCreateBy() == null : this.getCreateBy().equals(other.getCreateBy()))
            && (this.getDelFlag() == null ? other.getDelFlag() == null : this.getDelFlag().equals(other.getDelFlag()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getInfoName() == null) ? 0 : getInfoName().hashCode());
        result = prime * result + ((getInfoContent() == null) ? 0 : getInfoContent().hashCode());
        result = prime * result + ((getCompanyId() == null) ? 0 : getCompanyId().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        result = prime * result + ((getUpdateTime() == null) ? 0 : getUpdateTime().hashCode());
        result = prime * result + ((getCreateBy() == null) ? 0 : getCreateBy().hashCode());
        result = prime * result + ((getDelFlag() == null) ? 0 : getDelFlag().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", infoName=").append(infoName);
        sb.append(", infoContent=").append(infoContent);
        sb.append(", companyId=").append(companyId);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", createBy=").append(createBy);
        sb.append(", delFlag=").append(delFlag);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }


}