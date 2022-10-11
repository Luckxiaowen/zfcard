package com.zf.domain.entity;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 用户留言表
 * @TableName notes
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@ApiModel(value = "留言对象",description = "封装接口返回给前端的数据")
public class Notes implements Serializable {
    /**
     * id 留言Id
     */
    @ApiModelProperty(value = "留言Id",dataType = "Long")
    private Long id;

    /**
     * 头像
     */
    @ApiModelProperty(value = "头像",dataType = "String")
    private String avatar;

    /**
     * 姓名
     */
    @ApiModelProperty(value = "姓名",dataType = "String")
    private String name;

    /**
     * 用户id 用户id
     */
    @ApiModelProperty(value = "所属员工id",dataType = "Long")
    private Long userId;

    /**
     * 回复id 回复的哪一个留言
     */
    @ApiModelProperty(value = "回复id 回复的哪一个留言",dataType = "Long")
    private Long replyId;

    /**
     * 留言内容 留言内容
     */
    @ApiModelProperty(value = "留言内容",dataType = "String")
    private String notesContent;

    /**
     * 是否删除 删除标志（0代表未删除，1代表已删除）
     */
    @ApiModelProperty(value = "是否删除 删除标志（0代表未删除，1代表已删除）",dataType = "int")
    private int delFlag;

    /**
     * 是否公开 公开标志（0代表未公开，1代表私密）
     */
    @ApiModelProperty(value = "是否公开 公开标志（0代表未公开，1代表私密）",dataType = "Integer")
    private Integer isPublic;

    /**
     * 创建人 创建人
     */
    @ApiModelProperty(value = "创建人",dataType = "Long")
    private Long createBy;

    /**
     * 创建时间 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty(value = "创建时间",dataType = "Date")
    private Date createTime;

    /**
     * 电话号码
     */
    @ApiModelProperty(value = "电话号码",dataType = "String")
    private String tel;

    private static final long serialVersionUID = 1L;

}