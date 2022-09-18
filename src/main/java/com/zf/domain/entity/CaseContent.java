package com.zf.domain.entity;

import java.io.Serializable;
import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 公司案列内容表
 * @TableName case_content
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "公司案列内容表",description = "封装接口返回给前端的数据")
public class CaseContent implements Serializable {
    /**
     * id id
     */
    @ApiModelProperty(value = "id",dataType = "long")
    private Long id;

    /**
     * 公司案列分类id 公司案列分类id
     */
    @ApiModelProperty(value = "公司案列分类id",dataType = "Long")
    private Long caseId;

    /**
     * 案列标题 案列标题
     */
    @ApiModelProperty(value = "案列标题",dataType = "String")
    private String title;

    /**
     * 案列主图 案列主图
     */
    @ApiModelProperty(value = "案列主图",dataType = "imgPath")
    private String imgPath;

    /**
     * 案列内容 案列内容
     */
    @ApiModelProperty(value = "案列内容",dataType = "String")
    private String content;

    /**
     * 创建人id 创建人id
     */
    @ApiModelProperty(value = "创建人id",dataType = "Long")
    private Long createBy;

    /**
     * 创建时间 创建时间
     */
    @ApiModelProperty(value = "创建时间",dataType = "Date")
    private Date createTime;

    /**
     * 修改人id 修改人id
     */
    @ApiModelProperty(value = "修改人id",dataType = "Long")
    private Long updateBy;

    /**
     * 修改时间 修改时间
     */
    @ApiModelProperty(value = "修改时间",dataType = "Date")
    private Date updateTime;

    /**
     * 是否删除 是否删除
     */
    @ApiModelProperty(value = "删除标志（0代表未删除，1代表已删除）",dataType = "Integer")
    private Integer delFlag;

    /**
     * 访问量 访问量
     */
    @ApiModelProperty(value = "访问量",dataType = "Integer")
    private Integer visitorNum;

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
        CaseContent other = (CaseContent) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getCaseId() == null ? other.getCaseId() == null : this.getCaseId().equals(other.getCaseId()))
            && (this.getTitle() == null ? other.getTitle() == null : this.getTitle().equals(other.getTitle()))
            && (this.getImgPath() == null ? other.getImgPath() == null : this.getImgPath().equals(other.getImgPath()))
            && (this.getContent() == null ? other.getContent() == null : this.getContent().equals(other.getContent()))
            && (this.getCreateBy() == null ? other.getCreateBy() == null : this.getCreateBy().equals(other.getCreateBy()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
            && (this.getUpdateBy() == null ? other.getUpdateBy() == null : this.getUpdateBy().equals(other.getUpdateBy()))
            && (this.getUpdateTime() == null ? other.getUpdateTime() == null : this.getUpdateTime().equals(other.getUpdateTime()))
            && (this.getDelFlag() == null ? other.getDelFlag() == null : this.getDelFlag().equals(other.getDelFlag()))
            && (this.getVisitorNum() == null ? other.getVisitorNum() == null : this.getVisitorNum().equals(other.getVisitorNum()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getCaseId() == null) ? 0 : getCaseId().hashCode());
        result = prime * result + ((getTitle() == null) ? 0 : getTitle().hashCode());
        result = prime * result + ((getImgPath() == null) ? 0 : getImgPath().hashCode());
        result = prime * result + ((getContent() == null) ? 0 : getContent().hashCode());
        result = prime * result + ((getCreateBy() == null) ? 0 : getCreateBy().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        result = prime * result + ((getUpdateBy() == null) ? 0 : getUpdateBy().hashCode());
        result = prime * result + ((getUpdateTime() == null) ? 0 : getUpdateTime().hashCode());
        result = prime * result + ((getDelFlag() == null) ? 0 : getDelFlag().hashCode());
        result = prime * result + ((getVisitorNum() == null) ? 0 : getVisitorNum().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", caseId=").append(caseId);
        sb.append(", title=").append(title);
        sb.append(", imgPath=").append(imgPath);
        sb.append(", content=").append(content);
        sb.append(", createBy=").append(createBy);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateBy=").append(updateBy);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", delFlag=").append(delFlag);
        sb.append(", visitorNum=").append(visitorNum);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}