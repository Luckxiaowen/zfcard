package com.zf.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExcellentStaffVo {
    private String staffName;
    private String station;
    private int MouthVisitor;
    private int mouthClient;
    private int mouthActive;
}
