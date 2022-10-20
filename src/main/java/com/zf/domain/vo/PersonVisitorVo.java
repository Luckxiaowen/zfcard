package com.zf.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PersonVisitorVo {

    private Integer dayVisitor;
    private Integer dayNotes;
    private Integer dayDownloadCard;
    private Integer dayContact;

}
