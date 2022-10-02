package com.zf.domain.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @Author wenqin
 * @Date 2022/9/27 17:02
 */
@Data

public class NotesVo {
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
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date createTime;
    @ApiModelProperty(value = "姓名",dataType = "String")
    private String name;
    private String avatar;
    private NotesVo childrenNote;
}