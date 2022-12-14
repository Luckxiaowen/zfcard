package com.zf.domain.vo;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Date;

/**
 * 公司表
 * @TableName company
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewCompanyVo implements Serializable {

    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;
    @NotBlank(message = "公司名不能为空")
    private String companyName;

    @NotBlank(message = "公司简称不能为空")
    private String companyAbbreviation;

    @NotBlank(message = "公司LOGO不能为空")
    private String companyLogo;

    @NotBlank(message = "公司管理员姓名不能为空")
    private String adminName;

    @NotBlank(message = "公司管理员电话不能为空")
    private String adminTel;

    @NotBlank(message = "公司到期时间不能为空")
    private String expirationTime;

    @NotBlank(message = "appKey不能为空")
    private String appKey;

    @NotBlank(message = "appSecret不能为空")
    private String appSecret;

    @NotBlank(message = "创建人不能为空")
    private String createBy;

    @NotBlank(message = "个性化简介模块开关（1开，0关）不能为空")
    private int introductionSwitch;

    @NotBlank(message = "个性化内容模块开关（1开，0关）不能为空")
    private int contentSwitch;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    @ApiModelProperty(value = "创建时间",dataType = "Date")
    private Date createTime;
    /**
     * 更新时间 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    @ApiModelProperty(value = "更新时间",dataType = "Date")
    private Date updateTime;

    @TableLogic
    private Integer delFlag;


    @ApiModelProperty(value = "公司状态",dataType = "Integer")
    private Integer status;


}
