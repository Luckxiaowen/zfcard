package com.zf.domain.dto;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @Author wenqin
 * @Date 2022/9/24 19:30
 */
@Data
@NotBlank
@AllArgsConstructor
public class RoleDto {

//    @ApiModelProperty(value = "角色id,更新和删除时须填写")
//    @NotNull(message = "角色id不能为空",groups = Save.class)
//    @Min(value = 1L,groups = Save.class)
//    private Long id;

    /**
     * 角色名字
     */
    @ApiModelProperty(value = "新增角色名字",required = true)
    @NotBlank(message = "角色名不能为null")
    private String roleName;

    @ApiModelProperty(value = "该角色所拥有的菜单ID(是一个数组)",required = true)
    @NotEmpty(message = "至少也要有一个权限菜单吧!!!")
    private List<Long> menuId;

    public interface Save {
    }

    public interface Update {
    }
}
