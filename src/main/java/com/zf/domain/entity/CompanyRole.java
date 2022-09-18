package com.zf.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompanyRole {

    /**
     * 公司id 公司id
     */
    private Long companyId;
    /**
     * 角色Id 角色Id
     */
    private Long id;
}
