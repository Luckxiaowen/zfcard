package com.zf.domain.entity;

import java.io.Serializable;
import java.util.Date;

import io.swagger.annotations.ApiModelProperty;
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
    @ApiModelProperty(value = "曝光统计Id",dataType = "long")
    private Long id;

    /**
     * 创建人 创建人
     */
    @ApiModelProperty(value = "创建人",dataType = "long")
    private Long createBy;

    /**
     * 创建时间 创建时间
     */
    @ApiModelProperty(value = "创建时间",dataType = "Date")
    private Date createTime;

    /**
     * 修改时间 修改时间
     */
    @ApiModelProperty(value = "修改时间",dataType = "Date")
    private Date updateTime;

    /**
     * 访客总量 访客总量
     */
    @ApiModelProperty(value = "访客总量",dataType = "Long")
    private Long visitorTotal;

    /**
     * 客户总量
     */
    @ApiModelProperty(value = "客户总量",dataType = "Long")
    private Long clientTotalNum;

    /**
     * 近七日访客量 近七日访客量
     */
    @ApiModelProperty(value = "近七日访客量",dataType = "long")
    private Long sevenTotal;

    /**
     * 今日访客量 今日访客量
     */
    @ApiModelProperty(value = "今日访客量",dataType = "long")
    private Long dayTotal;

    /**
     * 今日名片下载量 今日名片下载量
     */
    @ApiModelProperty(value = "今日名片下载量",dataType = "long")
    private Long dayDownloadNum;

    /**
     * 今日留言 今日留言
     */
    @ApiModelProperty(value = "今日留言",dataType = "long")
    private Long dayNotes;

    /**
     * 今日添加通讯录 今日添加通讯录
     */
    @ApiModelProperty(value = "今日添加通讯录",dataType = "long")
    private Long dayAddContact;

    /**
     * 每日新增客户 每日新增客户
     */
    @ApiModelProperty(value = "每日新增客户",dataType = "long")
    private Long dayAddClient;

    /**
     * 周新增客户 周新增客户
     */
    @ApiModelProperty(value = "周新增客户",dataType = "long")
    private Long weekAddClient;
    /**
     * 每日转发名片数量
     */
    @ApiModelProperty(value = "每日转发名片数量",dataType = "long")
    private Long dayForwardNum;

    @ApiModelProperty(value = "总停留时常",dataType = "string")
    private String average_stay_min;

    @ApiModelProperty(value = "总停留次数",dataType = "string")
    private int stay_num;

}
