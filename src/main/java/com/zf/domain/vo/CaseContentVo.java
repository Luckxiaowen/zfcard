package com.zf.domain.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * 公司案列内容表
 * @TableName case_content
 */
@ToString
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "公司案列内容表",description = "封装接口返回给前端的数据")
public class CaseContentVo implements Serializable {
    /**
     * id id
     */
    @ApiModelProperty(value = "id",dataType = "long")
    private Long id;

    /**
     * 公司案列分类id 公司案列分类id
     */
    @ApiModelProperty(value = "公司案列分类id",dataType = "Long")
    private Long caseId;

    /**
     * 案列标题 案列标题
     */
    @ApiModelProperty(value = "案列标题",dataType = "String")
    private String title;

    /**
     * 案列主图 案列主图
     */
    @ApiModelProperty(value = "案列主图",dataType = "imgPath")
    private String imgPath;

    /**
     * 案列内容 案列内容
     */
    @ApiModelProperty(value = "案列内容",dataType = "String")
    private String content;

    /**
     * 创建人id 创建人id
     */
    @ApiModelProperty(value = "创建人id",dataType = "Long")
    private Long createBy;

    /**
     * 创建时间 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty(value = "创建时间",dataType = "Date")
    private Date createTime;

    /**
     * 修改人id 修改人id
     */
    @ApiModelProperty(value = "修改人id",dataType = "Long")
    private Long updateBy;

    /**
     * 修改时间 修改时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty(value = "修改时间",dataType = "Date")
    private Date updateTime;

    /**
     * 是否删除 是否删除
     */
    @ApiModelProperty(value = "删除标志（0代表未删除，1代表已删除）",dataType = "Integer")
    private Integer delFlag;

    /**
     * 访问量 访问量
     */
    @ApiModelProperty(value = "访问量",dataType = "Integer")
    private Integer visitorNum;
    /**
     * 基础访问量 基础访问量
     */
    @ApiModelProperty(value = "基础访问量",dataType = "Integer")
    private Integer baseVisitorNum;

    @ApiModelProperty(value = "创建人姓名",dataType = "String")
    private String createName;

    @ApiModelProperty(value = "文章所属分类",dataType = "String")
    private String caseName;
}