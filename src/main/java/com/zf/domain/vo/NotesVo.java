package com.zf.domain.vo;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.models.auth.In;
import lombok.Data;

import java.util.Date;

@Data
public class NotesVo {
    /**
     * id 客户id
     */
    @ApiModelProperty(value = "客户id",dataType = "long")
    private Long clientId;

    /**
     * 客户头像 客户头像
     */
    @ApiModelProperty(value = "客户头像",dataType = "String")
    private String avatar;


    /**
     * 客户姓名 客户姓名
     */
    @ApiModelProperty(value = "客户姓名",dataType = "String")
    private String name;

    /**
     * 客户电话 客户电话
     */
    @ApiModelProperty(value = "客户电话",dataType = "String")
    private String tel;

    /**
     * 客户性别 客户性别
     */
    @ApiModelProperty(value = "客户性别",dataType = "String")
    private String sex;

    /**
     * 客户地址 客户地址
     */
    @ApiModelProperty(value = "客户地址",dataType = "String")
    private String address;

    /**
     * 是否删除 是否删除
     */
    @ApiModelProperty(value = "删除标志（0代表未删除，1代表已删)",dataType = "Integer")
    private Integer delFlag;

    /**
     * 创建人 创建人
     */
    @ApiModelProperty(value = "创建人",dataType = "Long")
    private Long createdBy;

    /**
     * 创建时间 创建时间
     */
    @ApiModelProperty(value = "创建时间",dataType = "Date")
    private Date createdTime;

    /**
     * 更新人 更新人
     */
    @ApiModelProperty(value = "更新人",dataType = "Long")
    private Long updatedBy;

    /**
     * 更新时间 更新时间
     */
    @ApiModelProperty(value = "更新时间",dataType = "Date")
    private Date updatedTime;

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
    @ApiModelProperty(value = "回复id",dataType = "long")

    private Long replyId;

    /**
     * 留言内容 留言内容
     */
    @ApiModelProperty(value = "留言内容",dataType = "String")

    private String notesContent;
    /**
     * 回复内容 回复内容
     */
    @ApiModelProperty(value = "回复内容",dataType = "String")

    private String notesContent1;


    /**
     * 是否公开 公开标志（0代表未公开，1代表私密）
     */
    @ApiModelProperty(value = "是否公开",dataType = "Integer")

    private Integer isPublic;

    /**
     * 创建人 创建人
     */
    @ApiModelProperty(value = "创建人",dataType = "Long")

    private Long createBy;

    /**
     * 创建时间 创建时间
     */
    @ApiModelProperty(value = "创建时间",dataType = "Date")

    private Date createTime;

    public NotesVo() {
    }

    public NotesVo(Long clientId, String avatar, String name, String tel, String sex, String address, Integer delFlag, Long createdBy, Date createdTime, Long updatedBy, Date updatedTime, Long id, Long userId, Long replyId, String notesContent, Integer isPublic, Long createBy, Date createTime) {
        this.clientId = clientId;
        this.avatar = avatar;
        this.name = name;
        this.tel = tel;
        this.sex = sex;
        this.address = address;
        this.delFlag = delFlag;
        this.createdBy = createdBy;
        this.createdTime = createdTime;
        this.updatedBy = updatedBy;
        this.updatedTime = updatedTime;
        this.id = id;
        this.userId = userId;
        this.replyId = replyId;
        this.notesContent = notesContent;
        this.isPublic = isPublic;
        this.createBy = createBy;
        this.createTime = createTime;
    }

}
