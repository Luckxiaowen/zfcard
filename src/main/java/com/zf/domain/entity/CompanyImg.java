package com.zf.domain.entity;

import java.io.Serializable;
import java.util.Date;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 公司图片表
 * @TableName company_img
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompanyImg implements Serializable {
    /**
     * id 图片Id
     */
    @ApiModelProperty(value = "图片Id",dataType = "long")
    private Long id;

    /**
     * 公司Id 所属公司Id
     */
    @ApiModelProperty(value = "所属公司Id",dataType = "companyId")
    private Long companyId;

    /**
     * 图片路径 图片路径
     */
    @ApiModelProperty(value = "图片路径",dataType = "String")
    private String imgPath;

    /**
     * 是否删除 删除标志（0代表未删除，1代表已删除）
     */
    @ApiModelProperty(value = "删除标志（0代表未删除，1代表已删除）",dataType = "Integer")
    private Integer delFalg;

    /**
     * 创建人 创建人
     */

    @ApiModelProperty(value = "创建人",dataType = "Long")
    private Long createBy;

    /**
     * 创建时间 创建时间
     */
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
    @ApiModelProperty(value = "更新时间",dataType = "Date")
    private Date updateTime;

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
        CompanyImg other = (CompanyImg) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getCompanyId() == null ? other.getCompanyId() == null : this.getCompanyId().equals(other.getCompanyId()))
            && (this.getImgPath() == null ? other.getImgPath() == null : this.getImgPath().equals(other.getImgPath()))
            && (this.getDelFalg() == null ? other.getDelFalg() == null : this.getDelFalg().equals(other.getDelFalg()))
            && (this.getCreateBy() == null ? other.getCreateBy() == null : this.getCreateBy().equals(other.getCreateBy()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
            && (this.getUpdateBy() == null ? other.getUpdateBy() == null : this.getUpdateBy().equals(other.getUpdateBy()))
            && (this.getUpdateTime() == null ? other.getUpdateTime() == null : this.getUpdateTime().equals(other.getUpdateTime()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getCompanyId() == null) ? 0 : getCompanyId().hashCode());
        result = prime * result + ((getImgPath() == null) ? 0 : getImgPath().hashCode());
        result = prime * result + ((getDelFalg() == null) ? 0 : getDelFalg().hashCode());
        result = prime * result + ((getCreateBy() == null) ? 0 : getCreateBy().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        result = prime * result + ((getUpdateBy() == null) ? 0 : getUpdateBy().hashCode());
        result = prime * result + ((getUpdateTime() == null) ? 0 : getUpdateTime().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", companyId=").append(companyId);
        sb.append(", imgPath=").append(imgPath);
        sb.append(", delFalg=").append(delFalg);
        sb.append(", createBy=").append(createBy);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateBy=").append(updateBy);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}