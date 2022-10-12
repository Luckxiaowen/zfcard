package com.zf.domain.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "小程序客户管理返回模型",description = "封装接口返回给前端的数据")
public class ClientVo {

    @ApiModelProperty(value = "id",dataType = "Long ")
    private Long id;
    @ApiModelProperty(value = "创建时间",dataType = "Date ")
    private Date createTime;
    @ApiModelProperty(value = "客户名称",dataType = "Long ")
    private String clientName;
    @ApiModelProperty(value = "客户电话",dataType = "String ")
    private String tel;
    @ApiModelProperty(value = "互动数",dataType = "Integer ")
    private Integer interactNum;


}
