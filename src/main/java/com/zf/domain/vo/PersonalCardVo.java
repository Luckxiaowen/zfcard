package com.zf.domain.vo;

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

  private String username;
  private int companyId;
  private int roleId;
  private BigInteger phoneNumber;
  private String email;


}
