package com.zf.domain.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;


@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SearchUserVo {
    @ApiModelProperty(value = "公司Id",dataType = "long")
    private String username;
    @ApiModelProperty(value = "公司Id",dataType = "long")
    private Integer sex;
    @ApiModelProperty(value = "公司Id",dataType = "long")
    private String phoneNumber;
}
