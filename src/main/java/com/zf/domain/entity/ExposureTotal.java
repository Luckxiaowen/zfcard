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

    /**
     * 每日转发名片数量
     */
    private Long dayForwardNum;


}
