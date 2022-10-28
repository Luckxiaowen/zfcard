package com.zf.domain.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author pd
 * DateTime: 2022/10/21 15:48
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserQueryVo {

  @ApiModelProperty(value = "当前页",dataType = "int")
  private Integer pageNum;

  @ApiModelProperty(value = "每页条数",dataType = "int")
  private Integer pageSize;

  @ApiModelProperty(value = "员工编号",dataType = "int")
  private String userId;

  @ApiModelProperty(value = "员工部门",dataType = "String")
  private String roleName;

  @JsonFormat(pattern = "yyyy-MM-dd")
  @ApiModelProperty(value = "开始时间",dataType = "Date")
  private Date startTime;

  @JsonFormat(pattern = "yyyy-MM-dd")
  @ApiModelProperty(value = "结束时间",dataType = "Date")
  private Date endTime;
}
