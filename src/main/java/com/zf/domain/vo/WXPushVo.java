package com.zf.domain.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class  WXPushVo {
    @ApiModelProperty(value = "用户编号",dataType = "int")
    private Integer id;

    @ApiModelProperty(value = "用户名称",dataType = "String")
    private String username;

    @ApiModelProperty(value = "部门名称",dataType = "String")
    private String dep_name;

    @ApiModelProperty(value = "岗位名称",dataType = "String")
    private String station;

    @ApiModelProperty(value = "访客总数",dataType = "int")
    private int visitorNum;

    @ApiModelProperty(value = "客户总量",dataType = "int")
    private int clientNum;

    @ApiModelProperty(value = "留言总量",dataType = "int")
    private int noteNum;

    @ApiModelProperty(value = "保存名片总量",dataType = "int")
    private int saveCardNum;

    @ApiModelProperty(value = "保存电话总量",dataType = "int")
    private int saveContactNum;

    @ApiModelProperty(value = "活跃总量",dataType = "int")
    private int activeNum;

    @ApiModelProperty(value = "停留总次数",dataType = "int")
    private int stayNum;

    @ApiModelProperty(value = "停留总时长 换算为秒数",dataType = "String")
    private String averageStayMin;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty(value = "创建时间",dataType = "Date")
    private Date createTime;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty(value = "修改时间",dataType = "Date")
    private Date updateTime;

    @ApiModelProperty(value = "创建人",dataType = "String")
    private String createName;

}
