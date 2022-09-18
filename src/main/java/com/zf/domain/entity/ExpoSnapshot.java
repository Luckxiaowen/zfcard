package com.zf.domain.entity;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 曝光快照表
 * @TableName expo_snapshot
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExpoSnapshot implements Serializable {
    /**
     * id id
     */
    private Long id;

    /**
     * 曝光统计表id 曝光统计表id
     */
    private Long expoTotalId;

    /**
     * 日访问量 日访问量
     */
    private Long dayTotal;

    /**
     * 日留言数量 日留言数量
     */
    private Long dayNotesNum;

    /**
     * 日名片下载数量 日名片下载数量
     */
    private Long dayDownloadNum;

    /**
     * 日通讯录添加数量 日通讯录添加数量
     */
    private Long dayAddContact;

    /**
     * 日客户增加量 日客户增加量
     */
    private Long dayAddClient;

    /**
     * 周访客量 周访客量
     */
    private Long weekVisitorNum;

    /**
     * 周客户增加量 周客户增加量
     */
    private Long weekClientNum;

    /**
     * 创建时间 创建时间
     */
    private Long createTime;

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
        ExpoSnapshot other = (ExpoSnapshot) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getExpoTotalId() == null ? other.getExpoTotalId() == null : this.getExpoTotalId().equals(other.getExpoTotalId()))
            && (this.getDayTotal() == null ? other.getDayTotal() == null : this.getDayTotal().equals(other.getDayTotal()))
            && (this.getDayNotesNum() == null ? other.getDayNotesNum() == null : this.getDayNotesNum().equals(other.getDayNotesNum()))
            && (this.getDayDownloadNum() == null ? other.getDayDownloadNum() == null : this.getDayDownloadNum().equals(other.getDayDownloadNum()))
            && (this.getDayAddContact() == null ? other.getDayAddContact() == null : this.getDayAddContact().equals(other.getDayAddContact()))
            && (this.getDayAddClient() == null ? other.getDayAddClient() == null : this.getDayAddClient().equals(other.getDayAddClient()))
            && (this.getWeekVisitorNum() == null ? other.getWeekVisitorNum() == null : this.getWeekVisitorNum().equals(other.getWeekVisitorNum()))
            && (this.getWeekClientNum() == null ? other.getWeekClientNum() == null : this.getWeekClientNum().equals(other.getWeekClientNum()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getExpoTotalId() == null) ? 0 : getExpoTotalId().hashCode());
        result = prime * result + ((getDayTotal() == null) ? 0 : getDayTotal().hashCode());
        result = prime * result + ((getDayNotesNum() == null) ? 0 : getDayNotesNum().hashCode());
        result = prime * result + ((getDayDownloadNum() == null) ? 0 : getDayDownloadNum().hashCode());
        result = prime * result + ((getDayAddContact() == null) ? 0 : getDayAddContact().hashCode());
        result = prime * result + ((getDayAddClient() == null) ? 0 : getDayAddClient().hashCode());
        result = prime * result + ((getWeekVisitorNum() == null) ? 0 : getWeekVisitorNum().hashCode());
        result = prime * result + ((getWeekClientNum() == null) ? 0 : getWeekClientNum().hashCode());
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
        sb.append(", expoTotalId=").append(expoTotalId);
        sb.append(", dayTotal=").append(dayTotal);
        sb.append(", dayNotesNum=").append(dayNotesNum);
        sb.append(", dayDownloadNum=").append(dayDownloadNum);
        sb.append(", dayAddContact=").append(dayAddContact);
        sb.append(", dayAddClient=").append(dayAddClient);
        sb.append(", weekVisitorNum=").append(weekVisitorNum);
        sb.append(", weekClientNum=").append(weekClientNum);
        sb.append(", createTime=").append(createTime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}