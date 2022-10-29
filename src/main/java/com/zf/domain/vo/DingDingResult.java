package com.zf.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Author wenqin
 * @Date 2022/10/26 17:31
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DingDingResult {
    private Integer errcode;
    private String errmsg;
    private List<Info> result;
    private String request_id;
}
