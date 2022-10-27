package com.zf.domain.entity;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
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
    @ApiModelProperty(value = "id",dataType = "long")
    private Long id;

    /**
    /**
     * 曝光统计表id 曝光统计表id
     */
    @ApiModelProperty(value = "曝光统计表id",dataType = "long")
    private Long expoTotalId;

    /**
     * 日访问量 日访问量
     */
    @ApiModelProperty(value = "日访问量",dataType = "long")
    private Long dayTotal;

    /**
     * 日留言数量 日留言数量
     */
    @ApiModelProperty(value = "日留言数量",dataType = "long")
    private Long dayNotesNum;

    /**
     * 日名片下载数量 日名片下载数量
     */
    @ApiModelProperty(value = "日名片下载数量",dataType = "long")
    private Long dayDownloadNum;

    /**
     * 日通讯录添加数量 日通讯录添加数量
     */
    @ApiModelProperty(value = "日通讯录添加数量",dataType = "long")
    private Long dayAddContact;

    /**
     * 日客户增加量 日客户增加量
     */
    @ApiModelProperty(value = "日客户增加量",dataType = "long")
    private Long dayAddClient;

    /**
     * 周访客量 周访客量
     */
    @ApiModelProperty(value = "周访客量",dataType = "long")
    private Long weekVisitorNum;

    /**
     * 周客户增加量 周客户增加量
     */
    @ApiModelProperty(value = "周客户增加量",dataType = "long")
    private Long weekClientNum;

    /**
     * 创建时间 创建时间
     */
    @ApiModelProperty(value = "创建时间",dataType = "long")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date createTime;

    @ApiModelProperty(value = "总停留时常",dataType = "int")
    private Integer averageStayMin;

    @ApiModelProperty(value = "总停留次数",dataType = "string")
    private int stayNum;

}