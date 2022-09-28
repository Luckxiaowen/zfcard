package com.zf.domain.entity;

import java.io.Serializable;
import java.util.Date;

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
    private int delFlag;

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

    /**
     * 电话号码
     */
    private String tel;

    private static final long serialVersionUID = 1L;

}