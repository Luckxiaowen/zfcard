package com.zf.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class DingTalkDto {
    @NotBlank(message = "appKey不能为空")
    private String appKey;
    @NotBlank(message = "appSecret不能为空")
    private String appSecret;
}
