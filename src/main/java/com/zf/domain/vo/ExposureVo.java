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
@ApiModel(value = "历史数据",description = "封装接口返回给前端的数据")
public class ExposureVo {
    /*id*/
    @ApiModelProperty(value = "id",dataType = "Long")
    private Long id;
    /*时间*/
    @ApiModelProperty(value = "时间",dataType = "Date")
    private Date date;
    /*访客数*/
    @ApiModelProperty(value = "访客数",dataType = "Integer")
    private Integer visitor;
    /*停留次数*/
    @ApiModelProperty(value = "停留次数",dataType = "Integer")
    private Integer stay;
    /*下载量*/
    @ApiModelProperty(value = "下载量",dataType = "Integer")
    private Integer download ;
    /*通讯录*/
    @ApiModelProperty(value = "通讯录",dataType = "Integer")
    private Integer contact;
    /*留言*/
    @ApiModelProperty(value = "留言",dataType = "Integer")
    private Integer comment;
    /*记录条数*/
    @ApiModelProperty(value = "记录条数",dataType = "Integer")
    private Integer count;
    /*总停留时常*/
    @ApiModelProperty(value = "总停留时常",dataType = "Integer")
    private Double stayMin;
}
