package com.zf.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class DepVo implements Comparable<DepVo>{
    private int depId;
    private String depName;
    private int depPersonNum;
    private int depVisitNum;
    private int depVisitAverage;
    private int depClientNum;
    private int depClientAverage;
    private int depActiveNum;
    private int depActiveAverage;

    @Override
    public int compareTo(DepVo depVo) {

        return this.depPersonNum+depVo.depPersonNum;
    }
}
