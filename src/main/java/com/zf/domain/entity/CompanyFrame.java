package com.zf.domain.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompanyFrame {

    @TableId(type = IdType.INPUT)
    @ApiModelProperty(value = "部门id名称")
    private Long id;


    /**
     * 公司id 公司id
     */
    private Long companyId;

    @ApiModelProperty(value = "部门名称", required = true)
    @NotBlank(message = "部门名不能为空哦!")
    private String roleName;

    @ApiModelProperty(value = "该部门的父ID",required = true)
    private Long parentId;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private Date createTime;

    @ApiModelProperty(value = "公司Id",dataType = "long")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;
    @ApiModelProperty(value = "创建人",dataType = "long")
    private Integer createBy;

    @ApiModelProperty(value = "更新人",dataType = "long")
    private Integer updateBy;

    @ApiModelProperty(value = "删除标志（0代表未删除，1代表已删除）",dataType = "Integer")
    @TableLogic
    private Integer delFlag;

    @ApiModelProperty(value = "角色列表",dataType = "List")
    @TableField(exist = false)
    private List<CompanyFrame> children;

}
