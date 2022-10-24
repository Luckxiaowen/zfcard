package com.zf.domain.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class DingTalkDto {
    @ApiModelProperty(value = "钉钉AppKey",required = true)
    @NotBlank(message = "appKey不能为空")
    private String appKey;
    @ApiModelProperty(value = "钉钉AppSecret",required = true)
    @NotBlank(message = "appSecret不能为空")
    private String appSecret;
}
