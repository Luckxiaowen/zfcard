package com.zf.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;


@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SearchUserVo {
    private String username;
    private Integer sex;
    private String phoneNumber;
}
