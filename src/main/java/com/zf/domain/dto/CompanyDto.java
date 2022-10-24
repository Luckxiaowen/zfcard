package com.zf.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * @Author wenqin
 * @Date 2022/10/24 14:50
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CompanyDto {
    @NotBlank(message = "公司名不能为空")
    private String companyName;
    @NotBlank(message = "公司简称不能为空")
    private String companyAbbreviation;
    @NotBlank(message = "公司LOGO不能为空")
    private String companyLogo;
    @NotBlank(message = "公司管理员姓名不能为空")
    private String adminName;
    @NotBlank(message = "公司管理员电话不能为空")
    private String adminTel;
    @NotBlank(message = "公司到期时间不能为空")
    private String expirationTime;
    @NotEmpty(message = "公司的权限不能为空")
    private List<Integer> companyAuthority;
}
