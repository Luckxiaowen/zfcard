package com.zf.domain.vo;

import com.zf.domain.entity.SysMenu;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @Author wenqin
 * @Date 2022/9/22 18:27
 */
@Data
public class MenuVo {
    @ApiModelProperty(value = "公司Id",dataType = "long")
    private Long id;

    @ApiModelProperty(value = "公司Id",dataType = "long")
    private String menuName;

    @ApiModelProperty(value = "公司Id",dataType = "long")
    private String path;

    @ApiModelProperty(value = "公司Id",dataType = "long")
    private String icon;

    @ApiModelProperty(value = "公司Id",dataType = "long")
    private List<MenuVo> childrenMenu;
}
