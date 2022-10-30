package com.zf.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author pd
 * DateTime: 2022/10/24 14:18
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ModuleConfigVo {

  /**
   * 模块名字
   */
  @ApiModelProperty(value = "模块名字",dataType = "String")
  private String moduleName;

  /**
   * 模块图标
   */
  @ApiModelProperty(value = "模块图标",dataType = "String")
  private String moduleBanner;

  /**
   * 公司id
   */
  @ApiModelProperty(value = "公司id",dataType = "Long")
  private Long companyId;

  /**
   * 类别
   */
  @ApiModelProperty(value = "类别",dataType = "String")
  private String category;

  /**
   * 类别
   */
  @ApiModelProperty(value = "模块id",dataType = "String")
  @TableId(type = IdType.ASSIGN_ID)
  private String moduleId;

  @ApiModelProperty(value = "是否打开",dataType = "String")
  private int isOpened;

}
