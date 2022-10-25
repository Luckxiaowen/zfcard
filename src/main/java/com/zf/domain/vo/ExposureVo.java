package com.zf.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExposureVo {
    private Long id;
    private Date date;
    private Integer visitor;
    private Integer stay;
    private Integer download ;
    private Integer contact;
    private Integer comment;
}
