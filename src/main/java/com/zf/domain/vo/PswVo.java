package com.zf.domain.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author pd
 * DateTime: 2022/10/19 20:34
 */
@Data
public class PswVo {

  @ApiModelProperty(value = "原密码",dataType = "String")
  private String originalPsw ;

  @ApiModelProperty(value = "新密码",dataType = "String")
  private String newPsw;

  @ApiModelProperty(value = "确认密码",dataType = "String")
  private String confirmPsw ;

}
