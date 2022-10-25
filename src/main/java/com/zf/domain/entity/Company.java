package com.zf.domain.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

/**
 * 公司表
 * @TableName company
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Company implements Serializable {

    @TableId
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

    @TableLogic
    private Integer delFlag;

    /**
     * 创始人
     */
    @ApiModelProperty(value = "创始人",dataType = "Long")
    private Long createBy;

}
