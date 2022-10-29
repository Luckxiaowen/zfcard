package com.zf.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

/**
 * @Author wenqin
 * @Date 2022/10/27 18:37
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AppKey {
    @NotBlank(message = "appKey不能为空")
    private String appKey;
    @NotBlank(message = "appSecret不能为空")
    private String appSecret;
}
