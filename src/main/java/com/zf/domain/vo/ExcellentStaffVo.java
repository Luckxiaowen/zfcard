package com.zf.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExcellentStaffVo implements Comparable<ExcellentStaffVo> {
    private String staffName;
    private String station;
    private int MouthVisitor;
    private int mouthClient;
    private int mouthActive;

    /*TODO 排序比较*/
    @Override
    public int compareTo(ExcellentStaffVo excellentStaffVo) {
        return this.mouthActive+excellentStaffVo.mouthActive;
    }
}
