package com.zf.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompanyDing {
    private Long companyId;
    private String appKey;
    private String app_secret;
}
