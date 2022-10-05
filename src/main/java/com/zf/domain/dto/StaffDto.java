package com.zf.domain.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @Author wenqin
 * @Date 2022/10/2 19:26
 * 员工
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class StaffDto {

    @ApiModelProperty(value = "客服id,更新,删除时需要传入客服id",dataType = "int")
    @NotNull(message = "id不能为空",groups = Update.class)
    private Integer id;

    /**
     * 用户名 用户名
     */
    @ApiModelProperty(value = "用户名",dataType = "String",required = true)
    @NotBlank(message = "用户名不能为空")
    private String username;


    /**
     * 邮箱 邮箱
     */
    @ApiModelProperty(value = "邮箱",dataType = "String",required = true)
    @NotBlank(message = "邮箱不能为空")
    private String email;

    /**
     * 手机号 手机号
     */
    @ApiModelProperty(value = "手机号",dataType = "String",required = true)
    @NotBlank(message = "手机号不能为空")
    @Length(min = 11,max = 11,message = "手机号不正确")
    private String phonenumber;

    /**
     * 微信号 微信号
     */
    @ApiModelProperty(value = "微信号",dataType = "String",required = true)
    @NotBlank(message = "微信号不能为空")
    private String telWeixin;



    /**
     * 微信二维码 微信二维码
     */
    @ApiModelProperty(value = "微信二维码地址",dataType = "String",required = true)
    private String weixinCode;




    @ApiModelProperty(value = "岗位名称",dataType = "String",required = true)
    @NotBlank(message = "岗位名称不能为空")
    private String station;

    @ApiModelProperty(value = "所属部门Id",dataType = "Integer",required = true)
    @NotNull(message = "所属部门Id不能为空")
    private Integer depId;

    interface Update{

    }
}
