package com.zf.domain.entity;

import java.io.Serializable;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 曝光统计表
 * @TableName exposure_total
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExposureTotal implements Serializable {
    /**
     * id 曝光统计Id
     */
    private Long id;

    /**
     * 创建人 创建人
     */
    private Long createBy;

    /**
     * 创建时间 创建时间
     */
    private Date createTime;

    /**
     * 修改时间 修改时间
     */
    private Date updateTime;

    /**
     * 访客总量 访客总量
     */
    private Long visitorTotal;

    /**
     * 客户总量
     */
    private Long clientTotalNum;

    /**
     * 近七日访客量 近七日访客量
     */
    private Long sevenTotal;

    /**
     * 今日访客量 今日访客量
     */
    private Long dayTotal;

    /**
     * 今日名片下载量 今日名片下载量
     */
    private Long dayDownloadNum;

    /**
     * 今日留言 今日留言
     */
    private Long dayNotes;

    /**
     * 今日添加通讯录 今日添加通讯录
     */
    private Long dayAddContact;

    /**
     * 每日新增客户 每日新增客户
     */
    private Long dayAddClient;

    /**
     * 周新增客户 周新增客户
     */
    private Long weekAddClient;

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
        ExposureTotal other = (ExposureTotal) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getCreateBy() == null ? other.getCreateBy() == null : this.getCreateBy().equals(other.getCreateBy()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
            && (this.getUpdateTime() == null ? other.getUpdateTime() == null : this.getUpdateTime().equals(other.getUpdateTime()))
            && (this.getVisitorTotal() == null ? other.getVisitorTotal() == null : this.getVisitorTotal().equals(other.getVisitorTotal()))
            && (this.getClientTotalNum() == null ? other.getClientTotalNum() == null : this.getClientTotalNum().equals(other.getClientTotalNum()))
            && (this.getSevenTotal() == null ? other.getSevenTotal() == null : this.getSevenTotal().equals(other.getSevenTotal()))
            && (this.getDayTotal() == null ? other.getDayTotal() == null : this.getDayTotal().equals(other.getDayTotal()))
            && (this.getDayDownloadNum() == null ? other.getDayDownloadNum() == null : this.getDayDownloadNum().equals(other.getDayDownloadNum()))
            && (this.getDayNotes() == null ? other.getDayNotes() == null : this.getDayNotes().equals(other.getDayNotes()))
            && (this.getDayAddContact() == null ? other.getDayAddContact() == null : this.getDayAddContact().equals(other.getDayAddContact()))
            && (this.getDayAddClient() == null ? other.getDayAddClient() == null : this.getDayAddClient().equals(other.getDayAddClient()))
            && (this.getWeekAddClient() == null ? other.getWeekAddClient() == null : this.getWeekAddClient().equals(other.getWeekAddClient()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getCreateBy() == null) ? 0 : getCreateBy().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        result = prime * result + ((getUpdateTime() == null) ? 0 : getUpdateTime().hashCode());
        result = prime * result + ((getVisitorTotal() == null) ? 0 : getVisitorTotal().hashCode());
        result = prime * result + ((getClientTotalNum() == null) ? 0 : getClientTotalNum().hashCode());
        result = prime * result + ((getSevenTotal() == null) ? 0 : getSevenTotal().hashCode());
        result = prime * result + ((getDayTotal() == null) ? 0 : getDayTotal().hashCode());
        result = prime * result + ((getDayDownloadNum() == null) ? 0 : getDayDownloadNum().hashCode());
        result = prime * result + ((getDayNotes() == null) ? 0 : getDayNotes().hashCode());
        result = prime * result + ((getDayAddContact() == null) ? 0 : getDayAddContact().hashCode());
        result = prime * result + ((getDayAddClient() == null) ? 0 : getDayAddClient().hashCode());
        result = prime * result + ((getWeekAddClient() == null) ? 0 : getWeekAddClient().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", createBy=").append(createBy);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", visitorTotal=").append(visitorTotal);
        sb.append(", clientTotalNum=").append(clientTotalNum);
        sb.append(", sevenTotal=").append(sevenTotal);
        sb.append(", dayTotal=").append(dayTotal);
        sb.append(", dayDownloadNum=").append(dayDownloadNum);
        sb.append(", dayNotes=").append(dayNotes);
        sb.append(", dayAddContact=").append(dayAddContact);
        sb.append(", dayAddClient=").append(dayAddClient);
        sb.append(", weekAddClient=").append(weekAddClient);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}