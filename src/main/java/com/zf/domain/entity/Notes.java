package com.zf.domain.entity;

import java.io.Serializable;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用户留言表
 * @TableName notes
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Notes implements Serializable {
    /**
     * id 留言Id
     */
    private Long id;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 姓名
     */
    private String name;


    /**
     * 用户id 用户id
     */
    private Long userId;

    /**
     * 回复id 回复的哪一个留言
     */
    private Long replyId;

    /**
     * 留言内容 留言内容
     */
    private String notesContent;

    /**
     * 是否删除 删除标志（0代表未删除，1代表已删除）
     */
    private String delFlag;

    /**
     * 是否公开 公开标志（0代表未公开，1代表私密）
     */
    private Integer isPublic;

    /**
     * 创建人 创建人
     */
    private Long createBy;

    /**
     * 创建时间 创建时间
     */
    private Date createTime;

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
        Notes other = (Notes) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))

            && (this.getUserId() == null ? other.getUserId() == null : this.getUserId().equals(other.getUserId()))
            && (this.getReplyId() == null ? other.getReplyId() == null : this.getReplyId().equals(other.getReplyId()))
            && (this.getNotesContent() == null ? other.getNotesContent() == null : this.getNotesContent().equals(other.getNotesContent()))
            && (this.getDelFlag() == null ? other.getDelFlag() == null : this.getDelFlag().equals(other.getDelFlag()))
            && (this.getIsPublic() == null ? other.getIsPublic() == null : this.getIsPublic().equals(other.getIsPublic()))
            && (this.getCreateBy() == null ? other.getCreateBy() == null : this.getCreateBy().equals(other.getCreateBy()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getUserId() == null) ? 0 : getUserId().hashCode());
        result = prime * result + ((getReplyId() == null) ? 0 : getReplyId().hashCode());
        result = prime * result + ((getNotesContent() == null) ? 0 : getNotesContent().hashCode());
        result = prime * result + ((getDelFlag() == null) ? 0 : getDelFlag().hashCode());
        result = prime * result + ((getIsPublic() == null) ? 0 : getIsPublic().hashCode());
        result = prime * result + ((getCreateBy() == null) ? 0 : getCreateBy().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", userId=").append(userId);
        sb.append(", replyId=").append(replyId);
        sb.append(", notesContent=").append(notesContent);
        sb.append(", delFlag=").append(delFlag);
        sb.append(", isPublic=").append(isPublic);
        sb.append(", createBy=").append(createBy);
        sb.append(", createTime=").append(createTime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}