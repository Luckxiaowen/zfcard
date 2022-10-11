package com.zf.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @Author wenqin
 * @Date 2022/10/5 21:22
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountDto {
    private Integer id;
    @NotBlank(message = "姓名不能为空")
    private String username;
    @NotBlank(message = "电话不能为空")
    @Length(max = 11,min = 11,message = "电话格式不正确")
    private String telNumber;
    @NotNull(message = "角色id不能为空")
    private Integer roleId;
}
