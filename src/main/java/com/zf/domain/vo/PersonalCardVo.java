package com.zf.domain.vo;

import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigInteger;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author pd
 * DateTime: 2022/9/17 17:10
 */
@Data
public class PersonalCardVo {

  @ApiModelProperty(value = "公司Id",dataType = "long")
  private String username;
  @ApiModelProperty(value = "公司Id",dataType = "long")
  private int companyId;
  @ApiModelProperty(value = "公司Id",dataType = "long")
  private int roleId;
  @ApiModelProperty(value = "公司Id",dataType = "long")
  private BigInteger phoneNumber;
  @ApiModelProperty(value = "公司Id",dataType = "long")
  private String email;


}
