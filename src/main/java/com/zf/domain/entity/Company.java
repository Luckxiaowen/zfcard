package com.zf.domain.entity;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

/**
 * 公司表
 * @TableName company
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Company implements Serializable {
    /**
     * 公司Id 公司id
     */
    @TableId
    private Long id;

    /**
     * 公司名称 公司名称
     */
    @NotBlank(message = "公司名称不能为空")
    private String company;

    /**
     * 公司地址 公司地址
     */
    private String address;

    /**
     * 公司电话 公司电话
     */
    private String tel;

    /**
     * 创建人 创建人
     */
    private Long createBy;

    /**
     * 创建时间 创建时间
     */
    private Date createTime;

    /**
     * 是否删除 删除标志（0代表未删除，1代表已删除）
     */
    @TableLogic
    private Integer delFlag;

    /**
     * 更新人 更新人
     */
    private Long updateBy;

    /**
     * 更新时间 更新时间
     */
    private Date updateTime;

}
