package com.zf.domain.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "公司平台管理表",description = "封装接口返回给前端的数据")
public class CompanyVo {
    /**
     * 公司Id 公司id
     */
    @ApiModelProperty(value = "公司Id",dataType = "long")
    @TableId(value = "id",type = IdType.AUTO)
    private Long id;

    @NotBlank(message = "公司名不能为空")
    private String companyName;

    @NotBlank(message = "公司管理员姓名不能为空")
    private String adminName;

    @NotBlank(message = "公司管理员电话不能为空")
    private String adminTel;

    /**
     * 是否删除 删除标志（0代表未删除，1代表已删除）
     */
    @ApiModelProperty(value = "删除标志 0代表未删除，1代表已删除）",dataType = "Integer")
    @TableLogic
    private Integer delFlag;


    /**
     * 创建时间 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty(value = "创建时间",dataType = "Date")
    private Date createTime;

    /**
     * 更新时间 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty(value = "更新时间",dataType = "Date")
    private Date updateTime;

    /**
     * 创始人
     */

    @ApiModelProperty(value = "创始人",dataType = "String")
    private String userName;
}
