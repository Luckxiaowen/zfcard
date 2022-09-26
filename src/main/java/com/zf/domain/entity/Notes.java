package com.zf.domain.entity;

import java.io.Serializable;
import java.util.Date;

import io.swagger.annotations.ApiModelProperty;
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
    @ApiModelProperty(value = "留言Id",dataType = "long")
    private Long id;

    /**
     * 用户id 用户id
     */
    @ApiModelProperty(value = "用户id",dataType = "long")
    private Long userId;

    /**
     * 回复id 回复的哪一个留言
     */
    @ApiModelProperty(value = "回复的哪一个留言",dataType = "long")
    private Long replyId;

    /**
     * 留言内容 留言内容
     */
    @ApiModelProperty(value = "留言内容",dataType = "String")
    private String notesContent;

    /**
     * 是否删除 删除标志（0代表未删除，1代表已删除）
     */
    @ApiModelProperty(value = "删除标志（0代表未删除，1代表已删除）",dataType = "String")
    private String delFlag;

    /**
     * 是否公开 公开标志（0代表未公开，1代表私密）
     */
    @ApiModelProperty(value = "公开标志（0代表未公开，1代表私密）",dataType = "Integer")
    private Integer isPublic;

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


}
