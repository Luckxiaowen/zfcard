package com.zf.domain.vo;

import com.baomidou.mybatisplus.annotation.TableLogic;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;


@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "公司员工管理表",description = "封装接口返回给前端的数据")
public class CompanyClientVo {
    /**
     * id 员工管理id
     */
    @ApiModelProperty(value = "员工管理id",dataType = "integer")
    private Integer id;


    /**
     * 客户名称 客户名称
     */
    @ApiModelProperty(value = "客户名称",dataType = "String")
    private String clientName;

    /**
     * 客户电话 客户电话
     */
    @ApiModelProperty(value = "客户电话",dataType = "String")
    private String clientTel;
    /**
     * 访问次数 访问次数
     */
    @ApiModelProperty(value = "访问次数",dataType = "String")
    private String visitorNum;

    /**
     * 转发次数 转发次数
     */
    @ApiModelProperty(value = "转发次数",dataType = "String")
    private String forwardNum;

    /**
     * 保存次数 保存次数
     */
    @ApiModelProperty(value = "保存次数",dataType = "String")
    private String saveCaseNum;

    /**
     * 保存到通讯录
     */
    @ApiModelProperty(value = "保存到通讯录",dataType = "String")
    private String saveMailListNum;

    /**
     * 留言次数
     */
    @ApiModelProperty(value = "留言次数",dataType = "String")
    private String leaveNum;



    /**
     * 删除标志（0代表未删除，1代表已删除） 删除标志（0代表未删除，1代表已删除）
     */
    @ApiModelProperty(value = "删除标志（0代表未删除，1代表已删除）",dataType = "Integer")
    @TableLogic
    private Integer delFlag;

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
     * 更新时间 更新时间
     */
    @ApiModelProperty(value = "更新时间",dataType = "Date")
    private Date updateTime;

    /**
     * 员工名称 员工名称
     */
    @ApiModelProperty(value = "员工名称",dataType = "String")
    private String userName;
}
