package com.zf.domain.vo;

import com.zf.domain.entity.SysMenu;
import lombok.Data;

import java.util.List;

/**
 * @Author wenqin
 * @Date 2022/9/22 18:27
 */
@Data
public class MenuVo {
    private Long id;

    private String menuName;

    private String path;

    private String icon;

    private List<MenuVo> childrenMenu;
}
