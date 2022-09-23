package com.zf.domain.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class CompanyProfileVo {

    @ApiModelProperty(value = "id",dataType = "long")
    private Long id;
    @ApiModelProperty(value = "公司Id",dataType = "long")
    private String infoName;
    @ApiModelProperty(value = "公司Id",dataType = "long")
    private String infoContent;
    @ApiModelProperty(value = "公司Id",dataType = "long")
    private Integer companyId;
}

